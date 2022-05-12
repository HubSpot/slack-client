package com.hubspot.slack.client.methods.params.calls;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.calls.SlackInternalOrExternalUser;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface CallsAddParamsIF {
  String getExternalUniqueId();

  String getJoinUrl();

  Optional<String> getCreatedBy();

  Optional<Integer> getDateStart();

  Optional<String> getDesktopAppJoinUrl();

  Optional<String> getExternalDisplayId();

  Optional<String> getTitle();

  List<? extends SlackInternalOrExternalUser> getUsers();
}
