package com.hubspot.slack.client.models.blocks.elements.richtextelements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Polymorphic {@code details} object of a {@link RichTextCitationElement}. The concrete type is
 * discriminated by {@code citation_type}: {@code file}, {@code external}, {@code web},
 * {@code message}, or {@code memory}.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/citation-element">Citation element</a>
 */
@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  property = "citation_type",
  defaultImpl = UnknownCitationDetails.class
)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(
      value = FileCitationDetails.class,
      name = FileCitationDetails.CITATION_TYPE
    ),
    @JsonSubTypes.Type(
      value = ExternalCitationDetails.class,
      name = ExternalCitationDetails.CITATION_TYPE
    ),
    @JsonSubTypes.Type(
      value = WebCitationDetails.class,
      name = WebCitationDetails.CITATION_TYPE
    ),
    @JsonSubTypes.Type(
      value = MessageCitationDetails.class,
      name = MessageCitationDetails.CITATION_TYPE
    ),
    @JsonSubTypes.Type(
      value = MemoryCitationDetails.class,
      name = MemoryCitationDetails.CITATION_TYPE
    ),
  }
)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface CitationDetails {
  String getCitationType();
}
