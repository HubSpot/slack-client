package com.hubspot.slack.client.enums;

public class UnmappedKeyException extends Exception {

  public UnmappedKeyException(Class clazz, Object key) {
    super(String.format("%s does not contain a value for %s", clazz, key));
  }
}
