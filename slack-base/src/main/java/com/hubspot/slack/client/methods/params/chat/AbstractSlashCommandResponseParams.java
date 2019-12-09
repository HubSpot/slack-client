package com.hubspot.slack.client.methods.params.chat;

import static com.hubspot.slack.client.models.TopLevelMessageResponseType.EPHEMERAL;

import org.immutables.value.Value.Default;

import com.hubspot.slack.client.models.TopLevelMessageResponseType;

public abstract class AbstractSlashCommandResponseParams extends AbstractChatMessageParams {
  @Default
  public TopLevelMessageResponseType getResponseType() {
    return EPHEMERAL;
  }
}
