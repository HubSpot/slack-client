# slack-client [![Build Status](https://travis-ci.org/HubSpot/slack-client.svg?branch=master)](https://travis-ci.org/HubSpot/slack-client)

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
