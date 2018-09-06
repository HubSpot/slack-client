package com.hubspot.slack.client.models.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.hubspot.slack.client.TestMappers;
import com.hubspot.slack.client.models.response.users.UsersInfoResponse;

public class SlackResponseTest {

  private static final String ERROR_RESPONSE = "{\n" +
      "    \"ok\": false,\n" +
      "    \"error\": \"something_bad\"\n" +
      "}";

  private static final String USER_RESPONSE = "{\n" +
      "    \"ok\": true,\n" +
      "    \"user\": {\n" +
      "        \"id\": \"W012A3CDE\",\n" +
      "        \"team_id\": \"T012AB3C4\",\n" +
      "        \"name\": \"spengler\",\n" +
      "        \"deleted\": false,\n" +
      "        \"color\": \"9f69e7\",\n" +
      "        \"real_name\": \"Egon Spengler\",\n" +
      "        \"tz\": \"America/Los_Angeles\",\n" +
      "        \"tz_label\": \"Pacific Daylight Time\",\n" +
      "        \"tz_offset\": -25200,\n" +
      "        \"is_admin\": true,\n" +
      "        \"is_owner\": false,\n" +
      "        \"is_primary_owner\": false,\n" +
      "        \"is_restricted\": false,\n" +
      "        \"is_ultra_restricted\": false,\n" +
      "        \"is_bot\": false,\n" +
      "        \"is_stranger\": false,\n" +
      "        \"updated\": 1502138686,\n" +
      "        \"is_app_user\": false,\n" +
      "        \"has_2fa\": false,\n" +
      "        \"locale\": \"en-US\"\n" +
      "    }\n" +
      "}";

  @Test
  public void itDoesDeserializeErrorResponse() throws Exception {
    SlackErrorResponse errorResponse = TestMappers.OBJECT_MAPPER.readValue(ERROR_RESPONSE, SlackErrorResponse.class);

    assertThat(errorResponse.isOk()).isFalse();
    assertThat(errorResponse.getError().getError()).isNotEmpty();
    assertThat(errorResponse.getError().getType()).isEqualTo(SlackErrorType.UNKNOWN);
  }

  @Test
  public void itDoesDeserializeUserResponse() throws Exception {
    UsersInfoResponse userResponse = TestMappers.OBJECT_MAPPER.readValue(USER_RESPONSE, UsersInfoResponse.class);

    assertThat(userResponse.isOk()).isTrue();
    assertThat(userResponse.getUser().getUsername()).isPresent();
    assertThat(userResponse.getUser().getUsername().get()).isEqualTo("spengler");
  }
}
