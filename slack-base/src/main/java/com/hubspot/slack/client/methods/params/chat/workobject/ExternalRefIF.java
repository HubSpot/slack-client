package com.hubspot.slack.client.methods.params.chat.workobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface ExternalRefIF {
  String getId();
  Optional<String> getType();
}
