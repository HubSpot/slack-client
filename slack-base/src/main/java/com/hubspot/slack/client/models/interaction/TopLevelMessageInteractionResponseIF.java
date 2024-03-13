package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.TopLevelMessageResponseType;
import com.hubspot.slack.client.models.blocks.Block;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

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

  Optional<String> getText();

  List<Attachment> getAttachments();

  List<Block> getBlocks();

  @Check
  default void check() {
    if (!getText().isPresent() && getAttachments().isEmpty()) {
      throw new IllegalStateException("Must supply either text or attachments");
    }
  }
}
