package com.hubspot.slack.client.ratelimiting;

import com.hubspot.slack.client.methods.SlackMethod;

public interface SlackRateLimiter {
  /**
   * Acquires a permit, blocking if one cannot be acquired immediately
   * @param slackToken
   * @param slackMethod
   * @return The amount of time spent waiting in seconds
   */
  double acquire(String slackToken, SlackMethod slackMethod);
}
