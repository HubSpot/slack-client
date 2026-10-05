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

  private static final String ID_FIELD = "id";
  private static final String URL_FIELD = "url";
  private static final String IMAGE_URL_FIELD = "image_url";
  private static final String SLACK_FILE_FIELD = "slack_file";
  private static final String HERO_IMAGE_FIELD = "hero_image";

  private static final String FILE_ID = "F0123ABCD";
  private static final String FILE_URL =
    "https://files.slack.com/files-pri/T0123-F0123ABCD/preview.png";
  private static final String ALT_TEXT = "Preview";
  private static final String CARD_TITLE = "Landing page";

  @Test
  public void itOmitsUrlWhenOnlyIdIsSet() throws IOException {
    JsonNode node = toJson(idOnlySlackFile());

    assertThat(node.has(URL_FIELD)).isFalse();
    assertThat(node.get(ID_FIELD).asText()).isEqualTo(FILE_ID);
  }

  @Test
  public void itOmitsIdWhenOnlyUrlIsSet() throws IOException {
    JsonNode node = toJson(SlackFileObject.builder().setUrl(FILE_URL).build());

    assertThat(node.has(ID_FIELD)).isFalse();
    assertThat(node.get(URL_FIELD).asText()).isEqualTo(FILE_URL);
  }

  @Test
  public void itSerializesAnImageBlockSlackFileWithoutNullFields() throws IOException {
    Image image = Image
      .builder()
      .setAltText(ALT_TEXT)
      .setSlackFile(idOnlySlackFile())
      .build();

    JsonNode node = toJson(image);

    assertThat(node.has(IMAGE_URL_FIELD)).isFalse();
    assertThat(fieldNames(node.get(SLACK_FILE_FIELD))).containsExactly(ID_FIELD);
  }

  @Test
  public void itSerializesACardHeroImageSlackFileWithoutNullFields() throws IOException {
    Card card = Card
      .builder()
      .setTitle(Text.of(TextType.PLAIN_TEXT, CARD_TITLE))
      .setHeroImage(
        com.hubspot.slack.client.models.blocks.elements.Image
          .builder()
          .setAltText(ALT_TEXT)
          .setSlackFile(idOnlySlackFile())
          .build()
      )
      .build();

    JsonNode heroImage = toJson(card).get(HERO_IMAGE_FIELD);

    assertThat(heroImage.has(IMAGE_URL_FIELD)).isFalse();
    assertThat(fieldNames(heroImage.get(SLACK_FILE_FIELD))).containsExactly(ID_FIELD);
  }

  @Test
  public void itRoundTripsAnIdOnlySlackFile() throws IOException {
    SlackFileObject original = idOnlySlackFile();

    SlackFileObject roundTripped = MAPPER.readValue(
      MAPPER.writeValueAsString(original),
      SlackFileObject.class
    );

    assertThat(roundTripped).isEqualTo(original);
  }

  private static SlackFileObject idOnlySlackFile() {
    return SlackFileObject.builder().setId(FILE_ID).build();
  }

  private static JsonNode toJson(Object value) throws IOException {
    return MAPPER.readTree(MAPPER.writeValueAsString(value));
  }

  private static Iterable<String> fieldNames(JsonNode node) {
    return node::fieldNames;
  }
}
