package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Optional;

public interface SlackImageFile extends SlackFile {
  @JsonProperty("thumb_64")
  Optional<String> getThumb64Url();

  @JsonProperty("thumb_80")
  Optional<String> getThumb80Url();

  @JsonProperty("thumb_160")
  Optional<String> getThumb160Url();

  @JsonProperty("thumb_360")
  Optional<String> getThumb360Url();

  @JsonProperty("thumb_360_w")
  Optional<Integer> getThumb360Width();

  @JsonProperty("thumb_360_h")
  Optional<Integer> getThumb360Height();

  @JsonProperty("thumb_480")
  Optional<String> getThumb480Url();

  @JsonProperty("thumb_480_w")
  Optional<Integer> getThumb480Width();

  @JsonProperty("thumb_480_h")
  Optional<Integer> getThumb480Height();

  Optional<Integer> getImageExifRotation();

  @JsonProperty("original_w")
  Optional<Integer> getOriginalWidth();

  @JsonProperty("original_h")
  Optional<Integer> getOriginalHeight();

  @JsonProperty("has_rich_preview")
  boolean hasRichPreview();
}
