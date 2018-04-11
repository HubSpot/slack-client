# slack-client 

[![license](https://img.shields.io/github/license/HubSpot/slack-client.svg?style=social)](https://github.com/HubSpot/slack-client/blob/master/LICENSE)
[![GitHub last commit](https://img.shields.io/github/last-commit/HubSpot/slack-client.svg?style=social)](https://github.com/HubSpot/slack-client/commits/master)
 [![Build Status](https://travis-ci.org/HubSpot/slack-client.svg?branch=master)](https://travis-ci.org/HubSpot/slack-client) [![GitHub release](https://img.shields.io/github/release/HubSpot/slack-client.svg)](https://github.com/HubSpot/slack-client/releases) ![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/hubspot/slack/slack-client/maven-metadata.xml.svg) 
 
* [Overview](overview)
* [Features](features)
* [Usage](usage)
* [Setting up Guice](setting-up-guice)
* [Using the Client](using-the-client)
    * [Find a user by email, then send a message](find-a-user-by-email-then-send-a-message)
    * [Filtering messages in a QA environment to specific channels](filtering-messages-in-a-qa-environment-to-specific-channels)
    * [Printing requests for specific methods](printing-requests-for-specific-methods)
* [Contributors](contributors)
* [License](license)

## Overview

An asychronous HTTP client wrapping Slack's [RPC-style web api](https://api.slack.com/web). Provides an extensible API with builder-style parameters and responses, allowing you to focus on your interactions with users, rather than your interactions with Slack. Notably, we:

* Implement [most](features) of Slack's [web API](https://api.slack.com/web)
* Actively maintain this project
* Provide per-method in-memory rate limiting so you don't have to worry about overwhelming slack from a single process
* Expose highly configurable hooks to allow filtering and debugging in an extensible way

## Features

We currently support:
#### auth
 - auth.test
#### channels
 - channels.history
 - channels.info
 - channels.list
 - channels.replies (findReplies)
#### chat
 - chat.delete
 - chat.getPermalink
 - chat.postEphemeral
 - chat.postMessage
 - chat.update
#### conversations
 - conversations.archive
 - conversations.create
 - conversations.history
 - conversations.info
 - conversations.invite
 - conversations.list
 - conversations.unarchive
#### dialog
 - dialog.open
#### groups
 - groups.list
 - groups.replies (findReplies)
#### im
 - im.open
#### reactions
 - reactions.add
#### search
 - search.messages
#### usergroups
 - usergroups.create
 - usergroups.disable
 - usergroups.enable
 - usergroups.list
 - usergroups.update
#### usergroups.users
 - usergroups.users.update
#### users
 - users.info (findUser)
 - users.list
 - users.lookupByEmail
#### Utility Methods
 - getConversationByName
 - getChannelByName

We happily welcome any PRs for APIs that haven't yet been implemented!

## Usage

To use with Maven-based projects, add the following dependencies::

```xml
<dependency>
  <groupId>com.hubspot.slack</groupId>
  <artifactId>slack-base</artifactId>
  <version>{latest version}</version>
</dependency>
<dependency>
  <groupId>com.hubspot.slack</groupId>
  <artifactId>slack-java-client</artifactId>
  <version>{latest  version}</version>
</dependency>
```

Latest version can be seen [here, on Maven central](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.hubspot.slack%22).

### Seting up Guice

Install the `SlackClientModule` in the Guice module you want to use to talk to slack.

```java
public class MyFancyModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new SlackClientModule());
  }

  @Override
  public boolean equals(Object o) {
    return o != null && getClass().equals(o.getClass());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
```

Finally inject the factory where you want to build the client:

```java
public class MySlacker {
  private final SlackClient slackClient;
  
  
  public MySlacker(
      SlackWebClient.Factory clientFactory
  ) {
    this.slackClient = clientFactory.build(
        SlackClientRuntimeConfig.builder()
          .setTokenSupplier(() -> "your token here")
          // ... all your configuration here
          .build()
    );
  }
  
  // then just use the client!
}
```

### Using the Client

If you're not familiar with Java 8's `CompletableFuture` API, know that you can transform this client from an asynchronous one to a synchronous one by calling `join` on any of the futures returned. To simplify getting started, here are some examples of using the client for common tasks.

#### Find a user by email, then send a message

```java
void sendMeAMessage() {
  Optional<SlackUser> meMaybe = findMe();
  if (!meMaybe.isPresent()) {
    LOG.error("Couldn't find me in slack!");
  }

  slackMe(meMaybe.get());
}

Optional<SlackUser> findMe() {
  /**
   * Since we may have to enumerate a large set, we chunk it up into pages, and handle each page separately.
   */
  Iterable<CompletableFuture<Result<List<SlackUser>, SlackError>>> userPageFutures = slackClient.listUsers();

  /**
   * We'll enumerate the pages so we can short-circuit and break out early if we can
   */
  for (CompletableFuture<Result<List<SlackUser>, SlackError>> userPageFuture : userPageFutures) {
    Result<List<SlackUser>, SlackError> pageResult = userPageFuture.join();

    // If there was a problem fetching the page, it'll percolate here as a RTE
    List<SlackUser> slackUsers = pageResult.unwrapOrElseThrow();
    Optional<SlackUser> matchingUser = slackUsers.stream()
        .filter(user ->
            user.getProfile()
                .flatMap(UserProfile::getEmail) // flatMap just says the profile could be absent, or the email could be absent
                .filter("eszabowexler@hubspot.com"::equalsIgnoreCase) // keep it only if it's the email we want...
                .isPresent()) // and we'll filter the original SlackUser list to find the one with the profile that's me!
        .findFirst();

    if (matchingUser.isPresent()) {
      return matchingUser;
    }
  }

  return Optional.empty();
}

ChatPostMessageResponse slackMe(SlackUser me) {
  Result<ChatPostMessageResponse, SlackError> postResult = slackClient.postMessage(
      ChatPostMessageParams.builder()
          .setText("Hello me! Here's a slack message!")
          .setChannelId("a fancy channel ID here")
          .build()
  ).join();

  return postResult.unwrapOrElseThrow();// again, release failure here as a RTE
}
```

#### Filtering messages in a QA environment to specific channels

```java
public class MySlacker {
  private final SlackClient slackClient;
  
  public MySlacker(
      SlackWebClient.Factory clientFactory
  ) {
    this.slackClient = clientFactory.build(
        SlackClientRuntimeConfig.builder()
          .setTokenSupplier(() -> "your token here")
          .setMethodFilter(
            new SlackMethodAcceptor() {
                @Override
                public String getFailureExplanation(SlackMethod method, Object params) {
                  return "Only allow WRITE methods to our special channel in QA!";
                }
  
                @Override
                public boolean test(SlackMethod slackMethod, Object o) {
                  if (isQa() && slackMethod.getWriteMode() == MethodWriteMode.WRITE) {
                    if (o instanceof HasChannel && ((HasChannel) o).getChannelId().equals("snazzy id")) {
                      return true;
                    }
                    return false;
                  }
  
                  return true;
                }
            })
          // ... all your configuration here
          .build()
    );
  }
  
  // then just use the client!
}
```

#### Printing requests for specific methods

```java
public class MySlacker {
  private final SlackClient slackClient;
  
  public MySlacker(
      SlackWebClient.Factory clientFactory
  ) {
    this.slackClient = clientFactory.build(
        SlackClientRuntimeConfig.builder()
          .setTokenSupplier(() -> "your token here")
          .setRequestDebugger(
              new RequestDebugger() {
                @Override
                public void debug(long requestId, SlackMethod method, HttpRequest request) {
                  if (method == SlackMethods.chat_postEphemeral) {
                    LOG.info("Posting ephemeral message {}", format(request));
                  }
                }
              }
          )
          // ... all your configuration here
          .build()
    );
  }
  
  // then just use the client!
}
```

## Contributors
 - @szabowexler [:computer:](https://github.com/HubSpot/slack-client/commits?author=szabowexler)
 - @zklapow [:computer:](https://github.com/HubSpot/slack-client/commits?author=zklapow)
 - @wsorenson [:computer:](https://github.com/HubSpot/slack-client/commits?author=wsorenson)
 - @darcatron [:computer:](https://github.com/HubSpot/slack-client/commits?author=darcatron)
 - @dylanrb123  [:computer:](https://github.com/HubSpot/slack-client/commits?author=dylanrb123)

## License

    Copyright 2018 HubSpot, Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

