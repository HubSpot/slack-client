package com.hubspot.slack.client.methods.params.assistant;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hubspot.immutables.validation.InvalidImmutableStateException;
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

  private static Iterable<String> loadingMessages(int count) {
    return IntStream
      .range(0, count)
      .mapToObj(i -> "message " + i)
      .collect(Collectors.toList());
  }
}
