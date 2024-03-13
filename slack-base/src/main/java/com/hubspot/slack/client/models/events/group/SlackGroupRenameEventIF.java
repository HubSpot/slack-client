package com.hubspot.slack.client.models.events.group;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.conversation.SlackConversationEventWithChannel;
import org.immutables.value.Value;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackGroupRenameEvent.class)
@Value.Immutable
@HubSpotStyle
public interface SlackGroupRenameEventIF extends SlackConversationEventWithChannel {}
