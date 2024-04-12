package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.models.ChatUnfurlBlocksOrAttachment;
import com.hubspot.slack.client.models.json.BlockOrAttachmentDeserializer;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ChatUnfurlParamsIF extends HasChannel {
  @Override
  @JsonProperty("channel")
  String getChannelId();

  String getTs();

  @JsonDeserialize(contentUsing = BlockOrAttachmentDeserializer.class)
  Map<String, ChatUnfurlBlocksOrAttachment> getUnfurls();

  Optional<Boolean> isUserAuthRequired();

  Optional<String> getUserAuthMessage();

  Optional<URI> getUserAuthUrl();
}
