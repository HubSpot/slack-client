package com.hubspot.slack.client.models.dialog.form;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface InputErrorsIF {
  List<InputError> getErrors();
}
