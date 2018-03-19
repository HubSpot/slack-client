package com.hubspot.slack.client.models;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Resources;

public class JsonLoader {
  private static final Joiner NEWLINE_JOINER = Joiner.on('\n');

  public static String loadJsonFromFile(String fileName) {
    try {
      return NEWLINE_JOINER.join(Resources.readLines(Resources.getResource(fileName), Charsets.UTF_8));
    } catch (Exception ex) {
      throw new RuntimeException("Can't load " + fileName, ex);
    }
  }
}
