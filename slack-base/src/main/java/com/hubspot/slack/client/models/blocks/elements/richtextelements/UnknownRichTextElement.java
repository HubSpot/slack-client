package com.hubspot.slack.client.models.blocks.elements.richtextelements;

public class UnknownRichTextElement implements RichTextElement {

  public static final String TYPE = "unknown";

  protected UnknownRichTextElement() {}

  @Override
  public String getType() {
    return TYPE;
  }
}
