package com.hubspot.slack.client.interceptors.http;

import com.hubspot.horizon.HttpRequest;
import com.hubspot.slack.client.methods.SlackMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultHttpRequestDebugger implements RequestDebugger {

  private static final Logger LOG = LoggerFactory.getLogger(
    DefaultHttpRequestDebugger.class
  );

  @Override
  public void debug(long requestId, SlackMethod method, HttpRequest request) {
    LOG.debug(
      "REQ<{}> [{}]: Issuing request \n\n{}",
      requestId,
      method.getMethod(),
      HttpFormatter.formatRequest(request)
    );
  }
}
