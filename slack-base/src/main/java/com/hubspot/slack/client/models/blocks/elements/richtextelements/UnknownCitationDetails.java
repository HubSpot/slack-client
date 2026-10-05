package com.hubspot.slack.client.models.blocks.elements.richtextelements;

public class UnknownCitationDetails implements CitationDetails {

  public static final String CITATION_TYPE = "unknown";

  protected UnknownCitationDetails() {}

  @Override
  public String getCitationType() {
    return CITATION_TYPE;
  }
}
