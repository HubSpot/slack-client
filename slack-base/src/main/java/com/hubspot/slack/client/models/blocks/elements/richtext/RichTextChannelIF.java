package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextChannelIF extends RichTextElement {
  String TYPE = "channel";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The ID of the channel to be mentioned.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getChannelId();

  /**
   * An object of six optional boolean properties that dictate style: bold, italic, strike, highlight, client_highlight, and unlink.
   * <br/><br/>
   * Type: Object (RichTextStyle)
   * <br/>
   * Required: false
   */
  Optional<RichTextStyle> getStyle();
}
