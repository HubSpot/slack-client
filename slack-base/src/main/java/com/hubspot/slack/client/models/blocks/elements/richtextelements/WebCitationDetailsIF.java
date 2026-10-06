package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * A {@code web} citation details object (the {@code details} of a citation element).
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/citation-element/#fields">Citation element — fields</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface WebCitationDetailsIF extends CitationDetails {
  String CITATION_TYPE = "web";

  @Override
  @Value.Derived
  default String getCitationType() {
    return CITATION_TYPE;
  }

  String getDisplayName();

  String getTitle();

  String getSnippet();
}
