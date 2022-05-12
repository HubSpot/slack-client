package com.hubspot.slack.client.methods.params.calls;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface CallsUpdateParamsIF {
  String getId();
  Optional<String> getDesktopAppJoinUrl();
  Optional<String> getJoinUrl();
  Optional<String> getTitle();
}
