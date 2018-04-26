package com.hubspot.slack.client;

import com.google.common.base.Stopwatch;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.interceptors.calls.SlackMethodAcceptor;
import com.hubspot.slack.client.interceptors.http.ResponseDebugger;
import com.hubspot.slack.client.methods.MethodWriteMode;
import com.hubspot.slack.client.methods.SlackMethod;
import com.hubspot.slack.client.methods.SlackMethods;
import com.hubspot.slack.client.methods.interceptor.HasChannel;

public class SlackClientExampleModule extends AbstractModule {
  @Override
  protected void configure() {
    install(new SlackClientModule());
  }

  @Provides
  @SlackExampleClient(ExampleClientType.BASIC)
  public SlackClient providesBasicExampleClient(SlackWebClient.Factory factory) {
    return factory.build(
        SlackClientRuntimeConfig.builder()
            .setTokenSupplier(() -> "a fake token")
            .build()
    );
  }

  @Provides
  @SlackExampleClient(ExampleClientType.CUSTOM_DEBUG)
  public SlackClient providesCustomDebugExampleClient(SlackWebClient.Factory factory) {
    return factory.build(
        SlackClientRuntimeConfig.builder()
            .setTokenSupplier(() -> "a fake token")
            .setRequestDebugger((requestId, method, request) -> {
              if (method == SlackMethods.chat_postEphemeral) {
                // do something special with ephemeral messages
              }
            })
            .setResponseDebugger(new ResponseDebugger() {
              @Override
              public void debug(long requestId, SlackMethod method, Stopwatch timer, HttpRequest request, HttpResponse response) {
                // basic debug on everything
              }

              @Override
              public void debugTransportException(long requestId, SlackMethod method, HttpRequest request, Throwable exception) {
                // something went wrong talking to slack
              }

              @Override
              public void debugSlackApiError(long requestId, SlackMethod method, HttpRequest request, HttpResponse failure) {
                // slack tells us we did something wrong (or they had a problem)
              }

              @Override
              public void debugProcessingFailure(long requestId, SlackMethod method, HttpRequest request, HttpResponse response, Throwable ex) {
                // problem in the client code itself resulted in failure
              }
            })
            .build()
    );
  }

  @Provides
  @SlackExampleClient(ExampleClientType.WITH_METHOD_FILTERING)
  public SlackClient providesFilteringExampleClient(SlackWebClient.Factory factory) {
    return factory.build(
        SlackClientRuntimeConfig.builder()
            .setTokenSupplier(() -> "a fake token")
            .setMethodFilter(
                new SlackMethodAcceptor() {
                  @Override
                  public String getFailureExplanation(SlackMethod method, Object params) {
                    return "Only allow WRITE methods to our special channel in QA!";
                  }

                  @Override
                  public boolean test(SlackMethod slackMethod, Object o) {
                    if (isQa() && slackMethod.getWriteMode() == MethodWriteMode.WRITE) {
                      return o instanceof HasChannel && ((HasChannel) o).getChannelId().equals("snazzy id");
                    }

                    return true;
                  }
                })
            .build()
    );
  }

  private boolean isQa() {
    return true; // fake method, just tell us if this is the QA environment
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
