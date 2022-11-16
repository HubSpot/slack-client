package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface TextIF extends MessageBlock{
    String TYPE = "text";

    @Override
    @Value.Derived
    default String getType() {
        return TYPE;
    }

    String getText();

    Optional<TextStyle> getStyle();
}