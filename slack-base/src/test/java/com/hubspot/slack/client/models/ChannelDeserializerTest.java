package com.hubspot.slack.client.models;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;

public class ChannelDeserializerTest {

  @Test
  public void itDeserializesChannels() throws IOException {
    SlackChannel channel = fetchAndDeserializeSlackChannel("general_channel.json");
    assertTrue(channel.getIsGeneral().isPresent() && channel.getIsGeneral().get());
  }

  private SlackChannel fetchAndDeserializeSlackChannel(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, SlackChannel.class);
  }
}
