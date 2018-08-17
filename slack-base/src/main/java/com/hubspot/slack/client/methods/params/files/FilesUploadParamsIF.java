package com.hubspot.slack.client.methods.params.files;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface FilesUploadParamsIF {
  List<String> getChannels();
  Optional<String> getContent();
  Optional<String> getFilename();
  Optional<String> getInitialComment();
  Optional<String> getThreadTs();
  Optional<String> getTitle();

  @Check
  @JsonIgnore
  default void hasContent() {
    if (!getContent().isPresent()) {
      // We don't support multipart/form-data yet, so content has to be present.
      // But we will eventually, so the signature should have content as Optional
      throw new IllegalStateException("Must provide some content to upload a file");
    }
  }
}
