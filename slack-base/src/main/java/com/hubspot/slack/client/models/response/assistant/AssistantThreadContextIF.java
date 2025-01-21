package com.hubspot.slack.client.models.response.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface AssistantThreadContextIF {
  Optional<String> getChannelId();

  Optional<String> getTeamId();

  Optional<String> getEnterpriseId();

  Optional<String> getThreadEntryPoint();

  @JsonProperty("force_search")
  Optional<Boolean> isForceSearch();
}
