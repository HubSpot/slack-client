package com.hubspot.slack.client.http;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hubspot.horizon.AsyncHttpClient;
import com.hubspot.horizon.AsyncHttpClient.Callback;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.Options;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.internal.AbstractHttpResponse;
import com.hubspot.slack.client.concurrency.MoreExecutors;

public class NioHttpClient implements Closeable {
  private static final ExecutorService CALLBACK_EXECUTOR = MoreExecutors.threadPoolDaemonExecutorBuilder("NioHttpClient-Callback")
      .setFollowThreadLocals(false)
      .setUnbounded(true)
      .build();

  private final AsyncHttpClient delegate;

  public interface Factory {
    NioHttpClient wrap(@Assisted AsyncHttpClient delegate);
  }

  @Inject
  NioHttpClient(
      @Assisted AsyncHttpClient delegate
  ) {
    this.delegate = delegate;
  }

  /**
   * @deprecated Use {@link #executeCompletableFuture(HttpRequest)}
   */
  @Deprecated
  public ListenableFuture<HttpResponse> execute(HttpRequest httpRequest) {
    return delegate.execute(httpRequest);
  }

  /**
   * @deprecated Use {@link #executeCompletableFuture(HttpRequest)}
   */
  @Deprecated
  public ListenableFuture<HttpResponse> execute(HttpRequest httpRequest, Options options) {
    return delegate.execute(httpRequest, options);
  }

  /**
   * @deprecated Use {@link #executeCompletableFuture(HttpRequest)}
   */
  @Deprecated
  public void execute(HttpRequest httpRequest, Callback callback) {
    delegate.execute(httpRequest, callback);
  }

  /**
   * @deprecated Use {@link #executeCompletableFuture(HttpRequest)}
   */
  @Deprecated
  public void execute(HttpRequest httpRequest, Options options, Callback callback) {
    delegate.execute(httpRequest, options, callback);
  }

  public CompletableFuture<HttpResponse> executeCompletableFuture(HttpRequest request) {
    CompletableFuture<HttpResponse> responseFuture = new CompletableFuture<>();
    execute(request, new Callback() {
      @Override
      public void completed(HttpResponse response) {
        if (response instanceof AbstractHttpResponse) {
          HttpResponse cached = CachingHttpResponse.from((AbstractHttpResponse) response);
          responseFuture.complete(cached);
        } else {
          responseFuture.complete(response);
        }
      }

      @Override
      public void failed(Exception ex) {
        responseFuture.completeExceptionally(ex);
      }
    });

    return responseFuture.thenApplyAsync(Function.identity(), CALLBACK_EXECUTOR);
  }

  @Override
  public void close() throws IOException {
    delegate.close();
  }
}
