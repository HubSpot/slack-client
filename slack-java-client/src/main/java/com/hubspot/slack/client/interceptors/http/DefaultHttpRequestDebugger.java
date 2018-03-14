package com.hubspot.slack.client.interceptors.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.slack.client.methods.SlackMethod;

@Singleton
public class DefaultHttpRequestDebugger implements RequestDebugger {
  private static final Logger LOG = LoggerFactory.getLogger(DefaultHttpRequestDebugger.class);

  @Inject
  public DefaultHttpRequestDebugger() {
  }

  @Override
  public void debug(
      long requestId,
      SlackMethod method,
      HttpRequest request
  ) {
    LOG.debug("REQ<{}> [{}]: Issuing request \n\n{}", requestId, method.getMethod(), HttpFormatter.formatRequest(request));
  }
}
