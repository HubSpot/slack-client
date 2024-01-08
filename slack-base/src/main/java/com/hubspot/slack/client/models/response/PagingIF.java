package com.hubspot.slack.client.models.response;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface PagingIF {
  int getCount();
  int getTotal();
  int getPage();
  int getPages();
}
