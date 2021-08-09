package com.hubspot.slack.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;

class ReflectionBasedFieldPresenceTest {
  private static final Logger LOG = LoggerFactory.getLogger(ReflectionBasedFieldPresenceTest.class);

  private static final Number DEFAULT_NUMBER = 5;
  private static final Boolean DEFAULT_BOOLEAN = false;
  private static final String DEFAULT_STRING = "default";
  private static final Optional<String> DEFAULT_OPTIONAL_STRING = Optional.of("defaultOptional");
  private static final Set<String> OPTIONAL_STRING_METHODS_TO_BUILD = Collections.singleton("setText");
  private static final Map DEFAULT_MAP = Collections.emptyMap();

  private static final Reflections SLACK_CLIENT_REFLECTOR = new Reflections("com.hubspot.slack.client");

  static <T> Set<Class<? extends T>> getConcreteFinalSubclasses(Class<T> baseClass) {
    return SLACK_CLIENT_REFLECTOR.getSubTypesOf(baseClass).stream()
        .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
        .filter(clazz -> Modifier.isFinal(clazz.getModifiers()))
        .filter(clazz -> !clazz.getName().contains("$"))
        .collect(Collectors.toSet());
  }

  static <T> Object buildTestInstance(Class<? extends T> base) throws Exception {
    Optional<Method> builderFactoryMethodMaybe = ReflectionUtils.getMethods(base, ReflectionUtils.withName("builder")).stream()
        .filter(method -> Modifier.isStatic(method.getModifiers()))
        .findFirst();
    if (!builderFactoryMethodMaybe.isPresent()) {
      throw new IllegalStateException(
          String.format(
              "Couldn't find a method with name 'builder' on class %s",
              base.getClass().getName()
          )
      );
    }

    Object builder = builderFactoryMethodMaybe.get().invoke(null); // static methods ignore object params here
    Set<Method> builderMethods = ReflectionUtils.getMethods(
        builder.getClass(),
        ReflectionUtils.withPrefix("set"),
        ReflectionUtils.withParametersCount(1),
        method -> !Iterable.class.isAssignableFrom(method.getParameterTypes()[0]),
        method -> !(Optional.class.isAssignableFrom(method.getParameterTypes()[0]) &&
            !OPTIONAL_STRING_METHODS_TO_BUILD.contains(method.getName())),
        method -> !ReflectionUtils.withAnyParameterAnnotation(Nullable.class).apply(method)
    );
    for (Method builderMethod : builderMethods) {
      if (builderMethod.getParameterCount() != 1) {
        LOG.debug("Skipping non-monadic {}", builderMethod.getName());
        continue;
      }

      Class<?> singletonParamType = builderMethod.getParameterTypes()[0];
      if (isNumeric(singletonParamType)) {
        LOG.debug("Calling {} with default of {}", builderMethod.getName(), DEFAULT_NUMBER);
        builderMethod.invoke(builder, DEFAULT_NUMBER);
      } else if (String.class.isAssignableFrom(singletonParamType)) {
        LOG.debug("Calling {} with default of {}", builderMethod.getName(), DEFAULT_STRING);
        builderMethod.invoke(builder, DEFAULT_STRING);
      } else if (OPTIONAL_STRING_METHODS_TO_BUILD.contains(builderMethod.getName())) {
        LOG.debug("Calling {} with default optional of {}", builderMethod.getName(), DEFAULT_OPTIONAL_STRING);
        builderMethod.invoke(builder, DEFAULT_OPTIONAL_STRING);
      } else if (Boolean.class.isAssignableFrom(singletonParamType) || boolean.class.isAssignableFrom(singletonParamType)) {
        LOG.debug("Calling {} with default of {}", builderMethod.getName(), DEFAULT_BOOLEAN);
        builderMethod.invoke(builder, DEFAULT_BOOLEAN);
      } else if (Enum.class.isAssignableFrom(singletonParamType)) {
        Object firstEnumConstant = singletonParamType.getEnumConstants()[0];
        LOG.debug("Calling {} with default of {}", builderMethod.getName(), firstEnumConstant);
        builderMethod.invoke(builder, firstEnumConstant);
      } else if (Map.class.isAssignableFrom(singletonParamType)) {
        LOG.debug("Calling {} with default of {}", builderMethod.getName(), DEFAULT_MAP);
        builderMethod.invoke(builder, DEFAULT_MAP);
      } else {
        LOG.debug("Recursively constructing test instance for {}", builderMethod.getName());
        builderMethod.invoke(builder, buildTestInstance(singletonParamType));
      }
    }

    Optional<Method> buildMethodMaybe = ReflectionUtils.getMethods(builder.getClass(), ReflectionUtils.withName("build"))
        .stream()
        .findFirst();
    if (!buildMethodMaybe.isPresent()) {
      throw new IllegalStateException(
          String.format(
              "Couldn't find builder 'build' method on %s",
              builder.getClass()
          )
      );
    }

    Method buildMethod = buildMethodMaybe.get();
    return buildMethod.invoke(builder);
  }

  private static boolean isNumeric(Class<?> clazz) {
    return Number.class.isAssignableFrom(clazz)
        || int.class.isAssignableFrom(clazz)
        || long.class.isAssignableFrom(clazz)
        || double.class.isAssignableFrom(clazz)
        || float.class.isAssignableFrom(clazz);
  }

  static void verifyHasField(
      Class<?> base,
      Object object,
      String field
  ) throws Exception {
    String rawResult = ObjectMapperUtils.mapper().writeValueAsString(object);
    JsonNode parsedSerializedVersion = ObjectMapperUtils.mapper().readTree(rawResult);
    assertThat(parsedSerializedVersion.has(field))
        .describedAs(
            "Serialized version of %s (%s) must have a field exactly matching '%s'",
            base.getClass().getSimpleName(),
            object.getClass(),
            field
        )
        .isTrue();
  }
}
