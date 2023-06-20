package com.hubspot.slack.client.models;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.slack.client.jackson.AttachmentSerializer;
import com.hubspot.slack.client.models.blocks.Block;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.actions.Action;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonSerialize(using = AttachmentSerializer.class)
public interface AttachmentIF extends BlockOrAttachment{
  Optional<String> getFallback();
  Optional<String> getColor();
  Optional<String> getPretext();
  Optional<String> getAuthorName();
  Optional<String> getAuthorLink();
  Optional<String> getAuthorIcon();
  Optional<String> getTitle();
  Optional<String> getTitleLink();
  Optional<String> getText();
  Optional<String> getImageUrl();
  Optional<String> getAttachmentType();
  List<Field> getFields();
  Optional<String> getFooter();
  Optional<String> getFooterIcon();
  Optional<String> getThumbUrl();


  @JsonProperty("ts")
  Optional<String> getEpochSeconds();

  Optional<String> getCallbackId();

  List<Action> getActions();

  /** Slack will only markdown in fields whose names are included in this set. See {@link MarkdownSupportedFields}*/
  Set<String> getMrkdwnIn();
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  List<Block> getBlocks();
}
