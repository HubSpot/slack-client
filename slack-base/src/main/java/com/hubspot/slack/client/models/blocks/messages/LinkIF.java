package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.Block;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface LinkIF extends Block {
    String TYPE = "link";

    @Override
    @Value.Derived
    default String getType() {
        return TYPE;
    }

    String getUrl();

    Optional<String> getText();

    Optional<TextStyle> getStyle();
}
