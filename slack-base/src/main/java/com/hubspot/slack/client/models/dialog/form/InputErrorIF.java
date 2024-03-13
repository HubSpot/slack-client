package com.hubspot.slack.client.models.dialog.form;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface InputErrorIF {
  String getName();
  String getError();
}
