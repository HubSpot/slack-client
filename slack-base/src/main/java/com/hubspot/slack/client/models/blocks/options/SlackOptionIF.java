package com.hubspot.slack.client.models.blocks.options;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;
import com.hubspot.slack.client.models.blocks.objects.Text;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface SlackOptionIF {
    Text getText();
    String getValue();
}
