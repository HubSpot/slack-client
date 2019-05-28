package com.hubspot.slack.client.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hubspot.algebra.Result;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;

public class PostAMessage {
  private static final Logger LOG = LoggerFactory.getLogger(PostAMessage.class);

  public static void main(String[] args) {
    SlackClient client = BasicRuntimeConfig.getClient();

    ChatPostMessageResponse response = messageChannel("fake channel id", client);
    LOG.info("Got: {}", response);
  }

  public static ChatPostMessageResponse messageChannel(String channelToPostIn, SlackClient slackClient) {
    Result<ChatPostMessageResponse, SlackError> postResult = slackClient.postMessage(
        ChatPostMessageParams.builder()
            .setText("Hello me! Here's a slack message!")
            .setChannelId(channelToPostIn)
            .build()
    ).join();

    return postResult.unwrapOrElseThrow(); // release failure here as a RTE
  }
}
