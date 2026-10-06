package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import org.junit.Test;

public class CarouselTest {

  private static Card card(String title) {
    return Card.builder().setTitle(Text.of(TextType.PLAIN_TEXT, title)).build();
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    Carousel original = Carousel
      .builder()
      .addElements(card("First"))
      .addElements(card("Second"))
      .build();
    ObjectMapper mapper = ObjectMapperUtils.mapper();
    String serialized = mapper.writeValueAsString(original);

    assertThat(mapper.readValue(serialized, Block.class)).isInstanceOf(Carousel.class);
    assertThat(mapper.readValue(serialized, Carousel.class)).isEqualTo(original);
  }

  @Test
  public void itRejectsEmptyCarousel() {
    assertThatThrownBy(() -> Carousel.builder().build())
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("between 1 and 10");
  }

  @Test
  public void itRejectsMoreThanTenCards() {
    Carousel.Builder builder = Carousel.builder();
    for (int i = 0; i < 11; i++) {
      builder.addElements(card("Card " + i));
    }
    assertThatThrownBy(builder::build)
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("between 1 and 10");
  }
}
