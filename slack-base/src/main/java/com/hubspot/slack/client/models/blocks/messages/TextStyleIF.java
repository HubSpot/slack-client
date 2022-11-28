package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import org.immutables.value.Value.Default;

import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface TextStyleIF {
    @Default
    @JsonProperty("bold")
    default boolean isBold() {
        return false;
    }
    @Default
    @JsonProperty("italic")
    default boolean isItalic() {
        return false;
    }
    @Default
    @JsonProperty("strike")
    default boolean isStrikethrough() {
        return false;
    }
    @Default
    @JsonProperty("code")
    default boolean isCode() {
        return false;
    }
}
