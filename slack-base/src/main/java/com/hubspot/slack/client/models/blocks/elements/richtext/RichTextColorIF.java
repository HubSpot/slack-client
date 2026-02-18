package com.hubspot.slack.client.models.blocks.elements.richtext;

import static org.immutables.value.Value.Immutable;
import static org.immutables.value.Value.Parameter;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextColorIF extends RichTextElement {
  String TYPE = "color";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The hex value for the color.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getValue();
}
