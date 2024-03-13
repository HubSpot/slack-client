package com.hubspot.slack.client.interceptors.http;

import com.hubspot.horizon.HttpRequest;
import com.hubspot.slack.client.methods.SlackMethod;

public interface RequestDebugger {
  void debug(long requestId, SlackMethod method, HttpRequest request);
}
