package com.hubspot.slack.client.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingUncaughtExceptionHandler
    implements Thread.UncaughtExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(LoggingUncaughtExceptionHandler.class);

  @Override
  public void uncaughtException(Thread t, Throwable e) {
    LOG.error("[thread={}] uncaught exception", t.getName(), e);
  }

}
