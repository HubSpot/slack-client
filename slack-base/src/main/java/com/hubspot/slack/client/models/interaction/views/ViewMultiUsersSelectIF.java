package com.hubspot.slack.client.models.interaction.views;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ViewMultiUsersSelectIF extends ViewInput {
  List<String> getSelectedUsers();

  @JsonIgnore
  default Optional<String> getStringValue() {
    List<String> users = getSelectedUsers();
    return users.isEmpty() ? Optional.empty() : Optional.of(String.join(",", users));
  }
}
