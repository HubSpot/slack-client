package com.hubspot.slack.client.interceptors.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class HttpFormatterTest {

  @Test
  public void itDoesElideAuthTokensInHeader() throws Exception {
    assertThat(
      HttpFormatter.safeHeaderString(
        "Authorization",
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2V4YW1wbGUuYXV0aDAuY29tLyIsImF1ZCI6Imh0dHBzOi8vYXBpLmV4YW1wbGUuY29tL2NhbGFuZGFyL3YxLyIsInN1YiI6InVzcl8xMjMiLCJpYXQiOjE0NTg3ODU3OTYsImV4cCI6MTQ1ODg3MjE5Nn0.CA7eaHjIHz5NxeIJoFK9krqaeZrPLwmMmgI_XiQiIkQ"
      )
    )
      .describedAs(
        "If it looks like a bearer HTTP auth token, we should redact the full token, providing just a fingerprint"
      )
      .contains("Authorization = Bearer eyJ...iIkQ");

    assertThat(HttpFormatter.safeHeaderString("Authorization", "Bearer eiQiIkQ"))
      .describedAs(
        "If it looks like a short HTTP auth token, we should redact the full token, providing just a fingerprint"
      )
      .contains("Authorization = Bearer e...Q");

    assertThat(
      HttpFormatter.safeHeaderString("Authorization", "Basic YWxhZGRpbjpvcGVuc2VzYW1l")
    )
      .describedAs(
        "If it looks like a basic HTTP auth token, we should redact the full token, providing just a fingerprint"
      )
      .contains("Authorization = Basic YWx...YW1l");
  }

  @Test
  public void itDoesElideAuthTokensInUrl() throws Exception {
    assertThat(
      HttpFormatter.urlWithRedactedToken(
        "https://slack.com/api/channels.info?token=xoxb-000000000000-AAAAAAoM5xCesZ1d9HDFhKMG"
      )
    )
      .describedAs(
        "If it looks like a URL with only the raw token, we should redact the full token, providing just a fingerprint"
      )
      .isEqualTo("https://slack.com/api/channels.info?token=xox...hKMG");

    assertThat(
      HttpFormatter.urlWithRedactedToken(
        "https://slack.com/api/channels.info?foo=bar&token=xoxb-000000000000-AAAAAAoM5xCesZ1d9HDFhKMG&baz=why"
      )
    )
      .describedAs(
        "If it looks like a URL with the raw token and other query params, we should redact the full token, providing just a fingerprint"
      )
      .isEqualTo("https://slack.com/api/channels.info?foo=bar&token=xox...hKMG&baz=why");
  }
}
