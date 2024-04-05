package com.hubspot.slack.client.models.events.includingfiles;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.events.SlackEventMessage;
import com.hubspot.slack.client.models.events.SlackEventMessageExtendedBase;
import com.hubspot.slack.client.models.files.SlackFile;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonDeserialize(as = SlackEventMessage.class)
public abstract class AbstractSlackEventMessage extends SlackEventMessageExtendedBase {

  public abstract List<SlackFile> getFiles();
}
