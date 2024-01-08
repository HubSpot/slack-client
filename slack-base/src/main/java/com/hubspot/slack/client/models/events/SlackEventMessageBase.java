package com.hubspot.slack.client.models.events;

import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.Attachment;
import java.util.List;
import java.util.Optional;

public abstract class SlackEventMessageBase implements SlackEvent, HasChannel {

  public abstract Optional<SlackMessageSubtype> getSubtype();

  public abstract List<Attachment> getAttachments();
}
