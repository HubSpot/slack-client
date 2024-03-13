package com.hubspot.slack.client.http.status;

public interface HttpStatus {
  int getCode();
  HttpStatusFamily getFamily();

  static HttpStatus forCode(int code) {
    return StandardHttpStatus.lookup(code).orElseGet(() -> new HttpStatusImpl(code));
  }

  default boolean isInformation() {
    return getFamily() == HttpStatusFamily.INFORMATION;
  }

  default boolean isSuccess() {
    return getFamily() == HttpStatusFamily.SUCCESS;
  }

  default boolean isRedirect() {
    return getFamily() == HttpStatusFamily.REDIRECT;
  }

  default boolean isNotFound() {
    return getCode() == StandardHttpStatus.NOT_FOUND.getCode();
  }

  default boolean isError() {
    return isClientError() || isServerError();
  }

  default boolean isClientError() {
    return getFamily() == HttpStatusFamily.CLIENT_ERROR;
  }

  default boolean isServerError() {
    return getFamily() == HttpStatusFamily.SERVER_ERROR;
  }

  /**
   * Valid status codes are within the range [200,600)
   */
  default boolean isValid() {
    return getFamily() != HttpStatusFamily.INVALID;
  }
}
