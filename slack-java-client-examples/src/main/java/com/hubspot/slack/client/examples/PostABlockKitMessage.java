package com.hubspot.slack.client.examples;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hubspot.algebra.Result;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;

public class PostABlockKitMessage {
  private static final Logger LOG = LoggerFactory.getLogger(PostABlockKitMessage.class);

  public static void main(String[] args) {
    SlackClient client = BasicRuntimeConfig.getClient();

    ChatPostMessageResponse response = messageChannel("fake channel id", client);
    LOG.info("Got: {}", response);
  }

  public static ChatPostMessageResponse messageChannel(String channelToPostIn, SlackClient slackClient) {
    Result<ChatPostMessageResponse, SlackError> postResult = slackClient.postMessage(
        ChatPostMessageParams.builder()
            .setText("Here is an example message with blocks:")
            .setChannelId(channelToPostIn)
            .setBlocks(Arrays.asList(
                Blocks.markdownSection(":newspaper:  *Paper Company Newsletter*  :newspaper:"),
                Blocks.markdownContext("*November 12, 2019*  |  Sales Team Announcements"),
                Blocks.divider(),
                Blocks.markdownSection(":loud_sound: *IN CASE YOU MISSED IT* :loud_sound:"),
                Blocks.markdownSection("Replay our screening of *Threat Level Midnight* and pick up a copy of the DVD to give to your customers at the front desk.")
                    .withAccessory(Blocks.plainTextButton("Watch Now")),
                Blocks.markdownSection("The *2019 Dundies* happened. \nAwards were given, heroes were recognized. \nCheck out *#dundies-2019* to see who won awards."),
                Blocks.divider(),
                Blocks.markdownSection(":calendar: |   *UPCOMING EVENTS*  | :calendar:"),
                Blocks.markdownSection("`11/20-11/22` *Beet the Competition* _ annual retreat at Schrute Farms_")
                    .withAccessory(Blocks.plainTextButton("RSVP")),
                Blocks.markdownSection("`12/01` *Toby's Going Away Party* at _Benihana_")
                    .withAccessory(Blocks.plainTextButton("Learn More")),
                Blocks.markdownSection("`11/13` :pretzel: *Pretzel Day* :pretzel: at _Scranton Office_")
                    .withAccessory(Blocks.plainTextButton("RSVP")),
                Blocks.divider(),
                Blocks.markdownSection(":calendar: |   *PAST EVENTS*  | :calendar:"),
                Blocks.markdownSection("`10/21` *Conference Room Meeting*")
                    .withAccessory(Blocks.staticSelectMenu("Manage",
                        Blocks.option("Edit it", "value=0"),
                        Blocks.option("Read it", "value=1"),
                        Blocks.option("Save it", "value=2"))),
                Blocks.divider()
            )).build()
    ).join();

    return postResult.unwrapOrElseThrow(); // release failure here as a RTE
  }
}
