package com.hubspot.slack.client.concurrency;

import java.io.Closeable;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloseableExecutorService implements ExecutorService, Closeable {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    CloseableExecutorService.class
  );

  private final ExecutorService delegate;
  private final String name;
  private final long shutdownTimeout;
  private final TimeUnit timeUnit;

  private CloseableExecutorService(
    ExecutorService delegate,
    String name,
    long shutdownTimeout,
    TimeUnit timeUnit
  ) {
    this.delegate = delegate;
    this.name = name;
    this.shutdownTimeout = shutdownTimeout;
    this.timeUnit = timeUnit;
  }

  public static CloseableExecutorService wrap(
    ExecutorService executorService,
    String name,
    long shutdownTimeout,
    TimeUnit timeUnit
  ) {
    return new CloseableExecutorService(executorService, name, shutdownTimeout, timeUnit);
  }

  @Override
  public void shutdown() {
    delegate.shutdown();
  }

  @Override
  public List<Runnable> shutdownNow() {
    return delegate.shutdownNow();
  }

  @Override
  public boolean isShutdown() {
    return delegate.isShutdown();
  }

  @Override
  public boolean isTerminated() {
    return delegate.isTerminated();
  }

  @Override
  public boolean awaitTermination(long timeout, TimeUnit unit)
    throws InterruptedException {
    return delegate.awaitTermination(timeout, unit);
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return delegate.submit(task);
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return delegate.submit(task, result);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return delegate.submit(task);
  }

  @Override
  public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
    throws InterruptedException {
    return delegate.invokeAll(tasks);
  }

  @Override
  public <T> List<Future<T>> invokeAll(
    Collection<? extends Callable<T>> tasks,
    long timeout,
    TimeUnit unit
  ) throws InterruptedException {
    return delegate.invokeAll(tasks, timeout, unit);
  }

  @Override
  public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
    throws InterruptedException, ExecutionException {
    return delegate.invokeAny(tasks);
  }

  @Override
  public <T> T invokeAny(
    Collection<? extends Callable<T>> tasks,
    long timeout,
    TimeUnit unit
  ) throws InterruptedException, ExecutionException, TimeoutException {
    return delegate.invokeAny(tasks, timeout, unit);
  }

  @Override
  public void execute(Runnable command) {
    delegate.execute(command);
  }

  @Override
  public void close() {
    // This is basically stolen from the ExecutorService javadoc with some slight modifications
    if (!isTerminated()) {
      shutdown();
      try {
        if (!awaitTermination(shutdownTimeout, timeUnit)) {
          shutdownNow();
          if (!awaitTermination(shutdownTimeout, timeUnit)) {
            LOGGER.error(
              "{}: Tasks in executor failed to terminate in time, continuing with shutdown regardless.",
              name
            );
          }
        }
      } catch (InterruptedException ie) {
        shutdownNow();
        Thread.currentThread().interrupt();
      }
    }
  }
}
