package com.hubspot.slack.client.interceptors.http;


import java.util.Iterator;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;

public final class HttpRequestHelper {

  private HttpRequestHelper() {}

  public static ImmutableMultimap<String, String> objectToQueryParams(@Nonnull Object object) {
    Preconditions.checkNotNull(object);

    JsonNode root = ObjectMapperUtils.mapper().valueToTree(object);
    if (!root.isObject()) {
      throw new IllegalArgumentException("Expected OBJECT node type but was " + root.getNodeType());
    }

    ImmutableMultimap.Builder<String, String> builder = ImmutableMultimap.builder();

    for (Iterator<Entry<String, JsonNode>> properties = root.fields(); properties.hasNext(); ) {
      Entry<String, JsonNode> property = properties.next();

      String name = property.getKey();
      JsonNode value = property.getValue();

      if (value.isArray()) {
        for (JsonNode element : value) {
          if (element.isNull()) {
            throw new IllegalArgumentException("Encountered null element inside collection " + name);
          } else {
            builder.putAll(valueNodeToQueryParam(name, element));
          }
        }
      } else {
        builder.putAll(valueNodeToQueryParam(name, value));
      }
    }

    return builder.build();
  }

  private static Multimap<String, String> valueNodeToQueryParam(String name, JsonNode value) {
    if (value.isNull()) {
      return ImmutableMultimap.of();
    } else if (value.isValueNode()) {
      return ImmutableMultimap.of(name, value.asText());
    } else {
      throw new IllegalArgumentException(String.format("Cannot convert property %s to query parameter, %s is not a valid type", name, value.getNodeType()));
    }
  }
}
