package com.hubspot.slack.client.models.users;

import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface ProfileFieldIF {
  String getValue();

  Optional<String> getAlt();
}
