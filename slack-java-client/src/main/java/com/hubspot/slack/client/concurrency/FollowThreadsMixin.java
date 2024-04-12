package com.hubspot.slack.client.concurrency;

import com.google.common.base.Throwables;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.concurrent.Callable;

public interface FollowThreadsMixin {
  String getNamePrefix();

  default <T> Collection<? extends Callable<T>> transformRequests(
    Collection<? extends Callable<T>> tasks
  ) {
    return Collections2.transform(tasks, this::transformRequest);
  }

  default <T> Callable<T> transformRequest(Callable<T> callable) {
    String namePrefix = getNamePrefix();
    ThreadContextHook.Context currentContext =
      ThreadContextHook.INSTANCE.buildThreadContext(namePrefix);
    return () -> {
      ThreadContextHook.Context initializedContext = null;
      try {
        initializedContext =
          ThreadContextHook.INSTANCE.initializeThreadContext(namePrefix, currentContext);
        return callable.call();
      } catch (Exception e) {
        throw new RuntimeException(e);
      } finally {
        ThreadContextHook.INSTANCE.clearThreadContext(namePrefix, initializedContext);
      }
    };
  }

  default Runnable transformRequest(Runnable runnable) {
    Callable<Void> callableVersion = () -> {
      runnable.run();
      return null;
    };
    Callable<Void> voidCallable = transformRequest(callableVersion);
    return () -> {
      try {
        voidCallable.call();
      } catch (Exception e) {
        Throwables.throwIfUnchecked(e);
        throw new RuntimeException(e);
      }
    };
  }
}
