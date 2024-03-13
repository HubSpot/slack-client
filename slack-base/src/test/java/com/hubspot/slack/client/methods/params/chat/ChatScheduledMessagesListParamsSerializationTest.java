package com.hubspot.slack.client.methods.params.chat;

import com.hubspot.slack.client.SerializationTestBase;
import java.io.IOException;
import org.junit.Test;

public class ChatScheduledMessagesListParamsSerializationTest
  extends SerializationTestBase {

  @Test
  public void itSerializesADefaultValue() throws IOException {
    testSerialization(
      "chat_scheduled_message_list_params.json",
      ChatScheduledMessagesListParams.class
    );
  }
}
