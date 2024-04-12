package com.hubspot.slack.client.paging;

import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;
import java.util.concurrent.CompletableFuture;

public abstract class AbstractPagedIterator<T, K>
  extends AbstractIterator<CompletableFuture<T>> {

  private LazyLoadingPage<T, K> page = null;

  @Override
  protected final CompletableFuture<T> computeNext() {
    try {
      if (page == null) {
        page = getPage(getInitialOffset());

        if (page == null) {
          return endOfData();
        }

        return page.getResults();
      }

      if (page.hasMore().join()) {
        page = getPage(page.getOffset().join());
        return page.getResults();
      }

      return endOfData();
    } catch (Exception e) {
      Throwables.throwIfUnchecked(e);
      throw new RuntimeException(e);
    }
  }

  protected abstract K getInitialOffset();

  protected abstract LazyLoadingPage<T, K> getPage(K offset) throws Exception;
}
