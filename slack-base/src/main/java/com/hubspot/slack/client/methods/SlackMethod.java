package com.hubspot.slack.client.methods;

public interface SlackMethod {
  String getMethod();
  MethodWriteMode getWriteMode();
  RateLimitingTier getRateLimitingTier();
  JsonStatus jsonWhitelistStatus();
}
