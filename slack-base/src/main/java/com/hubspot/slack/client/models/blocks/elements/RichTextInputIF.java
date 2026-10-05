package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextBlock;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's rich text input element lets users enter formatted text in a WYSIWYG composer.
 * Works with the {@code input} and {@code table} blocks; available on Modals and Home tabs.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/rich-text-input-element">Rich text input element</a>
 */
@Immutable
@HubSpotStyle
@Value.Enclosing
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextInputIF extends BlockElement, HasActionId {
  String TYPE = "rich_text_input";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getActionId();

  Optional<RichTextBlock> getInitialValue();

  Optional<DispatchActionConfigIF> getDispatchActionConfig();

  Optional<Boolean> getFocusOnLoad();

  Optional<Text> getPlaceholder();

  Optional<Integer> getMinLines();

  Optional<Integer> getMaxLines();

  @Immutable
  @HubSpotStyle
  @JsonNaming(SnakeCaseStrategy.class)
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonDeserialize(as = RichTextInput.DispatchActionConfig.class)
  interface DispatchActionConfigIF {
    List<String> getTriggerActionsOn();
  }
}
