package com.hubspot.slack.client.models.events.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.models.events.SlackEvent;

public interface SlackGroupEventCore extends SlackEvent {
    @JsonProperty("channel")
    String getChannelId();

    //Group events do not have a ts, so we manually set it as null
    @Override
    default String getTs() {
        return null;
    }
}
