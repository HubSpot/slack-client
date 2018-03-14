package com.hubspot.slack.client.paging;

import java.util.concurrent.CompletableFuture;

import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;

public abstract class AbstractPagedIterator<T, K> extends AbstractIterator<CompletableFuture<T>> {

  private LazyLoadingPage<T, K> page = null;

  @Override
  protected final CompletableFuture<T> computeNext() {
    try {
      if (page == null || page.hasMore().join()) {
        page = getPage(getInitialOffset());
        return page.getResults();
      }

      return endOfData();
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

  protected abstract K getInitialOffset();
  protected abstract LazyLoadingPage<T, K> getPage(K offset) throws Exception;
}
