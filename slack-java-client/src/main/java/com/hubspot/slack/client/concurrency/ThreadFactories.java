package com.hubspot.slack.client.concurrency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.concurrent.ThreadFactory;

public class ThreadFactories {

  public static ThreadFactoryBuilder newBuilder(String name) {
    return new ThreadFactoryBuilder()
      .setNameFormat(name + "-%d")
      .setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler());
  }

  public static ThreadFactory newThreadFactory(String name) {
    return newBuilder(name).build();
  }

  public static ThreadFactory newDaemonThreadFactory(String name) {
    return newBuilder(name).setDaemon(true).build();
  }

  // prevent instantiation
  @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
  private ThreadFactories() {
    throw new AssertionError();
  }
}
