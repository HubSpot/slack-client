package com.hubspot.slack.client.models.blocks.elements.richtextelements;

public class UnknownRichTextObject implements RichTextObject {

  public static final String TYPE = "unknown";

  protected UnknownRichTextObject() {}

  @Override
  public String getType() {
    return TYPE;
  }
}
