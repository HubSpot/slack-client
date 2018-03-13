package com.hubspot.slack.client.models.dialog.form;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface InputErrorIF {
  String getName();
  String getError();
}
