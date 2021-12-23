package com.hubspot.slack.client.models.blocks.options;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BaseSlackOptionsResponse;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@HubSpotStyle
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface SlackBlockKitOptionGroupsResponseIF extends BaseSlackOptionsResponse {
    List<SlackOptionGroup> getOptionGroups();
}
