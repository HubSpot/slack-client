package com.hubspot.slack.client.methods.params.chat.workobject.entity.fullsizepreview;

public enum FullSizePreviewErrorCode {
  FILE_NOT_SUPPORTED("file_not_supported"),
  FILE_SIZE_EXCEEDED("file_size_exceeded"),
  CUSTOM("custom");

  private final String value;

  FullSizePreviewErrorCode(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
