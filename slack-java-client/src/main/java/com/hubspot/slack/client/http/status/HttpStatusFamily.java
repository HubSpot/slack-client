package com.hubspot.slack.client.http.status;

public enum HttpStatusFamily {
  INFORMATION,
  SUCCESS,
  REDIRECT,
  CLIENT_ERROR,
  SERVER_ERROR,
  INVALID,
  ;

  public static HttpStatusFamily getFamily(int code) {
    if (100 <= code && code < 200) {
      return INFORMATION;
    } else if (200 <= code && code < 300) {
      return SUCCESS;
    } else if (300 <= code && code < 400) {
      return REDIRECT;
    } else if (400 <= code && code < 500) {
      return CLIENT_ERROR;
    } else if (500 <= code && code < 600) {
      return SERVER_ERROR;
    }

    return INVALID;
  }
}
