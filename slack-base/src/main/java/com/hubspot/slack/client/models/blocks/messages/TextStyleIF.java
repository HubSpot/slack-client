package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface TextStyleIF {
    @JsonProperty("bold")
    Optional<Boolean> isBold();
    @JsonProperty("italic")
    Optional<Boolean> isItalic();
    @JsonProperty("strike")
    Optional<Boolean> isStrike();
    @JsonProperty("code")
    Optional<Boolean> isCode();
}
