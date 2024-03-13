package com.hubspot.slack.client.concurrency;

import com.google.common.util.concurrent.ForwardingExecutorService;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FollowThreadLocalsExecutorService
  extends ForwardingExecutorService
  implements FollowThreadsMixin {

  private final ExecutorService delegate;
  private final String namePrefix;

  public FollowThreadLocalsExecutorService(ExecutorService delegate, String namePrefix) {
    this.delegate = delegate;
    this.namePrefix = namePrefix;
  }

  @Override
  public String getNamePrefix() {
    return namePrefix;
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
    throws InterruptedException {
    return delegate().invokeAll(transformRequests(tasks));
  }

  @Override
  public <T> List<Future<T>> invokeAll(
    Collection<? extends Callable<T>> tasks,
    long timeout,
    TimeUnit unit
  ) throws InterruptedException {
    return delegate().invokeAll(transformRequests(tasks), timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
    throws InterruptedException, ExecutionException {
    return delegate().invokeAny(transformRequests(tasks));
  }

  @Override
  public <T> T invokeAny(
    Collection<? extends Callable<T>> tasks,
    long timeout,
    TimeUnit unit
  ) throws InterruptedException, ExecutionException, TimeoutException {
    return delegate().invokeAny(transformRequests(tasks), timeout, unit);
  }

  @Override
  public void execute(Runnable command) {
    delegate().execute(transformRequest(command));
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return delegate().submit(transformRequest(task));
  }

  @Override
  public Future<?> submit(Runnable task) {
    return delegate().submit(transformRequest(task));
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return delegate().submit(transformRequest(task), result);
  }

  @Override
  protected ExecutorService delegate() {
    return delegate;
  }
}
