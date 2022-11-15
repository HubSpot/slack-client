package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface LinkIF extends MessageBlock{
    String TYPE = "link";

    @Override
    @Value.Derived
    default String getType() {
        return TYPE;
    }

    String getUrl();

    String getText();
}
