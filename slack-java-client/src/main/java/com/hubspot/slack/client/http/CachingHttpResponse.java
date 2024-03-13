package com.hubspot.slack.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubspot.horizon.Headers;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.internal.AbstractHttpResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class CachingHttpResponse extends AbstractHttpResponse {

  private final AbstractHttpResponse delegate;
  private final byte[] responseBytes;

  public static CachingHttpResponse from(AbstractHttpResponse response) {
    return new CachingHttpResponse(response);
  }

  private CachingHttpResponse(AbstractHttpResponse delegate) {
    this.delegate = delegate;
    this.responseBytes = delegate.getAsBytes();
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return delegate.getObjectMapper();
  }

  @Override
  public boolean isSuccess() {
    return delegate.isSuccess();
  }

  @Override
  public boolean isError() {
    return delegate.isError();
  }

  @Override
  public boolean isClientError() {
    return delegate.isClientError();
  }

  @Override
  public boolean isServerError() {
    return delegate.isServerError();
  }

  @Override
  public byte[] getAsBytes() {
    return Arrays.copyOf(responseBytes, responseBytes.length);
  }

  @Override
  public HttpRequest getRequest() {
    return delegate.getRequest();
  }

  @Override
  public int getStatusCode() {
    return delegate.getStatusCode();
  }

  @Override
  public Headers getHeaders() {
    return delegate.getHeaders();
  }

  @Override
  public InputStream getAsInputStream() {
    return new ByteArrayInputStream(responseBytes);
  }
}
