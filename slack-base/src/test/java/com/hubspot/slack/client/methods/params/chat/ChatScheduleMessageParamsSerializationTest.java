package com.hubspot.slack.client.methods.params.chat;

import java.io.IOException;
import org.junit.Test;
import com.hubspot.slack.client.SerializationTestBase;

public class ChatScheduleMessageParamsSerializationTest extends SerializationTestBase {

  @Test
  public void itSerializesADefaultValue() throws IOException {
    testSerialization("chat_schedule_message_params.json", ChatScheduleMessageParams.class);
  }

}
