package com.hubspot.slack.client.interceptors.http;

import com.google.common.base.Stopwatch;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.methods.SlackMethod;

public interface ResponseDebugger {
  void debug(
    long requestId,
    SlackMethod method,
    Stopwatch timer,
    HttpRequest request,
    HttpResponse response
  );

  void debugTransportException(
    long requestId,
    SlackMethod method,
    HttpRequest request,
    Throwable exception
  );

  void debugSlackApiError(
    long requestId,
    SlackMethod method,
    HttpRequest request,
    HttpResponse failure
  );

  void debugProcessingFailure(
    long requestId,
    SlackMethod method,
    HttpRequest request,
    HttpResponse response,
    Throwable ex
  );

  void debugProactiveRateLimit(long requestId, SlackMethod method, HttpRequest request);
}
