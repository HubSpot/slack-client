package com.hubspot.slack.client.models.interaction;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.response.views.ViewResponseBase;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface BlockActionsIF extends SlackInteractiveCallback {
  String getTriggerId();
  Optional<String> getResponseUrl();
  Optional<LiteMessage> getMessage();
  Optional<ViewResponseBase> getView();
  @JsonProperty("actions")
  List<BlockElementAction> getElementActions();

  ViewStateValuesPayload getState();

  @Override
  default String getCallbackId() {
    return null;
  }

  @Override
  default String getActionTs() {
    return null;
  }

  @Override
  default SlackChannel getChannel() {
    return null;
  }
}
