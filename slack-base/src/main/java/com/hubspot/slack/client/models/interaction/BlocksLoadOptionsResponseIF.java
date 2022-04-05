package com.hubspot.slack.client.models.interaction;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BaseSlackOptionsResponse;
import com.hubspot.slack.client.models.blocks.SlackBlockNormalizer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface BlocksLoadOptionsResponseIF extends BaseSlackOptionsResponse {
  String TYPE = "blocks_load_options_response";

  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @JsonInclude(Include.NON_EMPTY)
  List<Option> getOptions();

  @JsonInclude(Include.NON_EMPTY)
  List<OptionGroup> getOptionGroups();

  @Check
  default BlocksLoadOptionsResponseIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}
