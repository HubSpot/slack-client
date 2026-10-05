package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import org.junit.Test;

public class SlackFileObjectSerializationTest {

  private static final ObjectMapper MAPPER = ObjectMapperUtils.mapper();
  private static final String FILE_ID = "F0123ABCD";
  private static final String FILE_URL =
    "https://files.slack.com/files-pri/T0123-F0123ABCD/preview.png";

  @Test
  public void itOmitsUrlWhenOnlyIdIsSet() throws IOException {
    JsonNode node = toJson(SlackFileObject.builder().setId(FILE_ID).build());

    assertThat(node.has("url")).isFalse();
    assertThat(node.get("id").asText()).isEqualTo(FILE_ID);
  }

  @Test
  public void itOmitsIdWhenOnlyUrlIsSet() throws IOException {
    JsonNode node = toJson(SlackFileObject.builder().setUrl(FILE_URL).build());

    assertThat(node.has("id")).isFalse();
    assertThat(node.get("url").asText()).isEqualTo(FILE_URL);
  }

  @Test
  public void itSerializesAnImageBlockSlackFileWithoutNullFields() throws IOException {
    Image image = Image
      .builder()
      .setAltText("Preview")
      .setSlackFile(SlackFileObject.builder().setId(FILE_ID).build())
      .build();

    JsonNode node = toJson(image);

    assertThat(node.has("image_url")).isFalse();
    assertThat(fieldNames(node.get("slack_file"))).containsExactly("id");
  }

  @Test
  public void itSerializesACardHeroImageSlackFileWithoutNullFields() throws IOException {
    Card card = Card
      .builder()
      .setTitle(Text.of(TextType.PLAIN_TEXT, "Landing page"))
      .setHeroImage(
        com.hubspot.slack.client.models.blocks.elements.Image
          .builder()
          .setAltText("Preview")
          .setSlackFile(SlackFileObject.builder().setId(FILE_ID).build())
          .build()
      )
      .build();

    JsonNode heroImage = toJson(card).get("hero_image");

    assertThat(heroImage.has("image_url")).isFalse();
    assertThat(fieldNames(heroImage.get("slack_file"))).containsExactly("id");
  }

  @Test
  public void itRoundTripsAnIdOnlySlackFile() throws IOException {
    SlackFileObject original = SlackFileObject.builder().setId(FILE_ID).build();

    SlackFileObject roundTripped = MAPPER.readValue(
      MAPPER.writeValueAsString(original),
      SlackFileObject.class
    );

    assertThat(roundTripped).isEqualTo(original);
  }

  private static JsonNode toJson(Object value) throws IOException {
    return MAPPER.readTree(MAPPER.writeValueAsString(value));
  }

  private static Iterable<String> fieldNames(JsonNode node) {
    return node::fieldNames;
  }
}
