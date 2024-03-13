package com.hubspot.slack.client.http.ning;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.hubspot.horizon.Compression;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.Method;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.multipart.ByteArrayPart;
import com.ning.http.client.multipart.FilePart;
import com.ning.http.client.multipart.Part;
import com.ning.http.client.multipart.StringPart;
import com.ning.http.client.providers.jdk.MultipartRequestEntity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MultipartHttpRequest {

  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

  public static Builder newBuilder() {
    return new Builder();
  }

  public static final class Builder {

    private final List<Part> parts;
    private final HttpRequest.Builder builder;

    private Builder() {
      this.parts = new ArrayList<>();
      this.builder = HttpRequest.newBuilder();
    }

    public Builder addStringPart(@Nonnull String name, @Nonnull String value) {
      return addStringPart(name, value, DEFAULT_CHARSET);
    }

    public Builder addStringPart(
      @Nonnull String name,
      @Nonnull String value,
      @Nonnull Charset charset
    ) {
      return addStringPart(name, value, charset, null);
    }

    public Builder addStringPart(
      @Nonnull String name,
      @Nonnull String value,
      @Nonnull Charset charset,
      @Nullable String contentType
    ) {
      parts.add(
        new StringPart(
          Preconditions.checkNotNull(name),
          Preconditions.checkNotNull(value),
          contentType,
          Preconditions.checkNotNull(charset)
        )
      );
      return this;
    }

    public Builder addByteArrayPart(@Nonnull String name, @Nonnull byte[] value) {
      return addByteArrayPart(name, name, value);
    }

    public Builder addByteArrayPart(
      @Nonnull String name,
      @Nonnull String fileName,
      @Nonnull byte[] value
    ) {
      return addByteArrayPart(name, fileName, value, DEFAULT_CHARSET);
    }

    public Builder addByteArrayPart(
      @Nonnull String name,
      @Nonnull String fileName,
      @Nonnull byte[] value,
      Charset charset
    ) {
      ByteArrayPart part = new ByteArrayPart(
        Preconditions.checkNotNull(name),
        Preconditions.checkNotNull(value),
        null,
        charset
      );
      part.setFileName(fileName);
      parts.add(part);
      return this;
    }

    public Builder addFilePart(@Nonnull String name, @Nonnull File value) {
      return addFilePart(name, value.getName(), value);
    }

    public Builder addFilePart(
      @Nonnull String name,
      @Nonnull String fileName,
      @Nonnull File value
    ) {
      FilePart part = new FilePart(
        Preconditions.checkNotNull(name),
        Preconditions.checkNotNull(value)
      );
      part.setFileName(fileName);
      parts.add(part);
      return this;
    }

    public Builder addHeader(String name, String value) {
      builder.addHeader(name, value);
      return this;
    }

    public Builder addQueryParam(@Nonnull String name, @Nullable String value) {
      builder.setQueryParam(name).to(value);
      return this;
    }

    public Builder addQueryParam(@Nonnull String name, int value) {
      builder.setQueryParam(name).to(value);
      return this;
    }

    public Builder addQueryParam(@Nonnull String name, long value) {
      builder.setQueryParam(name).to(value);
      return this;
    }

    public Builder addQueryParam(@Nonnull String name, @Nonnull Iterable<String> values) {
      builder.setQueryParam(name).to(values);
      return this;
    }

    public Builder addQueryParams(@Nonnull Multimap<String, String> mm) {
      mm.keySet().forEach(k -> builder.setQueryParam(k).to(mm.get(k)));
      return this;
    }

    public Builder setCompression(@Nonnull Compression compression) {
      builder.setCompression(compression);
      return this;
    }

    public Builder setUrl(@Nonnull String url) {
      builder.setUrl(url);
      return this;
    }

    public Builder setMethod(@Nonnull Method method) {
      builder.setMethod(method);
      return this;
    }

    public Builder setAccept(@Nonnull HttpRequest.ContentType accept) {
      builder.setAccept(accept);
      return this;
    }

    public Builder addBasicAuthentication(
      @Nonnull String userName,
      @Nullable String password
    ) {
      builder.addBasicAuth(userName, password);
      return this;
    }

    public HttpRequest build() {
      final byte[] body;
      MultipartRequestEntity requestEntity = new TerminatingMultipartRequestEntity(
        parts,
        new FluentCaseInsensitiveStringsMap()
      );
      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
        requestEntity.writeRequest(baos);
        baos.flush();
        body = baos.toByteArray();
      } catch (IOException e) {
        throw new RuntimeException("Error generating body bytes", e);
      }

      builder.addHeader("Content-Type", requestEntity.getContentType());
      builder.setBody(body);

      return builder.build();
    }
  }
}
