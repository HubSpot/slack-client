package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.ModalViewPayload;
import org.immutables.value.Value;

import java.util.Map;
import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
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
