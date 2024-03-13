package com.hubspot.slack.client.models.events;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hubspot.slack.client.enums.EnumIndex;

/**
 * See https://api.slack.com/events/message for more info. Note that there are differences between the web api and RTM.
 */
public enum SlackMessageSubtype {
  /**
   * Human joins a channel
   */
  CHANNEL_JOIN,

  /**
   * A message was posted by an integration
   */
  BOT_MESSAGE(SlackEventBotMessage.class),

  /**
   * Someone uploaded a file
   */
  FILE_SHARE,

  /**
   * A /me message was sent
   */
  ME_MESSAGE,

  /**
   * A message was changed
   */
  MESSAGE_CHANGED(SlackEventMessageChanged.class),

  /**
   * A message was deleted
   */
  MESSAGE_DELETED(SlackEventMessageDeleted.class),

  /**
   * A message thread received a reply
   */
  MESSAGE_REPLIED(SlackEventMessageReplied.class),

  /**
   * A message thread's reply was broadcast to a channel
   */
  THREAD_BROADCAST,

  /**
   * A message thread's reply was broadcast to a channel (documented, but slack seems to use ^^^ instead)
   */
  REPLY_BROADCAST,

  /**
   * An unknown event type
   */
  UNKNOWN(SlackEventMessage.class);

  private final Class<? extends SlackEventMessageBase> messageClass;

  SlackMessageSubtype(Class<? extends SlackEventMessageBase> messageClass) {
    this.messageClass = messageClass;
  }

  SlackMessageSubtype() {
    this(SlackEventMessage.class);
  }

  private static final EnumIndex<String, SlackMessageSubtype> INDEX = new EnumIndex<>(
    SlackMessageSubtype.class,
    SlackMessageSubtype::toString
  );

  @JsonCreator
  public static SlackMessageSubtype get(String key) {
    return INDEX.find(key).orElse(UNKNOWN);
  }

  @Override
  @JsonValue
  public String toString() {
    return name().toLowerCase();
  }

  public Class<? extends SlackEventMessageBase> getMessageClass() {
    return messageClass;
  }
}
