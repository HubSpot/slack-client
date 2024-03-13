package com.hubspot.slack.client.concurrency;

public interface ThreadContextFollower<T, V> {
  /**
   * Get whatever thread local context this follower cares about.
   */
  T getContext(String namePrefix);

  /**
   * Set the context that was retrieved earlier in the current thread.
   */
  V setContext(String namePrefix, T item);

  /**
   * Clear the context after a thread.
   */
  default void clearContext(String namePrefix, V item) {}
}
