package com.hubspot.slack.client.http;

import com.hubspot.horizon.HttpResponse;

public interface NioResponseCallback {
  void completed(HttpResponse response);
  void failed(Exception ex);
  void cancelled();
}
