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
public interface RichTextEmojiIF extends RichTextElement {
  String TYPE = "emoji";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * The name of the emoji; i.e. "wave" or "wave::skin-tone-2".
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter
  String getName();

  /**
   * Represents the unicode code point of the emoji, where applicable.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getUnicode();
}
