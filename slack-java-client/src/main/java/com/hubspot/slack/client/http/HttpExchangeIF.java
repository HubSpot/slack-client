package com.hubspot.slack.client.http;

import java.time.Duration;

import org.immutables.value.Value.Immutable;

import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.immutables.styles.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface HttpExchangeIF {
  HttpRequest getRequest();
  HttpResponse getResponse();
  Duration getExchangeDuration();
}
