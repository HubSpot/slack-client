package com.hubspot.slack.client.methods.params.chat;

import java.util.List;

import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.Attachment;

public interface MessageParams extends HasChannel {
  String getText();

  List<Attachment> getAttachments();
}
