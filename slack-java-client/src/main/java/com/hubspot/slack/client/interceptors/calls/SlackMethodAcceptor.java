package com.hubspot.slack.client.interceptors.calls;

import com.hubspot.slack.client.methods.SlackMethod;
import java.util.function.BiPredicate;

public interface SlackMethodAcceptor extends BiPredicate<SlackMethod, Object> {
  String getFailureExplanation(SlackMethod method, Object params);
}
