package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.BlockOrAttachment;
import java.util.Optional;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownBlock.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = Actions.class, name = Actions.TYPE),
    @JsonSubTypes.Type(value = Call.class, name = Call.TYPE),
    @JsonSubTypes.Type(value = Context.class, name = Context.TYPE),
    @JsonSubTypes.Type(value = Divider.class, name = Divider.TYPE),
    @JsonSubTypes.Type(value = File.class, name = File.TYPE),
    @JsonSubTypes.Type(value = Header.class, name = Header.TYPE),
    @JsonSubTypes.Type(value = Image.class, name = Image.TYPE),
    @JsonSubTypes.Type(value = Input.class, name = Input.TYPE),
    @JsonSubTypes.Type(value = Section.class, name = Section.TYPE),
    @JsonSubTypes.Type(value = Markdown.class, name = Markdown.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface Block extends BlockOrAttachment {
  String getType();

  Optional<String> getBlockId();
}
