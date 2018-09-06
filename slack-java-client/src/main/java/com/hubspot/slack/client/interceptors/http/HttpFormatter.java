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
    request.getHeaders().forEach(header -> builder.append(safeHeaderString(header.getName(), header.getValue())));
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

  static String safeHeaderString(
      String headerName,
      String headerValue
  ) {
    if (isAuthHeader(headerName)) {
      String[] authParts = headerValue.split(" ");
      if (authParts.length == 2) {
        String token = authParts[1];
        String elidedToken;
        if (token.length() < 9) {
          elidedToken = token.charAt(0) + "..." + token.charAt(token.length() - 1);
        } else {
          elidedToken = token.substring(0, 3) + "..." + token.substring(token.length() - 4, token.length());
        }

        return headerName + " = " + authParts[0] + " " + elidedToken;
      }
    }

    return headerName + " = " + headerValue + "\n";
  }

  private static boolean isAuthHeader(String headerName) {
    String lowercaseHeaderName = headerName.toLowerCase();
    if (lowercaseHeaderName.contains("auth")) {
      return true;
    }

    return false;
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
