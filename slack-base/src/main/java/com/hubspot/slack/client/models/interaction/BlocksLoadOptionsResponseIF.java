package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BaseSlackOptionsResponse;
import com.hubspot.slack.client.models.blocks.SlackBlockNormalizer;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import java.util.List;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface BlocksLoadOptionsResponseIF extends BaseSlackOptionsResponse {
  @JsonInclude(Include.NON_EMPTY)
  List<Option> getOptions();

  @JsonInclude(Include.NON_EMPTY)
  List<OptionGroup> getOptionGroups();

  @Check
  default BlocksLoadOptionsResponseIF validate() {
    return SlackBlockNormalizer.normalize(this);
  }
}
