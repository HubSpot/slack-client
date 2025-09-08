package com.hubspot.slack.client.methods.params.chat.workobject;

import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface ExternalRefIF {
  String getId();
  Optional<String> getType();
}
