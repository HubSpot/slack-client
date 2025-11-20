package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.BroadcastRange;
import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextBroadcastIF extends RichTextElement {
  String TYPE = "broadcast";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The range of the broadcast; value can be here, channel, or everyone. Using here notifies only the active members of a channel; channel notifies all members of a channel; everyone notifies every person in the #general channel.
   * <br/><br/>
   * Type: String (enum BroadcastRange)
   * <br/>
   * Required: true
   */
  @Parameter
  BroadcastRange getRange();
}
