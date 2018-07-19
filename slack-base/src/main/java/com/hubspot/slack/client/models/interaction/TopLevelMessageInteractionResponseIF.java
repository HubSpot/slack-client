package com.hubspot.slack.client.models.interaction;

import java.util.List;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.TopLevelMessageResponseType;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface TopLevelMessageInteractionResponseIF {
  TopLevelMessageResponseType getResponseType();

  boolean getReplaceOriginal();

  @Default
  default boolean getDeleteOriginal() {
    return false;
  }

  String getText();

  List<Attachment> getAttachments();
}
