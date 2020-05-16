package com.hubspot.slack.client.models.response.views;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.slack.client.models.response.views.json.StateActionValueDeserializer;
import com.hubspot.slack.client.models.response.views.json.StateActionValueSerializer;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonDeserialize(using = StateActionValueDeserializer.class)
@JsonSerialize(using = StateActionValueSerializer.class)
public interface StateActionValueIF {
  String getBlockElementType();

  Object getBlockElementValue();
}
