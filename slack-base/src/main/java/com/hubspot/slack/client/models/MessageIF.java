package com.hubspot.slack.client.models;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
public interface MessageIF {
  String getType();
  String getTeam();
  SlackChannel getChannel();

  Optional<String> getUser();
  String getUsername();

  @JsonProperty("ts")
  String getTimestamp();

  String getText();
  Optional<String> getPermalink();
  List<Attachment> getAttachments();


  @JsonProperty("previous_2")
  Optional<LiteMessage> getPreviousPrevious();
  @JsonProperty("previous")
  Optional<LiteMessage> getPrevious();
  @JsonProperty("next")
  Optional<LiteMessage> getNext();
  @JsonProperty("next_2")
  Optional<LiteMessage> getNextNext();

  @Derived
  default String getSlackLink() {
    return "slack://channel?team=" + getTeam()
        + "&id=" + getChannel().getId()
        + "&message=" + getTimestamp();
  }
}
