package com.hubspot.slack.client.methods.params.assistant;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hubspot.immutables.validation.InvalidImmutableStateException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public class SetThreadStatusParamsValidationTest {

  private static final String CHANNEL_ID = "C123";
  private static final String THREAD_TS = "1234.5678";
  private static final String STATUS = "is thinking...";

  @Test
  public void itBuildsWithoutLoadingMessages() {
    assertThatCode(() ->
        SetThreadStatusParams
          .builder()
          .setChannelId(CHANNEL_ID)
          .setThreadTs(THREAD_TS)
          .setStatus(STATUS)
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itBuildsWithMaxLoadingMessages() {
    assertThatCode(() ->
        SetThreadStatusParams
          .builder()
          .setChannelId(CHANNEL_ID)
          .setThreadTs(THREAD_TS)
          .setStatus(STATUS)
          .addAllLoadingMessages(
            loadingMessages(SetThreadStatusParams.MAX_LOADING_MESSAGES)
          )
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itFailsToBuildWithTooManyLoadingMessages() {
    assertThatThrownBy(() ->
        SetThreadStatusParams
          .builder()
          .setChannelId(CHANNEL_ID)
          .setThreadTs(THREAD_TS)
          .setStatus(STATUS)
          .addAllLoadingMessages(
            loadingMessages(SetThreadStatusParams.MAX_LOADING_MESSAGES + 1)
          )
          .build()
      )
      .isInstanceOf(InvalidImmutableStateException.class)
      .hasMessageContaining("loadingMessages");
  }

  @Test
  public void itBuildsWithOptionalFields() {
    assertThatCode(() ->
        SetThreadStatusParams
          .builder()
          .setChannelId(CHANNEL_ID)
          .setThreadTs(THREAD_TS)
          .setStatus(STATUS)
          .setIconEmoji(Optional.of(":robot_face:"))
          .setUsername(Optional.of("My Bot"))
          .build()
      )
      .doesNotThrowAnyException();
  }

  @Test
  public void itFailsToBuildWithBothIconEmojiAndIconUrl() {
    assertThatThrownBy(() ->
        SetThreadStatusParams
          .builder()
          .setChannelId(CHANNEL_ID)
          .setThreadTs(THREAD_TS)
          .setStatus(STATUS)
          .setIconEmoji(Optional.of(":robot_face:"))
          .setIconUrl(Optional.of("https://example.com/icon.png"))
          .build()
      )
      .isInstanceOf(InvalidImmutableStateException.class)
      .hasMessageContaining("iconEmoji")
      .hasMessageContaining("iconUrl");
  }

  private static Iterable<String> loadingMessages(int count) {
    return IntStream
      .range(0, count)
      .mapToObj(i -> "message " + i)
      .collect(Collectors.toList());
  }
}
