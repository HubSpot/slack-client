package com.hubspot.slack.client.interceptors.http;

import java.io.IOException;

import com.google.common.base.Charsets;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;

public class HttpFormatter {
  static String formatRequest(HttpRequest request) {
    StringBuilder builder = new StringBuilder();

    builder.append(request.getMethod()).append(" ").append(request.getUrl()).append("\n");

    builder.append("------------------------------------------\n");
    request.getHeaders().forEach(header -> builder.append(header.getName()).append(" = ").append(header.getValue()).append("\n"));
    builder.append("------------------------------------------\n");

    byte[] body = request.getBody(ObjectMapperUtils.mapper());
    if (body != null) {
      try {
        builder.append(
            ObjectMapperUtils.mapper().writerWithDefaultPrettyPrinter()
                .writeValueAsString(
                    ObjectMapperUtils.mapper().readValue(body, Object.class)));
      } catch (IOException ex) {
        builder.append(new String(body, Charsets.UTF_8));
      }
    }

    return builder.toString();
  }

  static String formatResponse(HttpResponse response) {
    StringBuilder builder = new StringBuilder();

    builder.append("------------------------------------------\n");

    response.getHeaders().forEach(header -> {
      builder.append(header.getName()).append(" = ").append(header.getValue()).append("\n");
    });

    builder.append("------------------------------------------\n");

    try {
      builder.append(ObjectMapperUtils.mapper().writerWithDefaultPrettyPrinter().writeValueAsString(response.getAs(Object.class)));
    } catch (IOException ex) {
      throw new RuntimeException("Unable to process response", ex);
    }

    return builder.toString();
  }
}
