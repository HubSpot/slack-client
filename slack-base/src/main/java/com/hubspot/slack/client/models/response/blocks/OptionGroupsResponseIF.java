package com.hubspot.slack.client.models.response.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BaseSlackOptionsResponse;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
@HubSpotStyle
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface OptionGroupsResponseIF extends BaseSlackOptionsResponse {
    List<OptionGroup> getOptionGroups();
}
