package com.hubspot.slack.client.methods.params.dialog;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.styles.HubSpotStyle;
import com.hubspot.slack.client.models.dialog.SlackDialog;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public interface DialogOpenParamsIF {
  String getTriggerId();
  SlackDialog getDialog();
}
