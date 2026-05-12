package com.hubspot.slack.client.methods.params.stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hubspot.slack.client.methods.params.stream.chunks.MarkdownTextChunk;
import org.junit.Test;

public class ChatStreamParamsValidationTest {

  private static final String CHANNNEL_ID = "C123";
  private static final String THREAD_TS = "1234.5678";
  private static final String TEST_TEXT = "hello";

  // ChatStartStreamParams: neither, one, or the other is valid; both fails

  @Test
  public void itBuildsStartStreamWithNeitherMarkdownNorChunks() {
    assertThatCode(() ->
        ChatStartStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setThreadTs(THREAD_TS)
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itBuildsStartStreamWithMarkdownTextOnly() {
    assertThatCode(() ->
        ChatStartStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setThreadTs(THREAD_TS)
          .setMarkdownText(TEST_TEXT)
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itBuildsStartStreamWithChunksOnly() {
    assertThatCode(() ->
        ChatStartStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setThreadTs(THREAD_TS)
          .addChunks(MarkdownTextChunk.builder().setText(TEST_TEXT).build())
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itFailsToBuildStartStreamWithBothMarkdownAndChunks() {
    assertThatThrownBy(() ->
        ChatStartStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setThreadTs(THREAD_TS)
          .setMarkdownText(TEST_TEXT)
          .addChunks(MarkdownTextChunk.builder().setText(TEST_TEXT).build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("mutually exclusive");
  }

  // ChatAppendStreamParams: exactly one of markdown or chunks required; both and neither fail

  @Test
  public void itBuildsAppendStreamWithMarkdownTextOnly() {
    assertThatCode(() ->
        ChatAppendStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setTs(THREAD_TS)
          .setMarkdownText(TEST_TEXT)
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itBuildsAppendStreamWithChunksOnly() {
    assertThatCode(() ->
        ChatAppendStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setTs(THREAD_TS)
          .addChunks(MarkdownTextChunk.builder().setText(TEST_TEXT).build())
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itFailsToBuildAppendStreamWithNeitherMarkdownNorChunks() {
    assertThatThrownBy(() ->
        ChatAppendStreamParams.builder().setChannel(CHANNNEL_ID).setTs(THREAD_TS).build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("Exactly one of markdownText or chunks must be provided");
  }

  @Test
  public void itFailsToBuildAppendStreamWithBothMarkdownAndChunks() {
    assertThatThrownBy(() ->
        ChatAppendStreamParams
          .builder()
          .setChannel(CHANNNEL_ID)
          .setTs(THREAD_TS)
          .setMarkdownText(TEST_TEXT)
          .addChunks(MarkdownTextChunk.builder().setText(TEST_TEXT).build())
          .build()
      )
      .isInstanceOf(IllegalStateException.class)
      .hasMessageContaining("Exactly one of markdownText or chunks must be provided");
  }
}
