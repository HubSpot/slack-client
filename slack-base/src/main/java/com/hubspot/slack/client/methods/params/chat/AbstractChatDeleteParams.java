package com.hubspot.slack.client.methods.params.chat;

import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChatDeleteParams extends AbstractChatDeleteParamsBase {

  @Default
  @JsonProperty("as_user")
  public boolean getAsUser() {
    return false;
  }
}
