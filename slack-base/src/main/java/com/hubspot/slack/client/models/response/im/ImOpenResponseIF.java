package com.hubspot.slack.client.models.response.im;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.channel.ImChannel;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.Optional;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ImOpenResponseIF extends SlackResponse {
  @Default
  default boolean isNoOp() {
    return false;
  }

  @Default
  default boolean isAlreadyOpen() {
    return false;
  }

  Optional<ImChannel> getChannel();
}
