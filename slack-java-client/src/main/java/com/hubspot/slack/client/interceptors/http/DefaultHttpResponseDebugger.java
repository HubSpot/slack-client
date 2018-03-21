package com.hubspot.slack.client.interceptors.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.methods.SlackMethod;

@Singleton
public class DefaultHttpResponseDebugger implements ResponseDebugger {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultHttpResponseDebugger.class);

  @Inject
  public DefaultHttpResponseDebugger() {
  }

  @Override
  public void debugTransportException(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      Throwable exception
  ) {
    LOG.error("REQ<{}> [{}]: Failed because {}", requestId, method, exception.getCause().getMessage(), exception);
  }

  @Override
  public void debug(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      HttpResponse response
  ) {
    LOG.debug("REQ<{}> [{}]: Received \n\n{}", requestId, method, HttpFormatter.formatResponse(response));
  }

  @Override
  public void debugSlackApiError(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      HttpResponse failure
  ) {
    LOG.error("REQ<{}> [{}]: Failed interaction\n{}\n{}", request, method, HttpFormatter.formatRequest(request), HttpFormatter.formatResponse(failure));
  }

  @Override
  public void debugProcessingFailure(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      HttpResponse response,
      Throwable ex
  ) {
    LOG.error("REQ<{}> [{}]: Failed interaction\n{}\n{}", request, method, HttpFormatter.formatRequest(request), HttpFormatter.formatResponse(response), ex);
  }
}
