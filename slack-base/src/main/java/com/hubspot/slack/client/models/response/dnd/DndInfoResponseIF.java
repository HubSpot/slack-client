package com.hubspot.slack.client.models.response.dnd;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.json.InstantDeserializer;
import com.hubspot.slack.client.models.json.InstantSerializer;
import com.hubspot.slack.client.models.response.SlackResponse;
import org.immutables.value.Value;

import java.time.Instant;
import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface DndInfoResponseIF extends SlackResponse {
  boolean isDndEnabled();

  @JsonProperty("next_dnd_start_ts")
  @JsonSerialize(contentUsing = InstantSerializer.class)
  @JsonDeserialize(contentUsing = InstantDeserializer.class)
  Optional<Instant> getNextDndStart();

  @JsonProperty("next_dnd_end_ts")
  @JsonSerialize(contentUsing = InstantSerializer.class)
  @JsonDeserialize(contentUsing = InstantDeserializer.class)
  Optional<Instant> getNextDndEnd();

  boolean isSnoozeEnabled();

  @JsonProperty("snooze_endtime")
  @JsonSerialize(contentUsing = InstantSerializer.class)
  @JsonDeserialize(contentUsing = InstantDeserializer.class)
  Optional<Instant> getSnoozeEnd();

  Optional<Integer> getSnoozeRemaining();
}
