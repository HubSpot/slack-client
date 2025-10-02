package com.hubspot.slack.client.methods.params.chat.workobject.actions;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface WorkObjectActionIF {
  String getText();
  String getActionId();
  Optional<String> getValue();
  Optional<WorkObjectActionStyle> getStyle();
  Optional<String> getUrl();
  Optional<String> getAccessibilityLabel();
}
