package com.hubspot.slack.client.models.events.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.models.events.SlackEvent;

public interface SlackConversationEventCore extends SlackEvent {
    @JsonProperty("channel")
    String getChannelId();

    //Conversation events do not have a ts, so we manually set it as null
    @Override
    default String getTs() {
        return null;
    }
}
