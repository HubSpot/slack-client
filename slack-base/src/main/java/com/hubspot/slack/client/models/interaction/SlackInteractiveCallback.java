package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.teams.SlackTeam;
import com.hubspot.slack.client.models.users.SlackUserLite;

@JsonTypeInfo(
  use = Id.NAME,
  include = As.EXISTING_PROPERTY,
  property = "type",
  visible = true
)
@JsonSubTypes(
  {
    @Type(value = BlockActions.class, name = "block_actions"),
    @Type(value = InteractiveAction.class, name = "interactive_message"),
    @Type(value = DialogSubmission.class, name = "dialog_submission"),
    @Type(value = ViewSubmission.class, name = "view_submission"),
    @Type(value = MessageAction.class, name = "message_action"),
    @Type(value = Shortcut.class, name = "shortcut"),
  }
)
public interface SlackInteractiveCallback {
  InteractiveCallbackType getType();
  String getCallbackId();
  String getActionTs();
  String getToken();
  SlackTeam getTeam();
  SlackUserLite getUser();
  SlackChannel getChannel();
}
