package com.hubspot.slack.client.methods.params.chat;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.BlockOrAttachment;
import com.hubspot.slack.client.models.json.BlockOrAttachmentDeserializer;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatUnfurlParamsIF extends HasChannel {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  String getTs();

  @JsonDeserialize(contentUsing = BlockOrAttachmentDeserializer.class)
  Map<String, BlockOrAttachment> getUnfurls();

  Optional<Boolean> isUserAuthRequired();

  Optional<String> getUserAuthMessage();

  Optional<URI> getUserAuthUrl();
}
