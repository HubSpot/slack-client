package com.hubspot.slack.client.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public final class ObjectMapperUtils {

  private static final AtomicReference<Optional<ObjectMapper>> MAPPER_REF = new AtomicReference<>(
    Optional.<ObjectMapper>empty()
  );

  private ObjectMapperUtils() {}

  private static ObjectMapper create() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new GuavaModule());
    mapper.registerModule(new JodaModule());
    mapper.registerModule(new Jdk8Module());
    mapper.registerModule(new JSR310Module());

    mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, false);
    mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, true);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return mapper;
  }

  public static ObjectMapper mapper() {
    // check and check and set since object mappers are pretty expensive to construct
    if (!MAPPER_REF.get().isPresent()) {
      synchronized (ObjectMapperUtils.class) {
        if (!MAPPER_REF.get().isPresent()) {
          MAPPER_REF.set(Optional.of(create()));
        }
      }
    }

    return MAPPER_REF.get().get();
  }
}
