package com.hubspot.slack.client.methods.interceptor;

import com.hubspot.slack.client.models.events.MessageEdited;
import java.util.Optional;

public interface HasEdited {
  Optional<MessageEdited> getEdited();
}
