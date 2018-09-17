package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.models.events.channel.SlackChannelArchiveEvent;
import com.hubspot.slack.client.models.events.channel.SlackChannelCreatedEvent;
import com.hubspot.slack.client.models.events.channel.SlackChannelDeletedEvent;
import com.hubspot.slack.client.models.events.channel.SlackChannelRenameEvent;
import com.hubspot.slack.client.models.events.channel.SlackChannelUnarchiveEvent;

public enum SlackEventType {

  APP_UNINSTALLED,
  CHANNEL_ARCHIVE(SlackChannelArchiveEvent.class),
  CHANNEL_CREATED(SlackChannelCreatedEvent.class),
  CHANNEL_DELETED(SlackChannelDeletedEvent.class),
  CHANNEL_HISTORY_CHANGED,
  CHANNEL_RENAME(SlackChannelRenameEvent.class),
  CHANNEL_UNARCHIVE(SlackChannelUnarchiveEvent.class),
  DND_UPDATED,
  DND_UPDATED_USER,
  EMAIL_DOMAIN_CHANGED,
  EMOJI_CHANGED,
  FILE_CHANGE,
  FILE_COMMENT_ADDED,
  FILE_COMMENT_DELETED,
  FILE_COMMENT_EDITED,
  FILE_CREATED,
  FILE_DELETED,
  FILE_PUBLIC,
  FILE_SHARED,
  FILE_UNSHARED,
  GRID_MIGRATION_FINISHED,
  GRID_MIGRATION_STARTED,
  GROUP_ARCHIVE,
  GROUP_CLOSE,
  GROUP_HISTORY_CHANGED,
  GROUP_OPEN,
  GROUP_RENAME,
  GROUP_UNARCHIVE,
  IM_CLOSE,
  IM_CREATED,
  IM_HISTORY_CHANGED,
  IM_OPEN,
  LINK_SHARED,
  MEMBER_JOINED_CHANNEL,
  MEMBER_LEFT_CHANNEL,
  MESSAGE(SlackEventMessage.class),
  PIN_ADDED,
  PIN_REMOVED,
  REACTION_ADDED,
  REACTION_REMOVED,
  RESOURCES_ADDED,
  RESOURCES_REMOVED,
  SCOPE_DENIED,
  SCOPE_GRANTED,
  STAR_ADDED,
  STAR_REMOVED,
  SUBTEAM_CREATED,
  SUBTEAM_MEMBERS_CHANGED,
  SUBTEAM_SELF_ADDED,
  SUBTEAM_SELF_REMOVED,
  SUBTEAM_UPDATED,
  TEAM_DOMAIN_CHANGE,
  TEAM_JOIN,
  TEAM_RENAME,
  TOKENS_REVOKED,
  URL_VERIFICATION,
  USER_CHANGE,
  UNKNOWN
  ;

  private final Class<? extends SlackEvent> eventClass;

  private static final EnumIndex<String, SlackEventType> INDEX = new EnumIndex<>(SlackEventType.class, SlackEventType::toString);

  SlackEventType(Class<? extends SlackEvent> eventClass) {
    this.eventClass = eventClass;
  }

  SlackEventType() {
    this(SlackEventSkeleton.class);
  }

  @JsonCreator
  public static SlackEventType get(String key) {
    return INDEX.find(key).orElse(SlackEventType.UNKNOWN);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }

  public Class<? extends SlackEvent> getEventClass() {
    return eventClass;
  }
}
