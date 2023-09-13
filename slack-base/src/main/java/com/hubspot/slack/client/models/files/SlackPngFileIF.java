package com.hubspot.slack.client.models.files;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackPngFile.class)
public interface SlackPngFileIF extends SlackImageFile {
    @Value.Default
    @Override
    default SlackFileType getFiletype() {
        return SlackFileType.PNG;
    }
}
