package com.hubspot.slack.client.models.response;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.Message;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface MessagePageIF {
  int getTotal();
  Paging getPaging();
  List<Message> getMatches();
}
