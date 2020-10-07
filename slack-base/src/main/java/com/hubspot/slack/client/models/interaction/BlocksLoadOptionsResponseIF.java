package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import org.immutables.value.Value.Immutable;

import java.util.List;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface BlocksLoadOptionsResponseIF {
  @JsonInclude(Include.NON_EMPTY)
  List<Option> getOptions();

  @JsonInclude(Include.NON_EMPTY)
  List<OptionGroup> getOptionGroups();
}
