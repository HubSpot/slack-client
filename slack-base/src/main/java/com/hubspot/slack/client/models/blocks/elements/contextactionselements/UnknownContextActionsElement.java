package com.hubspot.slack.client.models.blocks.elements.contextactionselements;

public class UnknownContextActionsElement implements ContextActionsElement {

  public static final String TYPE = "unknown";

  protected UnknownContextActionsElement() {}

  @Override
  public String getType() {
    return TYPE;
  }
}
