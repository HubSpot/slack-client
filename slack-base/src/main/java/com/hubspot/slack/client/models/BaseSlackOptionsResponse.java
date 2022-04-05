package com.hubspot.slack.client.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.interaction.BlocksLoadOptionsResponse;
import com.hubspot.slack.client.models.interaction.SlackOptionsResponse;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = SlackOptionsResponse.class, name = SlackOptionsResponse.TYPE),
    @JsonSubTypes.Type(value = BlocksLoadOptionsResponse.class, name = BlocksLoadOptionsResponse.TYPE),
})
public interface BaseSlackOptionsResponse {
}
