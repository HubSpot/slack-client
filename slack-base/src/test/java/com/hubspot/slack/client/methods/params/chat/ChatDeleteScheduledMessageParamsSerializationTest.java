package com.hubspot.slack.client.methods.params.chat;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;

public class ChatDeleteScheduledMessageParamsSerializationTest extends SerializationTestBase {

  @Test
  public void itSerializesADefaultValue() throws IOException {
    testSerialization("chat_delete_scheduled_message_params.json", ChatDeleteScheduledMessageParams.class);
  }

}