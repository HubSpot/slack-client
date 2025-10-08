package com.hubspot.slack.client;

import com.hubspot.algebra.Result;
import com.hubspot.slack.client.methods.params.chat.workobject.flexpane.WorkObjectFlexpaneParams;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.EntityPresentDetailsResponse;
import java.util.concurrent.CompletableFuture;

public interface SlackClientV2 extends SlackClient {
  CompletableFuture<Result<EntityPresentDetailsResponse, SlackError>> entityPresentDetails(
    WorkObjectFlexpaneParams params
  );
}
