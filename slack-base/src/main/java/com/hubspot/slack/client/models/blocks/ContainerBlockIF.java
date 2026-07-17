package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.Image;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface ContainerBlockIF extends Block {
  String TYPE = "container";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  Text getTitle();

  ImmutableList<Block> getChildBlocks();

  Optional<Text> getSubtitle();

  Optional<Image> getIcon();

  Optional<ContainerBlockWidth> getWidth();

  @JsonProperty("is_collapsible")
  Optional<Boolean> isCollapsible();

  @JsonProperty("default_collapsed")
  Optional<Boolean> isDefaultCollapsed();

  @Check
  default void check() {
    Preconditions.checkState(
      getTitle().getText().length() <=
      BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit(),
      "title cannot exceed %s characters",
      BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit()
    );
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
  }
}
