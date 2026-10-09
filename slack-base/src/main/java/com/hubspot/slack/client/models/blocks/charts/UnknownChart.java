package com.hubspot.slack.client.models.blocks.charts;

/**
 * Read-only deserialization fallback for a {@link Chart} whose {@code type} we don't model
 * (mirrors the other {@code Unknown*} fallbacks). Must not be re-serialized and sent to Slack.
 */
public class UnknownChart implements Chart {

  public static final String TYPE = "unknown";

  protected UnknownChart() {}

  @Override
  public String getType() {
    return TYPE;
  }
}
