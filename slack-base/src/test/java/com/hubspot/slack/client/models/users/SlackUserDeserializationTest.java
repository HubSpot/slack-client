package com.hubspot.slack.client.models.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class SlackUserDeserializationTest {
  @Test
  public void usersSerializeAndDeserializeProperly() throws IOException {
    SlackUser user = getSlackUser();
    assertTrue(user.isDeleted().isPresent() && user.isDeleted().get());
  }

  @Test
  public void serializeAndDeserializeAreInverses() throws IOException {
    SlackUser user = getSlackUser();
    SlackUser userDeserialized = ObjectMapperUtils.mapper().readValue(ObjectMapperUtils.mapper().writeValueAsString(user), SlackUser.class);
    assertThat(userDeserialized).isEqualTo(user);
  }

  private SlackUser getSlackUser() throws IOException {
    return ObjectMapperUtils.mapper().readValue(JsonLoader.loadJsonFromFile("deleted_slack_user.json"), SlackUser.class);
  }
}
