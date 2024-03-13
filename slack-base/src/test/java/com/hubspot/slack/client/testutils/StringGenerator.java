package com.hubspot.slack.client.testutils;

import java.util.Collections;

public class StringGenerator {

  public static String generateStringWithLengthAndEllipsis(int length) {
    return generateStringWithLength(length) + "...";
  }

  public static String generateStringWithLength(int length) {
    return String.join("", Collections.nCopies(length, "a"));
  }
}
