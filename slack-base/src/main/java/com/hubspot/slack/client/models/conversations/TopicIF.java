package com.hubspot.slack.client.models.conversations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TopicIF {
  @JsonProperty("value")
  Optional<String> getValue();

  @JsonProperty("creator")
  Optional<String> getCreator();

  @JsonProperty("last_set")
  Optional<Long> getLastSet();
}
