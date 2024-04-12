package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.ModalViewPayload;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ViewResponseActionIF {
  @JsonProperty
  String getResponseAction();

  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Optional<ModalViewPayload> getView();

  @JsonProperty
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Map<String, String> getErrors();
}
