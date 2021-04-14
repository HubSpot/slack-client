package com.hubspot.slack.client.methods.params.chat;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;

public class ChatDeleteParamsTest extends SerializationTestBase {

  @Test
  public void itDoesntSerializeAsUserFieldIfItContainsDefaultValue() throws IOException {
    ChatDeleteParams chatDeleteParams = ChatDeleteParams.builder()
        .setChannelId("C1234567890")
        .setMessageToDeleteTs("123456789.9875")
        .build();
    String chatDeleteParamsSerialized = ObjectMapperUtils.mapper().writeValueAsString(chatDeleteParams);
    assertThat(chatDeleteParamsSerialized).doesNotContain("as_user");
  }
}
