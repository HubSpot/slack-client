package com.hubspot.slack.client.models;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.group.SlackGroup;

public class ChannelDeserializerTest {

  @Test
  public void itDeserializesChannels() throws IOException {
    SlackChannel channel = fetchAndDeserializeSlackChannel("general_channel.json");
    assertTrue(channel.getIsGeneral().isPresent() && channel.getIsGeneral().get());
  }

  @Test
  public void itDeserializesPrivateChannels() throws IOException {
    List<SlackGroup> groups = fetchAndDeserializeSlackGroup("private_channels.json");
    assertTrue(groups.size() == 6);
  }

  private SlackChannel fetchAndDeserializeSlackChannel(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, SlackChannel.class);
  }

  private List<SlackGroup> fetchAndDeserializeSlackGroup(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, new TypeReference<List<SlackGroup>>(){});
  }
}
