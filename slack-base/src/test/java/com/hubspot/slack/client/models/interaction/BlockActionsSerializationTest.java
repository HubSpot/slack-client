package com.hubspot.slack.client.models.interaction;

import com.hubspot.slack.client.SerializationTestBase;
import java.io.IOException;
import org.junit.Test;

public class BlockActionsSerializationTest extends SerializationTestBase {

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("block_actions.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithoutState() throws IOException {
    testSerialization("block_actions_without_state.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithoutContainer() throws IOException {
    testSerialization("block_actions_without_container.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithMessageContainer() throws IOException {
    testSerialization("block_actions_with_message_container.json", BlockActions.class);
  }

  @Test
  public void testBlockSerializationWithViewContainer() throws IOException {
    testSerialization("block_actions_with_view_container.json", BlockActions.class);
  }
}
