package com.hubspot.slack.client.methods.params.chat.workobject.entity;

public enum FieldDataType {
  STRING("string"),
  INTEGER("integer"),
  USER_ID("slack#/types/user_id"),
  CHANNEL_ID("slack#/types/channel_id"),
  TIMESTAMP("slack#/types/timestamp"),
  //A date in the format YYYY-MM-DD
  DATE("slack#/types/date"),
  //An image; provide either the URL of a publicly hosted image (image_url) or a slack_file with a preview image
  IMAGE("slack#/types/image");

  private final String value;

  FieldDataType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
