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
public interface RichTextLinkIF extends RichTextElement {
  String TYPE = "link";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The link's url
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getUrl();

  /**
   * The text shown to the user (instead of the url). If no text is provided, the url is used.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getText();

  /**
   * Indicates whether the link is safe.
   * <br/><br/>
   * Type: Boolean
   * <br/>
   * Required: false
   */
  Optional<Boolean> getUnsafe();

  /**
   * An object containing four boolean properties: bold, italic, strike, and code.
   * <br/><br/>
   * Type: RichTextSimpleStyle
   * <br/>
   * Required: false
   */
  Optional<RichTextSimpleStyle> getStyle();
}
