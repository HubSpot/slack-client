package com.hubspot.slack.client.models.events;

import java.util.Optional;

import com.hubspot.slack.client.methods.interceptor.HasChannel;

public abstract class SlackEventMessageBase implements SlackEvent, HasChannel {
  public abstract Optional<SlackMessageSubtype> getSubtype();
}
