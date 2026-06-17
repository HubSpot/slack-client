package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;
import com.hubspot.slack.client.models.blocks.elements.Image;
import com.hubspot.slack.client.models.blocks.objects.SlackIconObject;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface CardIF extends Block {
  String TYPE = "card";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  Optional<Image> getHeroImage();

  Optional<Image> getIcon();

  Optional<Text> getTitle();

  Optional<Text> getSubtitle();

  Optional<Text> getBody();

  Optional<Text> getSubtext();

  Optional<List<BlockElement>> getActions();

  Optional<SlackIconObject> getSlackIcon();

  @Check
  default void check() {
    Preconditions.checkState(
      getHeroImage().isPresent() ||
      getTitle().isPresent() ||
      getActions().isPresent() ||
      getBody().isPresent(),
      "A card block must have at least one of: hero_image, title, actions, or body"
    );
    Preconditions.checkState(
      !(getIcon().isPresent() && getSlackIcon().isPresent()),
      "A card block cannot have both icon and slack_icon"
    );
    getTitle()
      .ifPresent(title ->
        Preconditions.checkState(
          title.getText().length() <=
          BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit(),
          "title cannot exceed %s characters",
          BlockElementLengthLimits.MAX_CARD_TITLE_LENGTH.getLimit()
        )
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
    getBody()
      .ifPresent(body ->
        Preconditions.checkState(
          body.getText().length() <=
          BlockElementLengthLimits.MAX_CARD_BODY_LENGTH.getLimit(),
          "body cannot exceed %s characters",
          BlockElementLengthLimits.MAX_CARD_BODY_LENGTH.getLimit()
        )
      );
    getSubtext()
      .ifPresent(subtext ->
        Preconditions.checkState(
          subtext.getText().length() <=
          BlockElementLengthLimits.MAX_CARD_BODY_LENGTH.getLimit(),
          "subtext cannot exceed %s characters",
          BlockElementLengthLimits.MAX_CARD_BODY_LENGTH.getLimit()
        )
      );
    getActions()
      .ifPresent(actions ->
        Preconditions.checkState(
          actions.size() <= BlockElementLengthLimits.MAX_CARD_ACTIONS_COUNT.getLimit(),
          "A card block cannot have more than %s actions",
          BlockElementLengthLimits.MAX_CARD_ACTIONS_COUNT.getLimit()
        )
      );
    getBlockId()
      .ifPresent(blockId ->
        Preconditions.checkState(
          blockId.length() <=
          BlockElementLengthLimits.MAX_CARD_BLOCK_ID_LENGTH.getLimit(),
          "block_id cannot exceed %s characters",
          BlockElementLengthLimits.MAX_CARD_BLOCK_ID_LENGTH.getLimit()
        )
      );
  }
}
