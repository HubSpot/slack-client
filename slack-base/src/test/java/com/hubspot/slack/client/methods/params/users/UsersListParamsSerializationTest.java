package com.hubspot.slack.client.methods.params.users;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import org.junit.Test;

public class UsersListParamsSerializationTest {

  @Test
  public void itSerializesTeamId() {
    UsersListParams params = UsersListParams.builder().setTeamId("T012AB3C4").build();

    JsonNode node = ObjectMapperUtils.mapper().valueToTree(params);

    assertThat(node.get("team_id").asText()).isEqualTo("T012AB3C4");
  }
}
