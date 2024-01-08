package com.hubspot.slack.client.models.actions;

import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface OptionIF {
  Optional<String> getText();
  String getValue();
}
