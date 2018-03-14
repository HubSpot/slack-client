package com.hubspot.slack.client.http.status;

import java.util.Objects;

public class HttpStatusImpl implements HttpStatus {

  private final int code;
  private final HttpStatusFamily family;

  HttpStatusImpl(int code) {
    this.code = code;
    this.family = HttpStatusFamily.getFamily(code);
  }

  @Override
  public int getCode() {
    return code;
  }

  @Override
  public HttpStatusFamily getFamily() {
    return family;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof HttpStatusImpl)) {
      return false;
    }
    return this.code == ((HttpStatusImpl) obj).code;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(code);
  }

  @Override
  public String toString() {
    return getClass().getName() + "{code=" + code + "}";
  }
}
