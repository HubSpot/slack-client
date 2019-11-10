package com.hubspot.slack.client.models.blocks;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Actions.class, name = Actions.TYPE),
    @JsonSubTypes.Type(value = Context.class, name = Context.TYPE),
    @JsonSubTypes.Type(value = Divider.class, name = Divider.TYPE),
    @JsonSubTypes.Type(value = File.class, name = File.TYPE),
    @JsonSubTypes.Type(value = Image.class, name = Image.TYPE),
    @JsonSubTypes.Type(value = Section.class, name = Section.TYPE),
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Block {
  String getType();

  Optional<String> getBlockId();
}
