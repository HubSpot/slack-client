package com.hubspot.slack.client.methods.params.chat.workobject.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fullsizepreview.FullSizePreviewErrorCode;
import java.io.IOException;
import java.util.Optional;

public class FullSizePreviewErrorCodeSerializer
  extends JsonSerializer<Optional<FullSizePreviewErrorCode>> {

  @Override
  public void serialize(
    Optional<FullSizePreviewErrorCode> errorCodeMaybe,
    JsonGenerator gen,
    SerializerProvider serializers
  ) throws IOException {
    if (errorCodeMaybe.isPresent()) {
      gen.writeString(errorCodeMaybe.get().getValue());
    } else {
      gen.writeNull();
    }
  }

  @Override
  public boolean isEmpty(
    SerializerProvider provider,
    Optional<FullSizePreviewErrorCode> value
  ) {
    return value.isEmpty();
  }
}
