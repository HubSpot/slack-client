package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.richtext.RichTextBlock;
import java.util.List;
import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextIF extends Block {
  String TYPE = "rich_text";

  @Override
  @Derived
  default String getType() {
    return TYPE;
  }

  /**
   * An array of rich text objects - rich_text_section, rich_text_list, rich_text_preformatted, and rich_text_quote.
   * <br/><br/>
   * Type: Object[] (List of RichTextBlock)
   * <br/>
   * Required: true
   */
  @Parameter
  List<RichTextBlock> getElements();
}
