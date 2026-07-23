package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.Image;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextBlock;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public interface ContainerBlockIF extends Block {
  String TYPE = "container";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  Optional<Text> getTitle();

  Optional<RichTextBlock> getRichTextTitle();

  ImmutableList<Block> getChildBlocks();

  Optional<Text> getSubtitle();

  Optional<Image> getIcon();

  Optional<ContainerBlockWidth> getWidth();

  Optional<Boolean> getHasHeaderDivider();

  @JsonProperty("is_collapsible")
  Optional<Boolean> isCollapsible();

  @JsonProperty("default_collapsed")
  Optional<Boolean> isDefaultCollapsed();

  @Check
  default void check() {
    Preconditions.checkState(
      getTitle().isPresent() || getRichTextTitle().isPresent(),
      "container block must have either title or rich_text_title"
    );
    getTitle()
      .ifPresent(title -> {
        Preconditions.checkState(
          title.getType() == TextType.PLAIN_TEXT,
          "title must use plain_text formatting"
        );
        Preconditions.checkState(
          title.getText().length() <=
          BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit(),
          "title cannot exceed %s characters",
          BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit()
        );
      });
    getSubtitle()
      .ifPresent(subtitle ->
        Preconditions.checkState(
          subtitle.getText().length() <=
          BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit(),
          "subtitle cannot exceed %s characters",
          BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit()
        )
      );
    Preconditions.checkState(
      getChildBlocks().size() <=
      BlockElementLengthLimits.MAX_CONTAINER_CHILD_BLOCKS.getLimit(),
      "child_blocks cannot exceed %s blocks",
      BlockElementLengthLimits.MAX_CONTAINER_CHILD_BLOCKS.getLimit()
    );
    Preconditions.checkState(
      getChildBlocks().stream().noneMatch(b -> TYPE.equals(b.getType())),
      "child_blocks cannot contain container blocks"
    );
    Preconditions.checkState(
      !isDefaultCollapsed().orElse(false) || isCollapsible().orElse(false),
      "default_collapsed requires is_collapsible to be true"
    );
    Preconditions.checkState(
      !isCollapsible().orElse(false) || !getHasHeaderDivider().orElse(false),
      "has_header_divider does not apply to collapsible blocks"
    );
  }
}
