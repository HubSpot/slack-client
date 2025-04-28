package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextUserIF extends RichTextElement {
  String TYPE = "user";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The ID of the user to be mentioned
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getUserId();

  /**
   * An object of six optional boolean properties that dictate style: bold, italic, strike, highlight, client_highlight, and unlink.
   * <br/><br/>
   * Type: Object (RichTextStyle)
   * <br/>
   * Required: false
   */
  Optional<RichTextStyle> getStyle();
}
