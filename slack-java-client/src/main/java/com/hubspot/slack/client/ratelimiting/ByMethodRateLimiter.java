package com.hubspot.slack.client.ratelimiting;

import com.google.common.util.concurrent.RateLimiter;
import com.hubspot.slack.client.methods.SlackMethod;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ByMethodRateLimiter implements SlackRateLimiter {

  private static final ConcurrentMap<SlackMethod, RateLimiter> RATE_LIMITERS =
    new ConcurrentHashMap<>();

  @Override
  public double acquire(String slackToken, SlackMethod slackMethod) {
    double permissibleQueriesPerSecond =
      slackMethod.getRateLimitingTier().getMinutelyAllowance() / 60.0;
    return RATE_LIMITERS
      .computeIfAbsent(
        slackMethod,
        ignored -> RateLimiter.create(permissibleQueriesPerSecond)
      )
      .acquire();
  }
}
