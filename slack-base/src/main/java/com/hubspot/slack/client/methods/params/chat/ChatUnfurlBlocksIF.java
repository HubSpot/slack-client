package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BlockOrAttachment;
import com.hubspot.slack.client.models.json.BlockOrAttachmentDeserializer;
import java.util.List;
import org.immutables.value.Value;

@HubSpotStyle
@Value.Immutable
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface ChatUnfurlBlocksIF {
  @Value.Parameter(order = 1)
  @JsonDeserialize(contentUsing = BlockOrAttachmentDeserializer.class)
  List<? extends BlockOrAttachment> getBlocks();
}
