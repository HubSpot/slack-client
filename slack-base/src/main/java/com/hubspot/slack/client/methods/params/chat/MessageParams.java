package com.hubspot.slack.client.methods.params.chat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.Attachment;
import com.hubspot.slack.client.models.blocks.Block;

public interface MessageParams extends HasChannel {
  Optional<String> getText();

  List<Attachment> getAttachments();

  @Value.Default
  default List<Block> getBlocks() {
    return Collections.emptyList();
  }
}
