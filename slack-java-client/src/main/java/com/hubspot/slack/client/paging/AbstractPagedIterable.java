package com.hubspot.slack.client.paging;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractPagedIterable<T, K> implements Iterable<CompletableFuture<T>> {

  @Override
  public Iterator<CompletableFuture<T>> iterator() {
    return new AbstractPagedIterator<T, K>() {

      @Override
      protected K getInitialOffset() {
        return AbstractPagedIterable.this.getInitialOffset();
      }

      @Override
      protected LazyLoadingPage<T, K> getPage(K offset) throws Exception {
        return AbstractPagedIterable.this.getPage(offset);
      }
    };
  }

  protected abstract K getInitialOffset();
  protected abstract LazyLoadingPage<T, K> getPage(K offset) throws Exception;
}
