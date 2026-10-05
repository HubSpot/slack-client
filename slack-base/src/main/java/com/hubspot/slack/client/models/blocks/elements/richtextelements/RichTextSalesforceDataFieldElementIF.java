package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's Salesforce data field element renders as a Salesforce data field reference.
 * This is a rich text element, compatible only with the {@code rich_text} block. It must be
 * used within the {@code rich_text_list}, {@code rich_text_quote}, or {@code rich_text_section}
 * block element within the {@code rich_text} block's {@code elements} array.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/salesforce-data-field-element">Salesforce data field element</a>
 */
@Immutable
@HubSpotStyle
@Value.Enclosing
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextSalesforceDataFieldElementIF extends RichTextElement {
  String TYPE = "salesforce_data_field";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  String getSalesforceRecordId();

  Optional<String> getSalesforceFieldLabel();

  Optional<String> getSalesforceFieldApiName();

  Optional<Boolean> getSalesforceIncludeFieldLabel();

  Optional<StyleIF> getStyle();

  @Immutable
  @HubSpotStyle
  @JsonNaming(SnakeCaseStrategy.class)
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonDeserialize(as = RichTextSalesforceDataFieldElement.Style.class)
  interface StyleIF {
    Optional<Boolean> getBold();

    Optional<Boolean> getItalic();

    Optional<Boolean> getStrike();

    Optional<Boolean> getHighlight();

    Optional<Boolean> getClientHighlight();

    Optional<Boolean> getUnderline();

    Optional<Boolean> getUnlink();
  }
}
