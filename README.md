# slack-client 

[![license](https://img.shields.io/github/license/HubSpot/slack-client.svg?style=social)](https://github.com/HubSpot/slack-client/blob/master/LICENSE)
[![GitHub last commit](https://img.shields.io/github/last-commit/HubSpot/slack-client.svg?style=social)](https://github.com/HubSpot/slack-client/commits/master)
 [![Build Status](https://travis-ci.org/HubSpot/slack-client.svg?branch=master)](https://travis-ci.org/HubSpot/slack-client) [![GitHub release](https://img.shields.io/github/release/HubSpot/slack-client.svg)](https://github.com/HubSpot/slack-client/releases) ![Maven metadata URI](https://img.shields.io/maven-metadata/v/http/central.maven.org/maven2/com/hubspot/slack/slack-client/maven-metadata.xml.svg) 
 
* [Overview](#overview)
* [Maintenance](#maintenance)
* [Features](#features)
* [Usage](#usage)
* [Setting up Guice](#setting-up-guice)
* [Using the Client](#using-the-client)
* [Contributors](#contributors)
* [License](#license)

## Overview

An asychronous HTTP client wrapping Slack's [RPC-style web api](https://api.slack.com/web). Provides an extensible API with builder-style parameters and responses, allowing you to focus on your interactions with users, rather than your interactions with Slack. Notably, we:

* Implement [most](#features) of Slack's [web API](https://api.slack.com/web)
* Actively maintain this project
* Provide per-method in-memory rate limiting so you don't have to worry about overwhelming slack from a single process
* Expose highly configurable hooks to allow custom rate limiting, method filtering, and debugging in an extensible way


## Breaking Changes

**NOTICE:**

On October 18th 2019, Slack will stop supporting the `replies` thread on a `Message` returned from the [`conversations.replies` endpoint](https://api.slack.com/methods/conversations.replies) as well as any places that use `LiteMessage`.

Due to this, we are deprecating `getReplies()` on [`LiteMessage`](https://github.com/HubSpot/slack-client/blob/master/slack-base/src/main/java/com/hubspot/slack/client/models/LiteMessageIF.java)
It will return the data as long as Slack still returns it, bue when they stop, we will just return an empty array.

More context in the [April 2019 changelog](https://api.slack.com/changelog)

You can continue to use an older version of this client until October 18th, at which time you'll need to upgrade to the latest.


## Maintenance

Like most APIs, deprecations and modifications that we'll call "breaking changes" will occur on Slack's web API.
General guidelines on detecting and handling "breaking changes" can be found [here](https://github.com/HubSpot/slack-client/blob/master/DEV_README.md)

## Features

We currently support:
#### auth
 - auth.test
 - auth.revoke
#### channels
 - channels.history
 - channels.info
 - channels.kick (kickUserFromChannel)
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
 - conversations.members
 - conversations.open
 - conversations.unarchive
#### dialog
 - dialog.open
#### files
 - files.upload
 - files.sharedPublicURL
#### groups
 - groups.kick (kickUserFromGroup)
 - groups.list
 - groups.replies (findReplies)
#### im
 - im.open
#### reactions
 - reactions.add
#### search
 - search.messages
#### team
 - team.info 
#### usergroups
 - usergroups.create
 - usergroups.disable
 - usergroups.enable
 - usergroups.list
 - usergroups.update
#### usergroups.users
 - usergroups.users.update
#### users
 - users.conversations
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

### Setting up Guice

Install the `SlackClientModule` in the Guice module you want to use to talk to slack.

```java
public class MyFancyModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new SlackClientModule());
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

If you're not familiar with Java 8's `CompletableFuture` API, know that you can transform this client from an asynchronous one to a synchronous one by calling `join` on any of the futures returned. To simplify getting started, we've included an [example module](slack-java-client-examples) with some common tasks to give you a feel for how you can interact with the client.


## Contributors
 - [@szabowexler](https://github.com/szabowexler) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=szabowexler)
 - [@zklapow](https://github.com/zklapow) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=zklapow)
 - [@wsorenson](https://github.com/wsorenson) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=wsorenson)
 - [@darcatron](https://github.com/darcatron) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=darcatron)
 - [@dylanrb123](https://github.com/dylanrb123) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=dylanrb123)
 - [@stevegutz](https://github.com/stevegutz) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=stevegutz)
 - [@BWehner](https://github.com/BWehner) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=BWehner)
 - [@axiak](https://github.com/axiak) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=axiak)
 - [@andybergon](https://github.com/andybergon) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=andybergon)
 - [@mlr46](https://github.com/mlr46) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=mlr46)
 - [@mindspin311](https://github.com/mindspin311) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=mindspin311)
 - [@Ulya0302](https://github.com/Ulya0302) | [:computer:](https://github.com/HubSpot/slack-client/commits?author=Ulya0302)

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

