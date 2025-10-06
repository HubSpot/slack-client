package com.hubspot.slack.client.methods.params.chat.workobject.flexpane;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface WorkObjectFlexpaneParamsIF {
  String getTriggerId();
  FlexPaneMetadata getMetadata();
  Optional<Boolean> getIsUserAuthRequired();
  Optional<String> getUserAuthUrl();
  Optional<WorkObjectFlexpaneError> getError();
}
