package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Parameter;

@Value.Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextQuoteIF extends RichTextBlock {
  String TYPE = "rich_text_quote";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * An array of rich text elements.
   * <br/><br/>
   * Type: Object[] (List of RichTextElement)
   * <br/>
   * Required: true
   */
  @Parameter
  List<RichTextElement> getElements();

  /**
   * Number of pixels of border thickness.
   * <br/><br/>
   * Type: Integer
   * <br/>
   * Required: false
   */
  Optional<Integer> getBorder();
}
