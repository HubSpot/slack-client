package com.hubspot.slack.client.concurrency;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * A helper to automatically follow threadlocal contexts
 * across thread boundaries where they make sense.
 * <p>
 * Writing a new context hook:
 * <p>
 * 1) Create an implementation of the interface {@link ThreadContextFollower}
 * 2) Create a file in /resources/META-INF/services called "com.hubspot.java.thread.ThreadContextFollower"
 * 3) Add a line containing the full class name of your implementation.
 * 4) Profit!
 * <p>
 * Adding a new place to thread the context:
 * <p>
 * 1) Save ThreadContextHook.Context currentContext = ThreadContextHook.INSTANCE.buildThreadContext(namePrefix) when about to leave
 * the current thread.
 * <p>
 * 2) In the other thread, call ThreadContextHook.INSTANCE.initializeThreadContext(namePrefix, currentContext) to hydreate the
 * old context in the current thread.
 */
public enum ThreadContextHook {
  INSTANCE;

  private final Collection<ThreadContextFollower> followers;

  ThreadContextHook() {
    ServiceLoader<ThreadContextFollower> loader = ServiceLoader.load(
      ThreadContextFollower.class
    );
    followers = ImmutableList.copyOf(loader);
  }

  public Context buildThreadContext(String namePrefix) {
    IdentityHashMap<ThreadContextFollower, Object> retainedObjects =
      new IdentityHashMap<>();
    for (ThreadContextFollower follower : followers) {
      retainedObjects.put(follower, follower.getContext(namePrefix));
    }
    return new Context(Collections.unmodifiableMap(retainedObjects));
  }

  @SuppressWarnings("unchecked")
  public Context initializeThreadContext(String namePrefix, Context context) {
    IdentityHashMap<ThreadContextFollower, Object> initializingObjects =
      new IdentityHashMap<>();
    for (ThreadContextFollower follower : followers) {
      Object followerContext = context.followersContext.get(follower);
      if (followerContext != null) {
        initializingObjects.put(
          follower,
          follower.setContext(namePrefix, followerContext)
        );
      }
    }
    return new Context(Collections.unmodifiableMap(initializingObjects));
  }

  @SuppressWarnings("unchecked")
  public void clearThreadContext(String namePrefix, Context context) {
    for (ThreadContextFollower follower : followers) {
      follower.clearContext(namePrefix, context.followersContext.get(follower));
    }
  }

  public Context buildThreadContext() {
    return buildThreadContext(null);
  }

  public void clearThreadContext(Context context) {
    clearThreadContext(null, context);
  }

  public Context initializeThreadContext(Context context) {
    return initializeThreadContext(null, context);
  }

  public static class Context {

    private final Map<ThreadContextFollower, Object> followersContext;

    private Context(Map<ThreadContextFollower, Object> followersContext) {
      this.followersContext = followersContext;
    }
  }
}
