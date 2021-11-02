package com.hubspot.slack.client.methods.params.conversation;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.methods.params.conversations.ConversationKickParams;

public class ConversationKickParamsSerializationTest extends SerializationTestBase {
  @Test
  public void itSerializesConversationKickParams() throws IOException {
    testSerialization("conversation_kick_params.json", ConversationKickParams.class);
  }
}
