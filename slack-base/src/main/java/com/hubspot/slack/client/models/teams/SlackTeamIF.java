package com.hubspot.slack.client.models.teams;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface SlackTeamIF {
  String getId();
  String getDomain();
  Optional<String>  getEnterpriseId();
  Optional<String>  getEnterpriseName();
}
