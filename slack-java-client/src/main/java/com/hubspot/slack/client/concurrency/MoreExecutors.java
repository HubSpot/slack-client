package com.hubspot.slack.client.concurrency;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MoreExecutors {

  private static final Logger LOG = LoggerFactory.getLogger(MoreExecutors.class);

  private static final AtomicBoolean HAS_WARNED_UNNAMED_THREADFACTORY = new AtomicBoolean(
    false
  );

  private MoreExecutors() {}

  /**
   * An opinionated builder for a new executor.
   * Rather than choosing between a cached executor, or a fixed
   * executor, this gives you a default thread pool that scales
   * from a core size to a max size with sensible defaults.
   * <p>
   * The only required parameter is a name for the thread factory,
   * and by default the thread pool is daemon.
   * <p>
   * {@code
   * <p>
   * ExecutorService service = Executors2.threadPoolNonDaemonExecutorBuilder("my-pool")
   * .setMaxSize(100)  // now we have 100 max threads
   * .setBlockWhenFull(true) // will block once 100 tasks are being worked on
   * .build();
   * }
   * <p>
   * The name of the threads will be of the form "{namePrefix}-N" where N is an
   * integer.
   */
  public static ThreadPoolExecutorBuilder threadPoolNonDaemonExecutorBuilder(
    String namePrefix
  ) {
    return new ThreadPoolExecutorBuilderImpl(namePrefix, false);
  }

  public static ThreadPoolExecutorBuilder threadPoolDaemonExecutorBuilder(
    String namePrefix
  ) {
    return new ThreadPoolExecutorBuilderImpl(namePrefix, true);
  }

  public interface ThreadPoolExecutorBuilder {
    /**
     * Sets the max number of threads in the thread pool.
     * If blockWithNoQueue is true, this is the max number
     * of items the service will hold. Any additional items
     * will cause blocking.
     * <p>
     * Defaults to 20
     */
    ThreadPoolExecutorBuilder setMaxSize(int maxSize);

    /**
     * Sets the keep alive time. After the keep alive time
     * has passed, the thread pool will scale down the threads
     * to the core size.
     * <p>
     * Defaults to 1 minutes.
     */
    ThreadPoolExecutorBuilder setKeepAlive(long keepAlive, TimeUnit timeUnit);

    /**
     * Sets the work queue, which defaults to a LinkedBlockingQueue unless
     * blockWhenFull is true.
     * <p>
     * DANGER! If you override the queue with blockWhenFull, be sure to set the
     * capacity to coreSize + 1 if you value determinism.
     */
    ThreadPoolExecutorBuilder setWorkQueue(BlockingQueue<Runnable> workQueue);

    /**
     * Sets the queue to use a fixed set of threads.
     * <p>
     * If true, the thread pool will not timeout threads.
     */
    ThreadPoolExecutorBuilder setFixed(boolean fixed);

    /**
     * Enable block when full. This means that the executor service will
     * block until there is room to actually run the task.
     *
     * @param backlogSize - The amount of pending tasks before blocking.
     */
    ThreadPoolExecutorBuilder setBlockWhenFull(int backlogSize);

    /**
     * If unbounded, the thread pool will behave like newCachedThreadPool().
     * That is - 0 core size and infinite max size.
     * <p>
     * Defaults to false.
     */
    ThreadPoolExecutorBuilder setUnbounded(boolean unbounded);

    /**
     * Disable block when full. This will enable the unbounded queue
     * with no blocking.
     */
    ThreadPoolExecutorBuilder clearBlockWhenFull();

    /**
     * If true, it will use a fair queue implementation for blockWhenFull.
     */
    ThreadPoolExecutorBuilder setFair(boolean fair);

    /**
     * Set whether or not to attempt to follow thread locals.
     * (Default: TRUE)
     */
    ThreadPoolExecutorBuilder setFollowThreadLocals(boolean followThreadLocals);

    /**
     * Set the timeout for shutdown of this executor service
     * Defaults to  30 seconds
     */
    ThreadPoolExecutorBuilder setShutdownTimeout(long timeout, TimeUnit timeUnit);

    /**
     * Build the executor service.
     */
    CloseableExecutorService build();
  }

  private static class ThreadPoolExecutorBuilderImpl
    implements ThreadPoolExecutorBuilder {

    private final String namePrefix;
    private final boolean daemon;
    private boolean fair = false;
    private boolean blockWhenFull = false;
    private boolean fixed = true;
    private boolean unbounded = false;
    private int maxSize = 20;
    private long keepAliveTime = 60;
    private Optional<Integer> queueSize = Optional.absent();
    private TimeUnit keepAliveTimeUnit = TimeUnit.SECONDS;
    private BlockingQueue<Runnable> queue = null;
    private boolean followThreadLocals = true;
    private long shutdownTimeout = 30;
    private TimeUnit shutdownTimeUnit = TimeUnit.SECONDS;

    private ThreadPoolExecutorBuilderImpl(String namePrefix, boolean daemon) {
      this.namePrefix = namePrefix;
      this.daemon = daemon;
    }

    @Override
    public ThreadPoolExecutorBuilder setFollowThreadLocals(boolean followThreadLocals) {
      this.followThreadLocals = followThreadLocals;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setMaxSize(int maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setKeepAlive(long keepAlive, TimeUnit timeUnit) {
      this.keepAliveTime = keepAlive;
      this.keepAliveTimeUnit = timeUnit;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setWorkQueue(BlockingQueue<Runnable> workQueue) {
      this.queue = workQueue;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setUnbounded(boolean unbounded) {
      this.unbounded = unbounded;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setFixed(boolean fixed) {
      this.fixed = fixed;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setBlockWhenFull(int queueSize) {
      this.blockWhenFull = true;
      this.queueSize = Optional.of(queueSize);
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder clearBlockWhenFull() {
      this.blockWhenFull = false;
      this.queueSize = Optional.absent();
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setFair(boolean fair) {
      this.fair = fair;
      return this;
    }

    @Override
    public ThreadPoolExecutorBuilder setShutdownTimeout(long timeout, TimeUnit timeUnit) {
      this.shutdownTimeout = timeout;
      this.shutdownTimeUnit = timeUnit;

      return this;
    }

    @Override
    public CloseableExecutorService build() {
      ThreadPoolExecutor executor = new ThreadPoolExecutor(
        unbounded ? 0 : maxSize,
        unbounded ? Integer.MAX_VALUE : maxSize,
        keepAliveTime,
        keepAliveTimeUnit,
        getQueue(),
        buildThreadFactory(),
        getRejectionPolicy()
      );
      if (!fixed) {
        executor.allowCoreThreadTimeOut(true);
      }

      ExecutorService executorService;
      if (followThreadLocals) {
        executorService = new FollowThreadLocalsExecutorService(executor, namePrefix);
      } else {
        executorService = executor;
      }

      return CloseableExecutorService.wrap(
        executorService,
        namePrefix,
        shutdownTimeout,
        shutdownTimeUnit
      );
    }

    private ThreadFactory buildThreadFactory() {
      if (Strings.isNullOrEmpty(namePrefix)) { // hack for people who don't have a name
        if (HAS_WARNED_UNNAMED_THREADFACTORY.compareAndSet(false, true)) {
          LOG.error(
            "(づ｡◕‿‿◕｡)づ  Your newborn ThreadFactory needs a name!",
            new RuntimeException("unnamed ThreadFactory")
          );
        }
        return new ThreadFactoryBuilder().setDaemon(daemon).build();
      } else {
        return ThreadFactories.newBuilder(namePrefix).setDaemon(daemon).build();
      }
    }

    private BlockingQueue<Runnable> getQueue() {
      if (queue != null) {
        return queue;
      } else if (unbounded) {
        return new SynchronousQueue<>(fair);
      } else if (queueSize.isPresent() && queueSize.get() == 0) {
        return new SynchronousQueue<>(fair);
      } else if (queueSize.isPresent()) {
        return new ArrayBlockingQueue<>(queueSize.get(), fair);
      } else {
        return new LinkedBlockingQueue<>();
      }
    }

    private RejectedExecutionHandler getRejectionPolicy() {
      if (blockWhenFull) {
        return new BlockCallerPolicy();
      } else {
        return new ThreadPoolExecutor.AbortPolicy();
      }
    }
  }

  private static class BlockCallerPolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
      try {
        // block until there's room
        executor.getQueue().put(r);
      } catch (InterruptedException e) {
        throw new RejectedExecutionException("Unexpected InterruptedException", e);
      }
    }
  }
}
