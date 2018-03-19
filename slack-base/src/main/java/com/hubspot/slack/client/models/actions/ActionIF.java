package com.hubspot.slack.client.models.actions;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonInclude(Include.NON_EMPTY)
@JsonNaming(SnakeCaseStrategy.class)
public interface ActionIF {
  Optional<String> getName();

  ActionType getType();
  Optional<String> getText();
  Optional<String> getValue();
  Optional<Confirm> getConfirm();
  Optional<String> getUrl();

  @JsonProperty("style")
  Optional<String> getRawStyle();

  @Derived
  default Optional<ButtonStyle> getButtonStyle() {
    return getRawStyle().flatMap(ButtonStyle::find);
  }

  List<Option> getOptions();
  List<Option> getSelectedOptions();
  Optional<SlackDataSource> getDataSource();
  Optional<Integer> getMinQueryLength();
}
