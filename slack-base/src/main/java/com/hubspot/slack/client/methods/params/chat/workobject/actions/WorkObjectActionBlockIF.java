package com.hubspot.slack.client.methods.params.chat.workobject.actions;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface WorkObjectActionBlockIF {
  List<WorkObjectAction> getPrimaryActions();
  List<WorkObjectAction> getOverflowActions();
}
