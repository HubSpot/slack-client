package com.hubspot.slack.client.methods.params.views;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.models.response.views.ModalViewCommandResponse;
import java.io.IOException;
import org.junit.Test;

public class ViewResponseSerializationTest extends SerializationTestBase {

  @Test
  public void testOpenViewSerialization() throws IOException {
    testSerialization("open_view_response.json", ModalViewCommandResponse.class);
  }

  @Test
  public void testUpdateViewSerialization() throws IOException {
    testSerialization("update_view_response.json", ModalViewCommandResponse.class);
  }
}
