package com.hubspot.slack.client.interceptors.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hubspot.horizon.HttpRequest;
import com.hubspot.slack.client.methods.SlackMethod;

public class DefaultHttpRequestDebugger implements RequestDebugger {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultHttpRequestDebugger.class);

  @Override
  public void debug(
      long requestId,
      SlackMethod method,
      HttpRequest request
  ) {
    LOG.debug("REQ<{}> [{}]: Issuing request \n\n{}", requestId, method.getMethod(), request);
  }
}
