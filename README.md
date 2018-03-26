# slack-client

An asychronous HTTP client wrapping Slack's [RPC-style web api](https://api.slack.com/web). The API here is simple: you just need a `SlackClient` and you're good to go. 

## Usage

Include the base and client modules in your POM:

```xml
<dependency>
  <groupId>com.hubspot.slack</groupId>
  <artifactId>slack-base</artifactId>
  <version>1.0</version>
</dependency>
<dependency>
  <groupId>com.hubspot.slack</groupId>
  <artifactId>slack-java-client</artifactId>
  <version>1.0</version>
</dependency>
```

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
