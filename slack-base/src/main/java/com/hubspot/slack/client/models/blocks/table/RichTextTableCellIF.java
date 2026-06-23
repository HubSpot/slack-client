package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.richtextelements.RichTextObject;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextTableCellIF extends DataTableCell {
  String TYPE = "rich_text";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @JsonProperty("elements")
  ImmutableList<RichTextObject> getElements();
}
