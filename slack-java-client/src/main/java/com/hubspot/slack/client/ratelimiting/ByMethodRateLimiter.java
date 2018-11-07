package com.hubspot.slack.client.ratelimiting;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hubspot.slack.client.methods.SlackMethod;

@Singleton
public class ByMethodRateLimiter implements SlackRateLimiter {
  private static final ConcurrentMap<SlackMethod, RateLimiter> RATE_LIMITERS = new ConcurrentHashMap<>();

  @Inject
  public ByMethodRateLimiter() {}

  @Override
  public double acquire(String slackToken, SlackMethod slackMethod) {
    double permissibleQueriesPerSecond = slackMethod.getRateLimitingTier().getMinutelyAllowance() / 60.0;
    return RATE_LIMITERS.computeIfAbsent(
        slackMethod,
        ignored -> RateLimiter.create(permissibleQueriesPerSecond)
    ).acquire();
  }
}
