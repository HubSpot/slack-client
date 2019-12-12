package com.hubspot.slack.client.models.blocks.elements;

public class UnknownBlockElement implements BlockElement {
  protected UnknownBlockElement() {
  }

  @Override
  public String getType() {
    return "";
  }
}
