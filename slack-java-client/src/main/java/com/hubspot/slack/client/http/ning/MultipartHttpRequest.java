package com.hubspot.slack.client.http.ning;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.hubspot.horizon.Compression;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.Method;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.asynchttpclient.request.body.Body.BodyState;
import org.asynchttpclient.request.body.multipart.ByteArrayPart;
import org.asynchttpclient.request.body.multipart.FilePart;
import org.asynchttpclient.request.body.multipart.MultipartBody;
import org.asynchttpclient.request.body.multipart.MultipartUtils;
import org.asynchttpclient.request.body.multipart.Part;
import org.asynchttpclient.request.body.multipart.StringPart;

public class MultipartHttpRequest {

  private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
  private static final int BODY_CHUNK_SIZE = 8192;

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
      parts.add(
        new ByteArrayPart(
          Preconditions.checkNotNull(name),
          Preconditions.checkNotNull(value),
          null,
          charset,
          fileName
        )
      );
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
      parts.add(
        new FilePart(
          Preconditions.checkNotNull(name),
          Preconditions.checkNotNull(value),
          null,
          null,
          fileName
        )
      );
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
      try (
        MultipartBody multipartBody = MultipartUtils.newMultipartBody(
          parts,
          new DefaultHttpHeaders()
        )
      ) {
        builder.addHeader("Content-Type", multipartBody.getContentType());
        builder.setBody(readBody(multipartBody));
      }

      return builder.build();
    }

    private static byte[] readBody(MultipartBody multipartBody) {
      long contentLength = multipartBody.getContentLength();
      ByteBuf byteBuf = contentLength >= 0
        ? Unpooled.buffer(Math.toIntExact(contentLength))
        : Unpooled.buffer();
      try {
        while (multipartBody.transferTo(byteBuf) != BodyState.STOP) {
          byteBuf.ensureWritable(BODY_CHUNK_SIZE);
        }
        return ByteBufUtil.getBytes(byteBuf);
      } catch (IOException e) {
        throw new RuntimeException("Error generating body bytes", e);
      } finally {
        byteBuf.release();
      }
    }
  }
}
