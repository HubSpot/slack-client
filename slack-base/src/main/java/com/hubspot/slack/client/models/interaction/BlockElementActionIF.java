package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;
import com.hubspot.slack.client.models.interaction.json.BlockElementActionDeserializer;
import com.hubspot.slack.client.models.interaction.json.BlockElementActionSerializer;
import java.time.LocalDate;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(using = BlockElementActionDeserializer.class)
@JsonSerialize(using = BlockElementActionSerializer.class)
public interface BlockElementActionIF {
  String getBlockId();
  String getActionId();

  BlockElement getElement();

  Optional<String> getSelectedValue();

  @JsonProperty("selected_date")
  Optional<LocalDate> getSelectedDate();

  Optional<String> getActionTs();
}
