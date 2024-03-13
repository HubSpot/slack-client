package com.hubspot.slack.client.models.blocks.elements;

public class UnknownBlockElement implements BlockElement {

  public static final String TYPE = "unknown";

  protected UnknownBlockElement() {}

  @Override
  public String getType() {
    return TYPE;
  }
}
