package com.hubspot.slack.client.models.views;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.blocks.Block;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ModalViewPayload.class, name = ModalViewPayload.TYPE),
    @JsonSubTypes.Type(value = HomeTabViewPayload.class, name = HomeTabViewPayload.TYPE),
})
public interface ViewPayloadBase {
  String getType();

  List<Block> getBlocks();

  Optional<String> getPrivateMetadata();

  Optional<String> getCallbackId();

  Optional<String> getExternalId();
}
