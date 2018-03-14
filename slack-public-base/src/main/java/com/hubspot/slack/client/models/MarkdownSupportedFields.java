package com.hubspot.slack.client.models;

public enum MarkdownSupportedFields {
  /** Renders markdown in the pretext of an attachment */
  PRETEXT,
  /** Renders markdown in the text field of an attachment */
  TEXT,
  /** Renders markdown in each field on an attachment */
  FIELDS,
  ;
  public String getSlackVersion() {
    return name().toLowerCase();
  }
}
