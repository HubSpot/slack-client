package com.hubspot.slack.client.interceptors.http;


import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.methods.SlackMethod;

public interface ResponseDebugger {
  void debug(
      long requestId,
      SlackMethod method,
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
}
