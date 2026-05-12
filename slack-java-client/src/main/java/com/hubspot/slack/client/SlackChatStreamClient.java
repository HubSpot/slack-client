package com.hubspot.slack.client;

import com.hubspot.algebra.Result;
import com.hubspot.slack.client.methods.params.stream.ChatAppendStreamParams;
import com.hubspot.slack.client.methods.params.stream.ChatStartStreamParams;
import com.hubspot.slack.client.methods.params.stream.ChatStopStreamParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatStartStreamResponse;
import com.hubspot.slack.client.models.response.chat.ChatStopStreamResponse;
import java.util.concurrent.CompletableFuture;

public interface SlackChatStreamClient {
  String NOT_IMPLEMENTED = "Method not implemented";

  default CompletableFuture<Result<ChatStartStreamResponse, SlackError>> startStream(
    ChatStartStreamParams params
  ) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  default CompletableFuture<Result<ChatStartStreamResponse, SlackError>> appendStream(
    ChatAppendStreamParams params
  ) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }

  default CompletableFuture<Result<ChatStopStreamResponse, SlackError>> stopStream(
    ChatStopStreamParams params
  ) {
    throw new UnsupportedOperationException(NOT_IMPLEMENTED);
  }
}
