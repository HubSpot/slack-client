package com.hubspot.slack.client.paging;

import java.util.concurrent.CompletableFuture;

import com.google.common.base.MoreObjects;

public class LazyLoadingPage<T, K> {
  private final CompletableFuture<T> results;
  private final CompletableFuture<Boolean> hasMore;
  private final CompletableFuture<K> offset;

  public LazyLoadingPage(
      CompletableFuture<T> results,
      CompletableFuture<Boolean> hasMore,
      CompletableFuture<K> offset
  ) {
    this.results = results;
    this.hasMore = hasMore;
    this.offset = offset;
  }

  public CompletableFuture<T> getResults() {
    return results;
  }

  public CompletableFuture<Boolean> hasMore() {
    return hasMore;
  }

  public CompletableFuture<K> getOffset() {
    return offset;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("results", results)
        .add("hasMore", hasMore)
        .add("offset", offset)
        .toString();
  }
}
