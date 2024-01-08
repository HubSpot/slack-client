package com.hubspot.slack.client.models.interaction;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.actions.Option;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface OptionsListIF {
  List<Option> getOptions();
}
