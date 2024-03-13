package com.hubspot.slack.client.methods;

import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
public interface SpecialTierIF extends RateLimitingTier {
  @Override
  @Parameter
  int getMinutelyAllowance();
}
