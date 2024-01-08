package com.hubspot.slack.client.methods;

/**
 * https://api.slack.com/docs/rate-limits#tiers
 */
public enum RateLimitingTiers implements RateLimitingTier {
  /**
   * Access tier 1 methods infrequently. A small amount of burst behavior is tolerated.
   */
  TIER_1(1),

  /**
   * Most methods allow at least 20 requests per minute, while allowing for occasional bursts of more requests.
   */
  TIER_2(20),

  /**
   * Tier 3 methods allow a larger number of requests and are typically attached to methods with paginating collections of conversations or users. Sporadic bursts are welcome.
   */
  TIER_3(50),

  /**
   * Enjoy a large request quota for Tier 4 methods, including generous burst behavior.
   */
  TIER_4(100);

  private final int allowancePerMinute;

  RateLimitingTiers(int allowancePerMinute) {
    this.allowancePerMinute = allowancePerMinute;
  }

  @Override
  public int getMinutelyAllowance() {
    return allowancePerMinute;
  }
}
