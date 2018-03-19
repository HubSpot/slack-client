package com.hubspot.slack.client.methods;

import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SpecialTierIF extends RateLimitingTier {
  @Override
  @Parameter
  int getMinutelyAllowance();
}
