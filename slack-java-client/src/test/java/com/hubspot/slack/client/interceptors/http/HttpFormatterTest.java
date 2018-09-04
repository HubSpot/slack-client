package com.hubspot.slack.client.interceptors.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class HttpFormatterTest {
  @Test
  public void itDoesElideAuthTokens() throws Exception {
    assertThat(
        HttpFormatter.safeHeaderString(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
        )
    )
        .describedAs("If it looks like a bearer HTTP auth token, we should redact the full token, providing just a fingerprint")
        .contains("Authorization = Bearer eyJ...iIkQ");

    assertThat(
        HttpFormatter.safeHeaderString(
            "Authorization",
            "Bearer eiQiIkQ"
        )
    )
        .describedAs("If it looks like a short HTTP auth token, we should redact the full token, providing just a fingerprint")
        .contains("Authorization = Bearer e...Q");

    assertThat(
        HttpFormatter.safeHeaderString(
            "Authorization",
            "Basic YWxhZGRpbjpvcGVuc2VzYW1l"
        )
    )
        .describedAs("If it looks like a basic HTTP auth token, we should redact the full token, providing just a fingerprint")
        .contains("Authorization = Basic YWx...YW1l");
  }
}
