package com.hubspot.slack.client.methods.params.chat.workobject.flexpane;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.actions.WorkObjectAction;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface WorkObjectFlexpaneErrorIF {
  WorkObjectFlexpaneErrorStatus getStatus();
  Optional<String> getCustomTitle();
  Optional<String> getCustomMessage();
  MessageFormat getMessageFormat();
  List<WorkObjectAction> getActions();
}
