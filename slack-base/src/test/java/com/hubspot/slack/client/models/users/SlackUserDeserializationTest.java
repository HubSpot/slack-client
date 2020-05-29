package com.hubspot.slack.client.models.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;

public class SlackUserDeserializationTest {
  @Test
  public void deletedUsersDeserializeProperly() throws IOException {
    SlackUser user = getSlackUser();
    assertTrue(user.isDeleted().isPresent() && user.isDeleted().get());
  }

  @Test
  public void serializeAndDeserializeAreInverses() throws IOException {
    SlackUser user = getSlackUser();
    SlackUser userDeserialized = ObjectMapperUtils.mapper().readValue(ObjectMapperUtils.mapper().writeValueAsString(user), SlackUser.class);
    assertThat(userDeserialized).isEqualTo(user);
  }

  @Test
  public void usersWithArrayProfileFieldsDeserializeCorrectly() throws IOException {
    SlackUser user = getUserWithArrayProfileFieldsType();
    assertThat(user.getProfile())
        .isPresent()
        .describedAs("User profile is missing");
    assertThat(user.getProfile().get().getFields()).isEmpty();
  }

  private SlackUser getSlackUser() throws IOException {
    return ObjectMapperUtils.mapper().readValue(JsonLoader.loadJsonFromFile("deleted_slack_user.json"), SlackUser.class);
  }

  private SlackUser getUserWithArrayProfileFieldsType() throws IOException {
    return ObjectMapperUtils.mapper().readValue(JsonLoader.loadJsonFromFile("slack_user_with_array_profile_fields.json"), SlackUser.class);
  }
}
