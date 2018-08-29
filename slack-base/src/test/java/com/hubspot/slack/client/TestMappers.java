package com.hubspot.slack.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public final class TestMappers {
  private TestMappers() {}
  public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
}
