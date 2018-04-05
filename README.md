# slack-client 

![license](https://img.shields.io/github/license/HubSpot/slack-client.svg?style=social) 
![GitHub last commit](https://img.shields.io/github/last-commit/HubSpot/slack-client.svg?style=social)
 [![Build Status](https://travis-ci.org/HubSpot/slack-client.svg?branch=master)](https://travis-ci.org/HubSpot/slack-client) ![GitHub release](https://img.shields.io/github/release/HubSpot/slack-client.svg) ![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/hubspot/slack/slack-client/maven-metadata.xml.svg) 


## Overview

An asychronous HTTP client wrapping Slack's [RPC-style web api](https://api.slack.com/web). Provides an extensible API with builder-style parameters and responses, allowing you to focus on your interactions with users, rather than your interactions with Slack.

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
          // ... all your configuration here
          .builder()
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
