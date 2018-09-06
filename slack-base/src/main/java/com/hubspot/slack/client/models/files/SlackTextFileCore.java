package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface SlackTextFileCore extends SlackFile {
  String getEditLink();
  String getPreview();
  String getPreviewHighlight();
  String getLines();
  String getLinesMore();

  @JsonProperty("preview_is_truncated")
  boolean isPreviewTruncated();
}
