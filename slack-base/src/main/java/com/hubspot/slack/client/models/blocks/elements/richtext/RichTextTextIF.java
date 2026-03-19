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
public interface RichTextTextIF extends RichTextElement {
  String TYPE = "text";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The text shown to the user
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getText();

  /**
   * An object containing four boolean fields, none of which are required: bold, italic, strike, and code.
   * <br/><br/>
   * Type: Object (RichTextSimpleStyle)
   * <br/>
   * Required: false
   */
  Optional<RichTextSimpleStyle> getStyle();
}
