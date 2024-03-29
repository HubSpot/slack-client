package com.hubspot.slack.client.methods.params.dnd;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface DndInfoParamsIF {
  @Value.Parameter
  @JsonProperty("user")
  Optional<String> getUserId();
}
