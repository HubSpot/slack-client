package com.hubspot.slack.client.models.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import org.junit.Test;

public class SlackUserDeserializationTest {

  @Test
  public void deletedUsersDeserializeProperly() throws IOException {
    SlackUser user = getSlackUser();
    assertTrue(user.isDeleted().isPresent() && user.isDeleted().get());
  }

  @Test
  public void serializeAndDeserializeAreInverses() throws IOException {
    SlackUser user = getSlackUser();
    SlackUser userDeserialized = ObjectMapperUtils
      .mapper()
      .readValue(ObjectMapperUtils.mapper().writeValueAsString(user), SlackUser.class);
    assertThat(userDeserialized).isEqualTo(user);
  }

  @Test
  public void externalTeamIdDeserializesAndMarksUserExternal() throws IOException {
    SlackUser user = ObjectMapperUtils
      .mapper()
      .readValue(
        "{\"id\":\"U123\",\"team_id\":\"TSCOPE\",\"external_team_id\":\"TORIGIN\"}",
        SlackUser.class
      );

    assertThat(user.getExternalTeamId()).contains("TORIGIN");
    assertThat(user.isExternal()).isTrue();
  }

  @Test
  public void userWithoutExternalTeamIdIsNotExternal() throws IOException {
    SlackUser user = getSlackUser();

    assertThat(user.getExternalTeamId()).isEmpty();
    assertThat(user.isExternal()).isFalse();
  }

  @Test
  public void derivedIsExternalIsNotSerialized() throws IOException {
    SlackUser user = SlackUser
      .builder()
      .setId("U123")
      .setExternalTeamId("TORIGIN")
      .build();

    String json = ObjectMapperUtils.mapper().writeValueAsString(user);

    // externalTeamId is persisted; the derived isExternal() must not leak into JSON
    assertThat(json).contains("external_team_id");
    assertThat(json).doesNotContain("is_external");
    assertThat(json).doesNotContain("\"external\"");

    SlackUser roundTripped = ObjectMapperUtils.mapper().readValue(json, SlackUser.class);
    assertThat(roundTripped.getExternalTeamId()).contains("TORIGIN");
    assertThat(roundTripped.isExternal()).isTrue();
  }

  @Test
  public void usersWithArrayProfileFieldsDeserializeCorrectly() throws IOException {
    SlackUser user = getUserWithArrayProfileFieldsType();
    assertThat(user.getProfile()).isPresent().describedAs("User profile is missing");
    assertThat(user.getProfile().get().getFields()).isEmpty();
  }

  private SlackUser getSlackUser() throws IOException {
    return ObjectMapperUtils
      .mapper()
      .readValue(JsonLoader.loadJsonFromFile("deleted_slack_user.json"), SlackUser.class);
  }

  private SlackUser getUserWithArrayProfileFieldsType() throws IOException {
    return ObjectMapperUtils
      .mapper()
      .readValue(
        JsonLoader.loadJsonFromFile("slack_user_with_array_profile_fields.json"),
        SlackUser.class
      );
  }
}
