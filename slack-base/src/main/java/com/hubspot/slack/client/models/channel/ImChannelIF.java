package com.hubspot.slack.client.models.channel;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ImChannelIF {
  String getId();
  int getCreated();
  boolean getIsIm();
  boolean getIsOrgShared();
  String getUser();
  boolean getIsOpen();
}
