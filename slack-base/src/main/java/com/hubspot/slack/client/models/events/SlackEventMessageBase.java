package com.hubspot.slack.client.models.events;

import java.util.List;
import java.util.Optional;

import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.Attachment;

public abstract class SlackEventMessageBase implements SlackEvent, HasChannel {
  public abstract Optional<SlackMessageSubtype> getSubtype();

  public abstract List<Attachment> getAttachments();
}
