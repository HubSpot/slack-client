package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BlockOrAttachment;
import com.hubspot.slack.client.models.ChatUnfurlBlocksOrAttachment;
import com.hubspot.slack.client.models.json.BlockOrAttachmentDeserializer;
import java.util.List;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface ChatUnfurlBlocksIF extends ChatUnfurlBlocksOrAttachment {
  @Value.Parameter(order = 1)
  @JsonDeserialize(contentUsing = BlockOrAttachmentDeserializer.class)
  List<? extends BlockOrAttachment> getBlocks();
}
