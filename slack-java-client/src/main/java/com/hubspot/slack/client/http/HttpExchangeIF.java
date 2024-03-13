package com.hubspot.slack.client.http;

import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.immutables.style.HubSpotStyle;
import java.time.Duration;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface HttpExchangeIF {
  HttpRequest getRequest();
  HttpResponse getResponse();
  Duration getExchangeDuration();
}
