package com.hubspot.slack.client.models.events.bot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.response.views.HomeTabViewResponseIF;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackAppHomeOpenedEvent.class)
public interface SlackAppHomeOpenedEventIF extends SlackEvent, HasUser {
    Optional<String> getTab();

    Optional<HomeTabViewResponseIF> getView();

    @JsonProperty("user")
    String getUserId();

    @JsonProperty("channel")
    String getChannelId();

    //Home opened events do not have a ts, so we manually set it as null
    @Override
    default String getTs() {
        return null;
    }

}
