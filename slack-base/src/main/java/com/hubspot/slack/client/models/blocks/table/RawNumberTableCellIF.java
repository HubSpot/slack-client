package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RawNumberTableCellIF extends DataTableCell {
  String TYPE = "raw_number";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter(order = 1)
  double getValue();

  @Value.Parameter(order = 2)
  String getText();
}
