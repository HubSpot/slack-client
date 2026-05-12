package com.hubspot.slack.client.methods.params.stream.chunks;

public class UnknownStreamChunk implements StreamChunk {

  public static final String TYPE = "unknown";

  protected UnknownStreamChunk() {}

  @Override
  public String getType() {
    return TYPE;
  }
}
