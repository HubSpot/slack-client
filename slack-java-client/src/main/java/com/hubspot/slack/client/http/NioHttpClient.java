package com.hubspot.slack.client.http;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hubspot.horizon.AsyncHttpClient;
import com.hubspot.horizon.AsyncHttpClient.Callback;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.Options;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.apache.internal.CachedHttpResponse;
import com.hubspot.horizon.internal.AbstractHttpResponse;

public class NioHttpClient implements Closeable {
  private static final ExecutorService CALLBACK_EXECUTOR = new ThreadPoolExecutor(
      0,
      Integer.MAX_VALUE,
      60,
      TimeUnit.SECONDS,
      new SynchronousQueue<>(false)
  );

  private final AsyncHttpClient delegate;

  public interface Factory {
    NioHttpClient wrap(@Assisted AsyncHttpClient delegate);
  }

  @Inject
  public NioHttpClient(
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
          try {
            HttpResponse cached = CachedHttpResponse.from((AbstractHttpResponse) response);
            responseFuture.complete(cached);
          } catch (IOException ex) {
            responseFuture.completeExceptionally(ex);
            throw new RuntimeException("Unable to cache http response", ex);
          }
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
