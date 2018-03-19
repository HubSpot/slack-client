package com.hubspot.slack.client.models.response;

import java.util.List;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.Message;

@Immutable
@HubSpotStyle
public interface MessagePageIF {
  int getTotal();
  Paging getPaging();
  List<Message> getMatches();
}
