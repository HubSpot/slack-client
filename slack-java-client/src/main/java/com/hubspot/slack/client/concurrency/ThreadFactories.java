package com.hubspot.slack.client.concurrency;

import java.util.concurrent.ThreadFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class ThreadFactories {

  public static ThreadFactoryBuilder newBuilder(String name) {
    return new ThreadFactoryBuilder()
        .setNameFormat(name + "-%d")
        .setUncaughtExceptionHandler(new LoggingUncaughtExceptionHandler());
  }

  public static ThreadFactory newThreadFactory(String name) {
    return newBuilder(name)
        .build();
  }

  public static ThreadFactory newDaemonThreadFactory(String name) {
    return newBuilder(name)
        .setDaemon(true)
        .build();
  }

  // prevent instantiation
  private ThreadFactories() {
    throw new AssertionError();
  }

}
