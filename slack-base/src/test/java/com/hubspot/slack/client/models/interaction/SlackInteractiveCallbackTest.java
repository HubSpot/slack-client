package com.hubspot.slack.client.models.interaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class SlackInteractiveCallbackTest {

  @Test
  public void checkAllInteractiveCallBackTypesAreMapped() {
    JsonSubTypes jsonSubTypes =
      SlackInteractiveCallback.class.getAnnotation(JsonSubTypes.class);

    Set<String> types = Arrays
      .stream(jsonSubTypes.value())
      .map(JsonSubTypes.Type::name)
      .collect(Collectors.toSet());

    for (String type : types) {
      assertThat(InteractiveCallbackType.get(type))
        .describedAs("Could not find enum mapping for %s", type)
        .isNotEqualTo(InteractiveCallbackType.UNKNOWN);
    }
  }
}
