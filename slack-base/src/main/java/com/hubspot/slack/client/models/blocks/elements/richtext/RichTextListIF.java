package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.ListStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextListIF {
  String TYPE = "rich_text_list";

  default String getType() {
    return TYPE;
  }

  /**
   * Either bullet or ordered, the latter meaning a numbered list.
   * <br/><br/>
   * Type: String (enum of ListStyle)
   * <br/>
   * Required: true
   */
  @Parameter(order = 0)
  ListStyle getStyle();

  /**
   * An array of rich_text_section objects containing two properties: type, which is "rich_text_section", and elements, which is an array of rich text element objects.
   * <br/><br/>
   * Type: Object[] (List of RichTextSection)
   * <br/>
   * Required: true
   */
  @Parameter(order = 1)
  List<RichTextSection> getElements();

  /**
   * Number of pixels to indent the list.
   * <br/><br/>
   * Type: Number
   * <br/>
   * Required: false
   */
  Optional<Integer> getIndent();

  /**
   * Number of pixels to offset the list.
   * <br/><br/>
   * Type: Integer
   * <br/>
   * Required: false
   */
  Optional<Integer> getOffset();

  /**
   * Number of pixels of border thickness.
   * <br/><br/>
   * Type: Integer
   * <br/>
   * Required: false
   */
  Optional<Integer> getBorder();
}
