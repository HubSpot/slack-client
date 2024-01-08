package com.hubspot.slack.client.models.actions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;

public enum SlackDataSource {
  /**
   * Provided in the body
   */
  STATIC,

  /**
   * Select from slack users in team
   */
  USERS,

  /**
   * Select from channels in team
   */
  CHANNELS,

  /**
   * Select from direct messages in team
   */
  CONVERSATIONS,

  /**
   * Hit interactive endpoint to populate
   */
  EXTERNAL;

  private static final EnumIndex<String, SlackDataSource> INDEX = new EnumIndex<>(
    SlackDataSource.class,
    SlackDataSource::toString
  );

  @JsonCreator
  public static SlackDataSource get(String key) throws UnmappedKeyException {
    return INDEX.get(key);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }
}
