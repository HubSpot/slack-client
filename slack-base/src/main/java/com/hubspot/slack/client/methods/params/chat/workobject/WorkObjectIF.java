package com.hubspot.slack.client.methods.params.chat.workobject;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.EntityTypeSerializer;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface WorkObjectIF {
  String getAppUnfurlUrl();
  String getUrl();
  ExternalRef getExternalRef();

  @JsonSerialize(using = EntityTypeSerializer.class)
  EntityType getEntityType();

  EntityPayload getEntityPayload();
}
