package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public interface SlackTextFileCore extends SlackFile {
  Optional<String> getEditLink();
  Optional<String> getPreview();
  Optional<String> getPreviewHighlight();
  Optional<String> getLines();
  Optional<String> getLinesMore();

  @JsonProperty("preview_is_truncated")
  Optional<Boolean> isPreviewTruncated();
}
