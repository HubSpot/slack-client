package com.hubspot.slack.client.models.blocks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.io.IOException;
import org.junit.Test;

public class VideoBlockTest {

  @Test
  public void itDeserializesFromJson() throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile("video_block.json");
    Block block = ObjectMapperUtils.mapper().readValue(rawJson, Block.class);
    assertThat(block).isInstanceOf(VideoBlock.class);

    VideoBlock video = ObjectMapperUtils.mapper().readValue(rawJson, VideoBlock.class);
    assertThat(video.getAltText()).isEqualTo("How to use Slack.");
    assertThat(video.getTitle().getText()).isEqualTo("How to use Slack.");
    assertThat(video.getThumbnailUrl()).isEqualTo("https://example.com/thumb.png");
    assertThat(video.getVideoUrl()).isEqualTo("https://www.youtube.com/embed/abc123");
    assertThat(video.getTitleUrl()).contains("https://www.youtube.com/watch?v=abc123");
    assertThat(video.getProviderName()).contains("YouTube");
    assertThat(video.getAuthorName()).contains("Slack");
  }

  @Test
  public void itSerializesAndDeserializes() throws IOException {
    VideoBlock original = VideoBlock
      .builder()
      .setAltText("A short demo")
      .setTitle(Text.of(TextType.PLAIN_TEXT, "Demo"))
      .setThumbnailUrl("https://example.com/thumb.png")
      .setVideoUrl("https://www.youtube.com/embed/abc123")
      .build();
    ObjectMapper mapper = ObjectMapperUtils.mapper();
    String serialized = mapper.writeValueAsString(original);
    VideoBlock deserialized = mapper.readValue(serialized, VideoBlock.class);
    assertThat(deserialized).isEqualTo(original);
  }

  @Test
  public void itAcceptsUppercaseHttpsScheme() {
    VideoBlock video = VideoBlock
      .builder()
      .setAltText("a")
      .setTitle(Text.of(TextType.PLAIN_TEXT, "t"))
      .setThumbnailUrl("https://example.com/t.png")
      .setVideoUrl("HTTPS://example.com/v")
      .build();
    assertThat(video.getVideoUrl()).isEqualTo("HTTPS://example.com/v");
  }

  @Test
  public void itRejectsNonHttpsVideoUrl() {
    assertThatThrownBy(() ->
        VideoBlock
          .builder()
          .setAltText("a")
          .setTitle(Text.of(TextType.PLAIN_TEXT, "t"))
          .setThumbnailUrl("https://example.com/t.png")
          .setVideoUrl("http://insecure.example.com/v")
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("HTTPS");
  }

  @Test
  public void itRejectsNonPlainTextTitle() {
    assertThatThrownBy(() ->
        VideoBlock
          .builder()
          .setAltText("a")
          .setTitle(Text.of(TextType.MARKDOWN, "t"))
          .setThumbnailUrl("https://example.com/t.png")
          .setVideoUrl("https://example.com/v")
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("plain_text");
  }

  @Test
  public void itRejectsAuthorNameOver50Chars() {
    String longName = new String(new char[50]).replace('\0', 'a');
    assertThatThrownBy(() ->
        VideoBlock
          .builder()
          .setAltText("a")
          .setTitle(Text.of(TextType.PLAIN_TEXT, "t"))
          .setThumbnailUrl("https://example.com/t.png")
          .setVideoUrl("https://example.com/v")
          .setAuthorName(longName)
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("author_name");
  }

  // Slack docs: title/description "must be less than 200 characters" — so 199 is allowed, 200 is not.
  @Test
  public void itAcceptsTitleOf199Chars() {
    String title199 = new String(new char[199]).replace('\0', 'a');
    VideoBlock video = VideoBlock
      .builder()
      .setAltText("a")
      .setTitle(Text.of(TextType.PLAIN_TEXT, title199))
      .setThumbnailUrl("https://example.com/t.png")
      .setVideoUrl("https://example.com/v")
      .build();
    assertThat(video.getTitle().getText()).hasSize(199);
  }

  @Test
  public void itRejectsTitleOf200Chars() {
    String title200 = new String(new char[200]).replace('\0', 'a');
    assertThatThrownBy(() ->
        VideoBlock
          .builder()
          .setAltText("a")
          .setTitle(Text.of(TextType.PLAIN_TEXT, title200))
          .setThumbnailUrl("https://example.com/t.png")
          .setVideoUrl("https://example.com/v")
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("title must be less than 200");
  }
}
