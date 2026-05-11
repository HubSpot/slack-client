package com.hubspot.slack.client.methods.params.stream.chunks;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownStreamChunk.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = MarkdownTextChunk.class, name = MarkdownTextChunk.TYPE),
    @JsonSubTypes.Type(value = TaskUpdateChunk.class, name = TaskUpdateChunk.TYPE),
    @JsonSubTypes.Type(value = PlanUpdateChunk.class, name = PlanUpdateChunk.TYPE),
    @JsonSubTypes.Type(value = BlocksChunk.class, name = BlocksChunk.TYPE),
  }
)
public interface StreamChunk {
  String getType();
}
