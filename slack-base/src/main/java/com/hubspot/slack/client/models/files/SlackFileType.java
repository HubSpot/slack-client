package com.hubspot.slack.client.models.files;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Optional;
import java.util.stream.Stream;

public enum SlackFileType {
  TEXT("text", SlackTextFile.class),
  GIF("gif", SlackGifFile.class),
  CSV("csv", SlackCsvFile.class),
  JPG("jpg", SlackJpgFile.class),
  PNG("png", SlackPngFile.class),
  JAVASCRIPT("javascript", SlackJavaScriptFile.class),
  XLSX("xlsx", SlackXlsxFile.class),
  XLS("xls", SlackXlsFile.class),
  GDOC("gdoc", SlackGdocFile.class),
  UNKNOWN("unknown", SlackUnknownFiletype.class);

  final String type;
  private final Class<? extends SlackFile> clazz;

  SlackFileType(String type, Class<? extends SlackFile> clazz) {
    this.type = type;
    this.clazz = clazz;
  }

  @JsonValue
  public String getType() {
    return type;
  }

  @JsonCreator
  public static SlackFileType parse(String field) {
    return tryParse(field)
      .orElseThrow(() ->
        new IllegalArgumentException(field + " doesn't match any known slack file type")
      );
  }

  public static Optional<SlackFileType> tryParse(String field) {
    return Stream
      .of(values())
      .filter(val -> val.getType().equalsIgnoreCase(field))
      .findFirst();
  }

  public Class<? extends SlackFile> getFileTypeClass() {
    return clazz;
  }
}
