package com.hubspot.slack.client.methods;

public interface RateLimitingTier {
  int getMinutelyAllowance();
}
