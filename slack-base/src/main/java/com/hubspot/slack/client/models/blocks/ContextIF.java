package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.json.ImageBlockOrTextDeserializer;
import com.hubspot.slack.client.models.blocks.objects.ImageBlockOrText;
import java.util.List;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

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
  @Value.Parameter
  List<ImageBlockOrText> getElements();
}
