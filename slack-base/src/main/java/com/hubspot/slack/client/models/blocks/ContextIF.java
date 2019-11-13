package com.hubspot.slack.client.models.blocks;

import java.util.List;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.json.ImageBlockOrTextDeserializer;
import com.hubspot.slack.client.models.blocks.objects.ImageBlockOrText;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ContextIF extends Block {
  String TYPE = "context";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @JsonDeserialize(contentUsing = ImageBlockOrTextDeserializer.class)
  List<ImageBlockOrText> getElements();
}
