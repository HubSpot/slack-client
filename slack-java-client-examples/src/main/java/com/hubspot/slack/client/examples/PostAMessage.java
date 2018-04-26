package com.hubspot.slack.client.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.hubspot.algebra.Result;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientExampleModule;
import com.hubspot.slack.client.SlackExampleClient;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;

public class PostAMessage {
  private static final Logger LOG = LoggerFactory.getLogger(PostAMessage.class);

  private final SlackClient slackClient;

  public static void main(String[] args) {
    ChatPostMessageResponse response = Guice.createInjector(new SlackClientExampleModule())
        .getInstance(PostAMessage.class)
        .messageChannel("fake channel id");

    LOG.info("Got: {}", response);
  }

  @Inject
  public PostAMessage(
      @SlackExampleClient SlackClient slackClient
  ) {
    this.slackClient = slackClient;
  }

  public ChatPostMessageResponse messageChannel(String channelToPostIn) {
    Result<ChatPostMessageResponse, SlackError> postResult = slackClient.postMessage(
        ChatPostMessageParams.builder()
            .setText("Hello me! Here's a slack message!")
            .setChannelId(channelToPostIn)
            .build()
    ).join();

    return postResult.unwrapOrElseThrow(); // release failure here as a RTE
  }
}
