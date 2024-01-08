package com.hubspot.slack.client.models.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface SlackTeamIF {
  String getId();
  String getDomain();
  Optional<String> getName();
  Optional<String> getEnterpriseId();
  Optional<String> getEnterpriseName();
  Optional<SlackTeamIcon> getIcon();
}
