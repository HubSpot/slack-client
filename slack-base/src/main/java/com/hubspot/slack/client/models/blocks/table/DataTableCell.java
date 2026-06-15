package com.hubspot.slack.client.models.blocks.table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "type",
  defaultImpl = UnknownTableCell.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = RawTextTableCell.class, name = RawTextTableCell.TYPE),
    @JsonSubTypes.Type(value = RawNumberTableCell.class, name = RawNumberTableCell.TYPE),
    @JsonSubTypes.Type(value = RichTextTableCell.class, name = RichTextTableCell.TYPE),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface DataTableCell extends TableCell {}
