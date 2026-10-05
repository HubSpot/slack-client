package com.hubspot.slack.client.models.blocks.elements.richtextelements;

/**
 * Read-only deserialization fallback for a {@link CitationDetails} whose {@code citation_type} we
 * don't model (mirrors {@code UnknownRichTextElement} / {@code UnknownBlock}). It drops the original
 * payload, so it must NOT be re-serialized and sent back to Slack — re-serializing emits
 * {@code citation_type: "unknown"}, which Slack rejects. Use it only to tolerate unknown inbound
 * citation types without failing deserialization.
 */
public class UnknownCitationDetails implements CitationDetails {

  public static final String CITATION_TYPE = "unknown";

  protected UnknownCitationDetails() {}

  @Override
  public String getCitationType() {
    return CITATION_TYPE;
  }
}
