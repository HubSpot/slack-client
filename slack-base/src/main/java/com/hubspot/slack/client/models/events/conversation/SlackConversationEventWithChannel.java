package com.hubspot.slack.client.models.events.conversation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hubspot.slack.client.models.SlackChannel;
import org.immutables.value.Value;

public interface SlackConversationEventWithChannel extends SlackConversationEventCore {
    SlackChannel getChannel();

    @JsonIgnore
    @Value.Derived
    default String getChannelId() {
        return getChannel().getId();
    }
}
