package com.hubspot.slack.client.methods.params.chat;

import static com.hubspot.slack.client.models.TopLevelMessageResponseType.EPHEMERAL;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.TopLevelMessageResponseType;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractSlashCommandResponseParams
  extends AbstractChatMessageParams {

  @Default
  public TopLevelMessageResponseType getResponseType() {
    return EPHEMERAL;
  }
}
