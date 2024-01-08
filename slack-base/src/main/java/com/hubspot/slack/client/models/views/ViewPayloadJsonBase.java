package com.hubspot.slack.client.models.views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = ModalViewPayload.class, name = ModalViewPayload.TYPE),
    @JsonSubTypes.Type(value = HomeTabViewPayload.class, name = HomeTabViewPayload.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface ViewPayloadJsonBase {}
