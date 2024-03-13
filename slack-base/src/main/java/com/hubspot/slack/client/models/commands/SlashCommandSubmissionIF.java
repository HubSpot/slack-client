package com.hubspot.slack.client.models.commands;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SlashCommandSubmissionIF {
  String getToken();
  String getTeamId();
  String getTeamDomain();
  Optional<String> getEnterpriseId();
  Optional<String> getEnterpriseName();
  String getChannelId();
  String getChannelName();
  String getUserId();
  String getCommand();
  String getText();
  String getResponseUrl();
  String getTriggerId();

  /**
   * @deprecated
   * The user_name field is being phased out
   * https://api.slack.com/changelog/2017-09-the-one-about-usernames
   */
  @Deprecated
  String getUserName();
}
