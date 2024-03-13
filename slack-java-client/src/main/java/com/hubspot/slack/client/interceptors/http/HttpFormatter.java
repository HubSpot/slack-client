package com.hubspot.slack.client.interceptors.http;

import com.google.common.base.Charsets;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import java.io.IOException;

public class HttpFormatter {

  static String formatRequest(HttpRequest request) {
    StringBuilder builder = new StringBuilder();

    String url = request.getUrl().toString();
    if (urlContainsRawAuthToken(url)) {
      url = urlWithRedactedToken(url);
    }

    builder.append(request.getMethod()).append(" ").append(url).append("\n");

    builder.append("------------------------------------------\n");
    request
      .getHeaders()
      .forEach(header ->
        builder.append(safeHeaderString(header.getName(), header.getValue()))
      );
    builder.append("------------------------------------------\n");

    byte[] body = request.getBody(ObjectMapperUtils.mapper());
    if (body != null) {
      try {
        builder.append(
          ObjectMapperUtils
            .mapper()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(ObjectMapperUtils.mapper().readValue(body, Object.class))
        );
      } catch (IOException ex) {
        builder.append(new String(body, Charsets.UTF_8));
      }
    }

    return builder.toString();
  }

  private static boolean urlContainsRawAuthToken(String url) {
    return url.contains("token=");
  }

  static String urlWithRedactedToken(String rawUrl) {
    String[] urlSplitOnToken = rawUrl.split("token=");
    String prefix = urlSplitOnToken[0];
    String rest = urlSplitOnToken[1];

    String[] restOfUrlSplit = rest.split("&", 2);
    String token = restOfUrlSplit[0];

    StringBuilder redactedUrl = new StringBuilder();

    redactedUrl.append(prefix).append("token=").append(redactedToken(token));

    if (restOfUrlSplit.length > 1) {
      redactedUrl.append("&").append(restOfUrlSplit[1]);
    }

    return redactedUrl.toString();
  }

  private static String redactedToken(String token) {
    if (token.length() < 9) {
      return token.charAt(0) + "..." + token.charAt(token.length() - 1);
    }

    return (
      token.substring(0, 3) + "..." + token.substring(token.length() - 4, token.length())
    );
  }

  static String safeHeaderString(String headerName, String headerValue) {
    if (isAuthHeader(headerName)) {
      String[] authParts = headerValue.split(" ");
      if (authParts.length == 2) {
        String token = authParts[1];

        return headerName + " = " + authParts[0] + " " + redactedToken(token);
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

    response
      .getHeaders()
      .forEach(header -> {
        builder
          .append(header.getName())
          .append(" = ")
          .append(header.getValue())
          .append("\n");
      });

    builder.append("------------------------------------------\n");

    try {
      builder.append(
        ObjectMapperUtils
          .mapper()
          .writerWithDefaultPrettyPrinter()
          .writeValueAsString(response.getAs(Object.class))
      );
    } catch (IOException ex) {
      throw new RuntimeException("Unable to process response", ex);
    }

    return builder.toString();
  }
}
