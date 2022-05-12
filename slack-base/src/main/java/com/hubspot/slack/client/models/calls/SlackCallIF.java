package com.hubspot.slack.client.models.calls;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.json.SlackOrExternalUserDeserializer;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface SlackCallIF {
  String getExternalUniqueId();

  String getId();

  @JsonProperty("start_date")
  Optional<Integer> getStartDateEpochSeconds();

  @JsonIgnore
  @Value.Derived
  default Optional<Instant> getStartDate() {
    return getStartDateEpochSeconds().map(Instant::ofEpochSecond);
  }

  Optional<String> getDesktopAppJoinUrl();

  Optional<String> getExternalDisplayId();

  Optional<String> getTitle();

  @JsonDeserialize(contentUsing = SlackOrExternalUserDeserializer.class)
  List<SlackInternalOrExternalUser> getUsers();
}
