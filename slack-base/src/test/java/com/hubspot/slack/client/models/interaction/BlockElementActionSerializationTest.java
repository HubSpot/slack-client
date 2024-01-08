package com.hubspot.slack.client.models.interaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.Test;

public class BlockElementActionSerializationTest extends SerializationTestBase {

  @Test
  public void testBlockSerialization() throws IOException {
    testSerialization("block_element_action.json", BlockElementAction.class);
  }

  @Test
  public void testBlockDeSerializationWithSelectedOption() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(
      "block_element_action_selected_option.json"
    );
    BlockElementAction jsonObject = ObjectMapperUtils
      .mapper()
      .readValue(rawJson, BlockElementAction.class);
    assertThat(jsonObject.getSelectedValue().isPresent()).isEqualTo(true);
    assertThat(jsonObject.getSelectedValue().get()).isEqualTo("value");
  }

  @Test
  public void testBlockDeSerializationWithSelectedDate() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(
      "block_element_action_selected_date.json"
    );
    BlockElementAction jsonObject = ObjectMapperUtils
      .mapper()
      .readValue(rawJson, BlockElementAction.class);
    assertThat(jsonObject.getSelectedDate().isPresent()).isEqualTo(true);
    assertThat(jsonObject.getSelectedDate().get())
      .isEqualTo(LocalDate.parse("2020-05-25"));
  }
}
