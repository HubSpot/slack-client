package com.hubspot.slack.client.methods.params.chat;

import java.util.List;
import java.util.Optional;

import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Block;

public interface MessageParams extends HasChannel {
  Optional<String> getText();

  List<Attachment> getAttachments();

  List<Block> getBlocks();
}
