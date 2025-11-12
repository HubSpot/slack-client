package com.hubspot.slack.client.methods.params.chat.workobject.entity.fullsizepreview;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.FullSizePreviewErrorCodeDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.FullSizePreviewErrorCodeSerializer;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface FullSizePreviewErrorIF {
  @JsonSerialize(using = FullSizePreviewErrorCodeSerializer.class)
  @JsonDeserialize(using = FullSizePreviewErrorCodeDeserializer.class)
  FullSizePreviewErrorCode getCode();

  String getMessage();
}
