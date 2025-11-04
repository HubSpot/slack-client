package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.ExternalRef;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface MessageAttachmentContainerIF extends Container {
  String getMessageTs();

  String getChannelId();

  @JsonProperty("is_ephemeral")
  boolean isEphemeral();

  @JsonProperty("is_app_unfurl")
  boolean isAppUnfurl();

  int getAttachmentId();

  Optional<ExternalRef> getExternalRef();

  Optional<String> getEntityUrl();
}
