package com.hubspot.slack.client.interceptors.calls;

import java.util.function.BiPredicate;

import com.hubspot.slack.client.methods.SlackMethod;

public interface SlackMethodAcceptor extends BiPredicate<SlackMethod, Object> {
  String getFailureExplanation(SlackMethod method, Object params);
}
