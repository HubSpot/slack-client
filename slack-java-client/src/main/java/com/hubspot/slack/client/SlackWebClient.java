package com.hubspot.slack.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.hubspot.algebra.Result;
import com.hubspot.horizon.HttpConfig;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.ContentType;
import com.hubspot.horizon.HttpRequest.Method;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.ning.NingAsyncHttpClient;
import com.hubspot.slack.client.concurrency.CloseableExecutorService;
import com.hubspot.slack.client.concurrency.MoreExecutors;
import com.hubspot.slack.client.http.NioHttpClient;
import com.hubspot.slack.client.http.NioHttpClientFactory;
import com.hubspot.slack.client.http.ning.MultipartHttpRequest;
import com.hubspot.slack.client.http.ning.MultipartHttpRequest.Builder;
import com.hubspot.slack.client.interceptors.calls.SlackMethodAcceptor;
import com.hubspot.slack.client.interceptors.http.DefaultHttpRequestDebugger;
import com.hubspot.slack.client.interceptors.http.DefaultHttpResponseDebugger;
import com.hubspot.slack.client.interceptors.http.HttpRequestHelper;
import com.hubspot.slack.client.interceptors.http.RequestDebugger;
import com.hubspot.slack.client.interceptors.http.ResponseDebugger;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.methods.JsonStatus;
import com.hubspot.slack.client.methods.SlackMethod;
import com.hubspot.slack.client.methods.SlackMethods;
import com.hubspot.slack.client.methods.params.assistant.SetSuggestedPromptsParams;
import com.hubspot.slack.client.methods.params.assistant.SetThreadStatusParams;
import com.hubspot.slack.client.methods.params.assistant.SetTitleParams;
import com.hubspot.slack.client.methods.params.auth.AuthRevokeParams;
import com.hubspot.slack.client.methods.params.bookmarks.BookmarksAddParams;
import com.hubspot.slack.client.methods.params.bookmarks.BookmarksEditParams;
import com.hubspot.slack.client.methods.params.bookmarks.BookmarksListParams;
import com.hubspot.slack.client.methods.params.bookmarks.BookmarksRemoveParams;
import com.hubspot.slack.client.methods.params.calls.CallsAddParams;
import com.hubspot.slack.client.methods.params.calls.CallsEndParams;
import com.hubspot.slack.client.methods.params.calls.CallsInfoParams;
import com.hubspot.slack.client.methods.params.calls.CallsParticipantsAddParams;
import com.hubspot.slack.client.methods.params.calls.CallsParticipantsRemoveParams;
import com.hubspot.slack.client.methods.params.calls.CallsUpdateParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsFilter;
import com.hubspot.slack.client.methods.params.channels.ChannelsHistoryParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsInfoParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsKickParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsListParams;
import com.hubspot.slack.client.methods.params.channels.FindRepliesParams;
import com.hubspot.slack.client.methods.params.channels.PagingDirection;
import com.hubspot.slack.client.methods.params.chat.ChatDeleteParams;
import com.hubspot.slack.client.methods.params.chat.ChatDeleteScheduledMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatGetPermalinkParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostEphemeralMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatScheduleMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatScheduledMessagesListParams;
import com.hubspot.slack.client.methods.params.chat.ChatUnfurlParams;
import com.hubspot.slack.client.methods.params.chat.ChatUpdateMessageParams;
import com.hubspot.slack.client.methods.params.chat.workobject.flexpane.WorkObjectFlexpaneParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationArchiveParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationCreateParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationInviteParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationInviteSharedParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationKickParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationMemberParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationOpenParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationSetPurposeParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationSetTopicParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationUnarchiveParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsFilter;
import com.hubspot.slack.client.methods.params.conversations.ConversationsHistoryParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsInfoParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsJoinParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsListParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsRepliesParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsUserParams;
import com.hubspot.slack.client.methods.params.dialog.DialogOpenParams;
import com.hubspot.slack.client.methods.params.dnd.DndInfoParams;
import com.hubspot.slack.client.methods.params.dnd.DndSetSnoozeParams;
import com.hubspot.slack.client.methods.params.files.CompleteUploadExternalParams;
import com.hubspot.slack.client.methods.params.files.FilesInfoParams;
import com.hubspot.slack.client.methods.params.files.FilesSharedPublicUrlParams;
import com.hubspot.slack.client.methods.params.files.FilesUploadParams;
import com.hubspot.slack.client.methods.params.files.GetUploadUrlExternalParams;
import com.hubspot.slack.client.methods.params.group.GroupsKickParams;
import com.hubspot.slack.client.methods.params.group.GroupsListParams;
import com.hubspot.slack.client.methods.params.im.ImOpenParams;
import com.hubspot.slack.client.methods.params.migration.MigrationExchangeParams;
import com.hubspot.slack.client.methods.params.reactions.ReactionsAddParams;
import com.hubspot.slack.client.methods.params.search.SearchMessagesParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupCreateParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupDisableParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupEnableParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupListParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupUpdateParams;
import com.hubspot.slack.client.methods.params.usergroups.users.UsergroupUsersUpdateParams;
import com.hubspot.slack.client.methods.params.users.SetUserProfileParams;
import com.hubspot.slack.client.methods.params.users.UserEmailParams;
import com.hubspot.slack.client.methods.params.users.UsersInfoParams;
import com.hubspot.slack.client.methods.params.users.UsersListParams;
import com.hubspot.slack.client.methods.params.views.OpenViewParams;
import com.hubspot.slack.client.methods.params.views.PublishViewParams;
import com.hubspot.slack.client.methods.params.views.UpdateViewParams;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.conversations.Conversation;
import com.hubspot.slack.client.models.group.SlackGroup;
import com.hubspot.slack.client.models.response.FindRepliesResponse;
import com.hubspot.slack.client.models.response.ResponseMetadata;
import com.hubspot.slack.client.models.response.SimpleSlackResponse;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.SlackErrorResponse;
import com.hubspot.slack.client.models.response.SlackErrorType;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.response.auth.AuthRevokeResponse;
import com.hubspot.slack.client.models.response.auth.AuthTestResponse;
import com.hubspot.slack.client.models.response.bookmarks.BookmarkAddResponse;
import com.hubspot.slack.client.models.response.bookmarks.BookmarkEditResponse;
import com.hubspot.slack.client.models.response.bookmarks.BookmarkListResponse;
import com.hubspot.slack.client.models.response.bookmarks.BookmarkRemoveResponse;
import com.hubspot.slack.client.models.response.calls.CallsAddResponse;
import com.hubspot.slack.client.models.response.calls.CallsEndResponse;
import com.hubspot.slack.client.models.response.calls.CallsInfoResponse;
import com.hubspot.slack.client.models.response.calls.CallsParticipantsAddResponse;
import com.hubspot.slack.client.models.response.calls.CallsParticipantsRemoveResponse;
import com.hubspot.slack.client.models.response.calls.CallsUpdateResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsHistoryResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsInfoResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsKickResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsListResponse;
import com.hubspot.slack.client.models.response.channels.SharedChannelInviteResponse;
import com.hubspot.slack.client.models.response.chat.ChatDeleteResponse;
import com.hubspot.slack.client.models.response.chat.ChatDeleteScheduledMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatGetPermalinkResponse;
import com.hubspot.slack.client.models.response.chat.ChatPostEphemeralMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatScheduleMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatScheduledMessagesListResponse;
import com.hubspot.slack.client.models.response.chat.ChatUnfurlResponse;
import com.hubspot.slack.client.models.response.chat.ChatUpdateMessageResponse;
import com.hubspot.slack.client.models.response.chat.EntityPresentDetailsResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationKickResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationListResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationMemberResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationSetPurposeResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationSetTopicResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsArchiveResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsCreateResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsHistoryResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsInfoResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsInviteResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsOpenResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsRepliesResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsUnarchiveResponse;
import com.hubspot.slack.client.models.response.dialog.DialogOpenResponse;
import com.hubspot.slack.client.models.response.dnd.DndInfoResponse;
import com.hubspot.slack.client.models.response.dnd.DndSnoozeResponse;
import com.hubspot.slack.client.models.response.emoji.EmojiListResponse;
import com.hubspot.slack.client.models.response.files.CompleteUploadExternalResponse;
import com.hubspot.slack.client.models.response.files.FilesInfoResponse;
import com.hubspot.slack.client.models.response.files.FilesSharedPublicUrlResponse;
import com.hubspot.slack.client.models.response.files.FilesUploadResponse;
import com.hubspot.slack.client.models.response.files.GetUploadUrlExternalResponse;
import com.hubspot.slack.client.models.response.group.GroupsKickResponse;
import com.hubspot.slack.client.models.response.group.GroupsListResponse;
import com.hubspot.slack.client.models.response.im.ImOpenResponse;
import com.hubspot.slack.client.models.response.migration.MigrationExchangeResponse;
import com.hubspot.slack.client.models.response.reactions.AddReactionResponse;
import com.hubspot.slack.client.models.response.search.SearchMessageResponse;
import com.hubspot.slack.client.models.response.team.TeamInfoResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupCreateResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupDisableResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupEnableResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupListResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupUpdateResponse;
import com.hubspot.slack.client.models.response.usergroups.users.UsergroupUsersUpdateResponse;
import com.hubspot.slack.client.models.response.users.UsersInfoResponse;
import com.hubspot.slack.client.models.response.users.UsersListResponse;
import com.hubspot.slack.client.models.response.users.UsersProfileResponse;
import com.hubspot.slack.client.models.response.views.HomeTabViewCommandResponse;
import com.hubspot.slack.client.models.response.views.ModalViewCommandResponse;
import com.hubspot.slack.client.models.usergroups.SlackUsergroup;
import com.hubspot.slack.client.models.users.SlackUser;
import com.hubspot.slack.client.paging.AbstractPagedIterable;
import com.hubspot.slack.client.paging.LazyLoadingPage;
import com.hubspot.slack.client.ratelimiting.ByMethodRateLimiter;
import com.hubspot.slack.client.ratelimiting.SlackRateLimiter;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackWebClient implements SlackClient {

  public static final int RATE_LIMIT_SENTINEL_VALUE = -1;
  public static final int RATE_LIMIT_LOG_WARNING_THRESHOLD_SECONDS = 5;

  private static final Logger LOG = LoggerFactory.getLogger(SlackWebClient.class);
  private static final HttpConfig DEFAULT_CONFIG = HttpConfig
    .newBuilder()
    .setObjectMapper(ObjectMapperUtils.mapper())
    .build();
  private static final AtomicLong REQUEST_COUNTER = new AtomicLong(0);

  private final NioHttpClient nioHttpClient;
  private final CloseableExecutorService recursingExecutor;
  private final ByMethodRateLimiter defaultRateLimiter;
  private final SlackClientRuntimeConfig config;

  private final SlackMethodAcceptor methodAcceptor;
  private final RequestDebugger requestDebugger;
  private final ResponseDebugger responseDebugger;

  public interface Factory {
    SlackWebClient build(SlackClientRuntimeConfig config);
  }

  public SlackWebClient(
    DefaultHttpRequestDebugger defaultHttpRequestDebugger,
    DefaultHttpResponseDebugger defaultHttpResponseDebugger,
    NioHttpClient.Factory nioHttpClientFactory,
    ByMethodRateLimiter defaultRateLimiter,
    SlackClientRuntimeConfig config
  ) {
    this.nioHttpClient =
      config
        .getHttpClient()
        .orElseGet(() ->
          nioHttpClientFactory.wrap(
            new NingAsyncHttpClient(config.getHttpConfig().orElse(DEFAULT_CONFIG))
          )
        );
    this.defaultRateLimiter = defaultRateLimiter;
    this.config = config;

    this.methodAcceptor =
      config
        .getMethodFilter()
        .orElse(
          new SlackMethodAcceptor() {
            @Override
            public String getFailureExplanation(SlackMethod method, Object params) {
              throw new IllegalStateException(
                "We can't fail with the pasthrough acceptor"
              );
            }

            @Override
            public boolean test(SlackMethod slackMethod, Object o) {
              return true;
            }
          }
        );
    this.requestDebugger = config.getRequestDebugger().orElse(defaultHttpRequestDebugger);
    this.responseDebugger =
      config.getResponseDebugger().orElse(defaultHttpResponseDebugger);
    this.recursingExecutor =
      MoreExecutors
        .threadPoolDaemonExecutorBuilder("Slack-recursive-callbacks")
        .setFollowThreadLocals(true)
        .setUnbounded(true)
        .build();
  }

  SlackWebClient(
    NioHttpClientFactory nioHttpClientFactory,
    SlackClientRuntimeConfig config
  ) {
    this(
      new DefaultHttpRequestDebugger(),
      new DefaultHttpResponseDebugger(),
      nioHttpClientFactory,
      new ByMethodRateLimiter(),
      config
    );
  }

  @Override
  public CompletableFuture<Result<AuthTestResponse, SlackError>> testAuth() {
    return postSlackCommand(
      SlackMethods.auth_test,
      Collections.emptyMap(),
      AuthTestResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<AuthRevokeResponse, SlackError>> revokeAuth(
    AuthRevokeParams params
  ) {
    return postSlackCommand(SlackMethods.auth_revoke, params, AuthRevokeResponse.class);
  }

  @Override
  public CompletableFuture<Result<SearchMessageResponse, SlackError>> searchMessages(
    SearchMessagesParams params
  ) {
    return postSlackCommand(
      SlackMethods.search_messages,
      params,
      SearchMessageResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<FindRepliesResponse, SlackError>> findReplies(
    FindRepliesParams params
  ) {
    switch (params.getChannelType()) {
      case GROUP:
        return postSlackCommand(
          SlackMethods.groups_replies,
          params,
          FindRepliesResponse.class
        );
      case CHANNEL:
        return postSlackCommand(
          SlackMethods.channels_replies,
          params,
          FindRepliesResponse.class
        );
      default:
        throw new IllegalArgumentException(
          params.getChannelType() + " is not a supported channel type for reply fetching"
        );
    }
  }

  @Override
  public CompletableFuture<Result<ConversationsRepliesResponse, SlackError>> getConversationReplies(
    ConversationsRepliesParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_replies,
      params,
      ConversationsRepliesResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationsInfoResponse, SlackError>> joinConversation(
    ConversationsJoinParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_join,
      params,
      ConversationsInfoResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationSetPurposeResponse, SlackError>> setConversationPurpose(
    ConversationSetPurposeParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_setPurpose,
      params,
      ConversationSetPurposeResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationSetTopicResponse, SlackError>> setConversationTopic(
    ConversationSetTopicParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_setTopic,
      params,
      ConversationSetTopicResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationKickResponse, SlackError>> kickUserFromConversation(
    ConversationKickParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_kick,
      params,
      ConversationKickResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackUser>, SlackError>>> listUsers() {
    return new AbstractPagedIterable<Result<List<SlackUser>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackUser>, SlackError>, String> getPage(
        String offset
      ) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching user page from {}", offset);
        }

        UsersListParams.Builder requestBuilder = UsersListParams
          .builder()
          .setLimit(config.getUsersListBatchSize().get());
        Optional.ofNullable(offset).ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<UsersListResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.users_list,
            requestBuilder.build(),
            UsersListResponse.class
          );

        CompletableFuture<Result<List<SlackUser>, SlackError>> pageFuture =
          resultFuture.thenApply(result -> result.mapOk(UsersListResponse::getMembers));

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public CompletableFuture<Result<UsersListResponse, SlackError>> listUsersPaginated(
    UsersListParams params
  ) {
    return postSlackCommand(SlackMethods.users_list, params, UsersListResponse.class);
  }

  private <T extends SlackResponse> CompletableFuture<Optional<String>> extractNextCursor(
    CompletableFuture<Result<T, SlackError>> responseFuture
  ) {
    return responseFuture
      .thenApply(result ->
        result.mapOk(response ->
          response
            .getResponseMetadata()
            .flatMap(ResponseMetadata::getNextCursor)
            .map(Strings::emptyToNull)
        )
      )
      .thenApply(result -> result.match(err -> Optional.empty(), ok -> ok));
  }

  @Override
  public CompletableFuture<Result<UsersInfoResponse, SlackError>> lookupUserByEmail(
    UserEmailParams params
  ) {
    return postSlackCommand(
      SlackMethods.users_lookupByEmail,
      params,
      UsersInfoResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<UsersInfoResponse, SlackError>> findUser(
    UsersInfoParams params
  ) {
    return postSlackCommand(SlackMethods.users_info, params, UsersInfoResponse.class);
  }

  @Override
  public CompletableFuture<Result<UsersProfileResponse, SlackError>> getUserProfile(
    UsersInfoParams params
  ) {
    return postSlackCommand(
      SlackMethods.users_profile_get,
      params,
      UsersProfileResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<UsersProfileResponse, SlackError>> setUserProfile(
    SetUserProfileParams params
  ) {
    return postSlackCommand(
      SlackMethods.users_profile_set,
      params,
      UsersProfileResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<DndInfoResponse, SlackError>> getDndInfo(
    DndInfoParams params
  ) {
    return postSlackCommand(SlackMethods.dnd_info, params, DndInfoResponse.class);
  }

  @Override
  public CompletableFuture<Result<SimpleSlackResponse, SlackError>> endDnd() {
    return postSlackCommand(
      SlackMethods.dnd_endDnd,
      Collections.emptyMap(),
      SimpleSlackResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<DndSnoozeResponse, SlackError>> setDndSnooze(
    DndSetSnoozeParams params
  ) {
    return postSlackCommand(SlackMethods.dnd_setSnooze, params, DndSnoozeResponse.class);
  }

  @Override
  public CompletableFuture<Result<DndInfoResponse, SlackError>> endDndSnooze() {
    return postSlackCommand(
      SlackMethods.dnd_endSnooze,
      Collections.emptyMap(),
      DndInfoResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackChannel>, SlackError>>> listChannels(
    ChannelsListParams filter
  ) {
    return new AbstractPagedIterable<Result<List<SlackChannel>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackChannel>, SlackError>, String> getPage(
        String offset
      ) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching channel page from {}", offset);
        }

        ChannelsListParams.Builder requestBuilder = ChannelsListParams
          .builder()
          .from(filter)
          .setLimit(config.getChannelsListBatchSize().get());
        Optional.ofNullable(offset).ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<ChannelsListResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.channels_list,
            requestBuilder.build(),
            ChannelsListResponse.class
          );

        CompletableFuture<Result<List<SlackChannel>, SlackError>> pageFuture =
          resultFuture.thenApply(result -> result.mapOk(ChannelsListResponse::getChannels)
          );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> channelHistory(
    ChannelsHistoryParams params
  ) {
    PagingDirection pagingDirection = params.getPagingDirection();
    return new AbstractPagedIterable<Result<List<LiteMessage>, SlackError>, Long>() {
      @Override
      protected Long getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<LiteMessage>, SlackError>, Long> getPage(
        Long offset
      ) throws Exception {
        ChannelsHistoryParams.Builder requestBuilder = ChannelsHistoryParams
          .builder()
          .from(params);
        if (params.getCount().isEmpty()) {
          requestBuilder.setCount(config.getChannelsHistoryMessageBatchSize().get());
        }

        Optional
          .ofNullable(offset)
          .ifPresent(presentOffset -> {
            if (pagingDirection == PagingDirection.FORWARD_IN_TIME) {
              requestBuilder.setOldestTimestamp(presentOffset.toString());
            } else {
              requestBuilder.setNewestTimestamp(presentOffset.toString());
            }
          });

        ChannelsHistoryParams currentRequest = requestBuilder.build();
        if (LOG.isTraceEnabled()) {
          LOG.trace(
            "Fetching channel history page for {} from [{}, {}]",
            currentRequest.getChannelId(),
            currentRequest.getOldestTimestamp(),
            currentRequest.getNewestTimestamp()
          );
        }

        CompletableFuture<Result<ChannelsHistoryResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.channels_history,
            currentRequest,
            ChannelsHistoryResponse.class
          );

        CompletableFuture<Result<List<LiteMessage>, SlackError>> pageFuture =
          resultFuture.thenApply(result ->
            result.mapOk(ChannelsHistoryResponse::getMessages)
          );

        CompletableFuture<Boolean> hasMoreFuture = resultFuture.thenApply(result ->
          result.match(err -> false, ChannelsHistoryResponse::hasMore)
        );
        CompletableFuture<Long> nextOffset = nextOffset(pagingDirection, pageFuture);

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextOffset);
      }
    };
  }

  private CompletableFuture<Long> nextOffset(
    PagingDirection pagingDirection,
    CompletableFuture<Result<List<LiteMessage>, SlackError>> pageFuture
  ) {
    return pageFuture.thenApply(page ->
      page.match(
        err -> null,
        messages -> {
          Set<String> timestamps = messages
            .stream()
            .map(LiteMessage::getTimestamp)
            .collect(Collectors.toSet());
          if (pagingDirection == PagingDirection.FORWARD_IN_TIME && !messages.isEmpty()) {
            return timestamps
              .stream()
              .max(Comparator.comparing(Function.identity()))
              .map(largestTs -> (long) Double.parseDouble(largestTs))
              .get();
          } else if (!messages.isEmpty()) {
            return timestamps
              .stream()
              .min(Comparator.comparing(Function.identity()))
              .map(smallestTs -> (long) Double.parseDouble(smallestTs))
              .get();
          }

          return null;
        }
      )
    );
  }

  @Override
  public CompletableFuture<Result<SlackChannel, SlackError>> getChannelByName(
    String channelName,
    ChannelsFilter channelsFilter
  ) {
    return findChannelByName(channelName, channelsFilter)
      .thenApply(channelMaybe -> {
        if (channelMaybe.isPresent()) {
          return Result.ok(channelMaybe.get());
        } else {
          return Result.err(
            SlackError
              .builder()
              .setError("No channel found with name: " + channelName)
              .setType(SlackErrorType.CHANNEL_NOT_FOUND)
              .build()
          );
        }
      });
  }

  private CompletableFuture<Optional<SlackChannel>> findChannelByName(
    String name,
    ChannelsFilter channelsFilter
  ) {
    return searchNextPage(
      name,
      listChannels(ChannelsListParams.builder().from(channelsFilter).build()).iterator()
    );
  }

  private CompletableFuture<Optional<SlackChannel>> searchNextPage(
    String channelName,
    Iterator<CompletableFuture<Result<List<SlackChannel>, SlackError>>> pageIterator
  ) {
    if (!pageIterator.hasNext()) {
      return CompletableFuture.completedFuture(Optional.empty());
    }

    CompletableFuture<Result<List<SlackChannel>, SlackError>> nextPage =
      pageIterator.next();
    return nextPage
      .thenApply(Result::unwrapOrElseThrow)
      .thenComposeAsync(
        channels -> {
          Optional<SlackChannel> matchInPage = channels
            .stream()
            .filter(channel -> channel.getName().equals(channelName))
            .findFirst();
          if (matchInPage.isPresent()) {
            return CompletableFuture.completedFuture(matchInPage);
          }

          return searchNextPage(channelName, pageIterator);
        },
        recursingExecutor
      );
  }

  @Override
  public CompletableFuture<Result<ChannelsInfoResponse, SlackError>> getChannelInfo(
    ChannelsInfoParams params
  ) {
    return postSlackCommand(
      SlackMethods.channels_info,
      params,
      ChannelsInfoResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChannelsKickResponse, SlackError>> kickUserFromChannel(
    ChannelsKickParams params
  ) {
    return postSlackCommand(
      SlackMethods.channels_kick,
      params,
      ChannelsKickResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ImOpenResponse, SlackError>> openIm(
    ImOpenParams params
  ) {
    return postSlackCommand(SlackMethods.im_open, params, ImOpenResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatPostMessageResponse, SlackError>> postMessage(
    ChatPostMessageParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_postMessage,
      params,
      ChatPostMessageResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatPostEphemeralMessageResponse, SlackError>> postEphemeralMessage(
    ChatPostEphemeralMessageParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_postEphemeral,
      params,
      ChatPostEphemeralMessageResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatUpdateMessageResponse, SlackError>> updateMessage(
    ChatUpdateMessageParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_update,
      params,
      ChatUpdateMessageResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatScheduleMessageResponse, SlackError>> scheduleMessage(
    ChatScheduleMessageParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_scheduleMessage,
      params,
      ChatScheduleMessageResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatScheduledMessagesListResponse, SlackError>> scheduledMessageList(
    ChatScheduledMessagesListParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_scheduledMessages_list,
      params,
      ChatScheduledMessagesListResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatDeleteScheduledMessageResponse, SlackError>> deleteScheduledMessage(
    ChatDeleteScheduledMessageParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_deleteScheduledMessage,
      params,
      ChatDeleteScheduledMessageResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatGetPermalinkResponse, SlackError>> getPermalink(
    ChatGetPermalinkParams params
  ) {
    return postSlackCommand(
      SlackMethods.chat_getPermalink,
      params,
      ChatGetPermalinkResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ChatDeleteResponse, SlackError>> deleteMessage(
    ChatDeleteParams params
  ) {
    return postSlackCommand(SlackMethods.chat_delete, params, ChatDeleteResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatUnfurlResponse, SlackError>> unfurlLinks(
    ChatUnfurlParams params
  ) {
    return postSlackCommand(SlackMethods.chat_unfurl, params, ChatUnfurlResponse.class);
  }

  @Override
  public CompletableFuture<Result<EntityPresentDetailsResponse, SlackError>> entityPresentDetails(
    WorkObjectFlexpaneParams params
  ) {
    return postSlackCommand(
      SlackMethods.entity_presentDetails,
      params,
      EntityPresentDetailsResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> listConversations(
    ConversationsListParams params
  ) {
    return new AbstractPagedIterable<Result<List<Conversation>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<Conversation>, SlackError>, String> getPage(
        String offset
      ) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack conversation page from {}", offset);
        }

        ConversationsListParams.Builder requestBuilder = ConversationsListParams
          .builder()
          .from(params)
          .setLimit(config.getChannelsListBatchSize().get());
        Optional.ofNullable(offset).ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<ConversationListResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.conversations_list,
            requestBuilder.build(),
            ConversationListResponse.class
          );

        CompletableFuture<Result<List<Conversation>, SlackError>> pageFuture =
          resultFuture.thenApply(result ->
            result.mapOk(ConversationListResponse::getConversations)
          );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public CompletableFuture<Result<ConversationListResponse, SlackError>> listConversationsPaginated(
    ConversationsListParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_list,
      params,
      ConversationListResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> usersConversations(
    ConversationsUserParams params
  ) {
    return new AbstractPagedIterable<Result<List<Conversation>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<Conversation>, SlackError>, String> getPage(
        String offset
      ) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack user conversation page from {}", offset);
        }

        ConversationsUserParams.Builder requestBuilder = ConversationsUserParams
          .builder()
          .from(params)
          .setLimit(config.getChannelsListBatchSize().get());
        Optional.ofNullable(offset).ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<ConversationListResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.users_conversations,
            requestBuilder.build(),
            ConversationListResponse.class
          );

        CompletableFuture<Result<List<Conversation>, SlackError>> pageFuture =
          resultFuture.thenApply(result ->
            result.mapOk(ConversationListResponse::getConversations)
          );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public CompletableFuture<Result<ConversationListResponse, SlackError>> usersConversationsPaginated(
    ConversationsUserParams params
  ) {
    return postSlackCommand(
      SlackMethods.users_conversations,
      params,
      ConversationListResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationsCreateResponse, SlackError>> createConversation(
    ConversationCreateParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_create,
      params,
      ConversationsCreateResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationsInviteResponse, SlackError>> inviteToConversation(
    ConversationInviteParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_invite,
      params,
      ConversationsInviteResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<SharedChannelInviteResponse, SlackError>> inviteToSharedConversation(
    ConversationInviteSharedParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_inviteShared,
      params,
      SharedChannelInviteResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationsUnarchiveResponse, SlackError>> unarchiveConversation(
    ConversationUnarchiveParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_unarchive,
      params,
      ConversationsUnarchiveResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> getConversationHistory(
    ConversationsHistoryParams params
  ) {
    PagingDirection pagingDirection = params.getPagingDirection();
    return new AbstractPagedIterable<Result<List<LiteMessage>, SlackError>, Long>() {
      @Override
      protected Long getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<LiteMessage>, SlackError>, Long> getPage(
        Long offset
      ) throws Exception {
        ConversationsHistoryParams.Builder requestBuilder = ConversationsHistoryParams
          .builder()
          .from(params);
        if (params.getLimit().isEmpty()) {
          requestBuilder.setLimit(config.getConversationsHistoryMessageBatchSize().get());
        }

        Optional
          .ofNullable(offset)
          .ifPresent(presentOffset -> {
            if (pagingDirection == PagingDirection.FORWARD_IN_TIME) {
              requestBuilder.setOldestTimestamp(presentOffset.toString());
            } else {
              requestBuilder.setNewestTimestamp(presentOffset.toString());
            }
          });

        ConversationsHistoryParams currentRequest = requestBuilder.build();
        if (LOG.isTraceEnabled()) {
          LOG.trace(
            "Fetching conversation history page for {} from [{}, {}]",
            currentRequest.getChannelId(),
            currentRequest.getOldestTimestamp(),
            currentRequest.getNewestTimestamp()
          );
        }

        CompletableFuture<Result<ConversationsHistoryResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.conversations_history,
            currentRequest,
            ConversationsHistoryResponse.class
          );

        CompletableFuture<Result<List<LiteMessage>, SlackError>> pageFuture =
          resultFuture.thenApply(result ->
            result.mapOk(ConversationsHistoryResponse::getMessages)
          );

        CompletableFuture<Boolean> hasMoreFuture = resultFuture.thenApply(result ->
          result.match(err -> false, ConversationsHistoryResponse::hasMore)
        );
        CompletableFuture<Long> nextOffset = nextOffset(pagingDirection, pageFuture);

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextOffset);
      }
    };
  }

  @Override
  public CompletableFuture<Result<ConversationsArchiveResponse, SlackError>> archiveConversation(
    ConversationArchiveParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_archive,
      params,
      ConversationsArchiveResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ConversationsInfoResponse, SlackError>> getConversationInfo(
    ConversationsInfoParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_info,
      params,
      ConversationsInfoResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<Conversation, SlackError>> getConversationByName(
    String conversationName,
    ConversationsFilter conversationsFilter
  ) {
    return findConversationByName(conversationName, conversationsFilter)
      .thenApply(conversation -> {
        if (conversation.isPresent()) {
          return Result.ok(conversation.get());
        } else {
          return Result.err(
            SlackError
              .builder()
              .setType(SlackErrorType.CHANNEL_NOT_FOUND)
              .setError("No conversation found with name: " + conversationName)
              .build()
          );
        }
      });
  }

  @Override
  public CompletableFuture<Result<ConversationsOpenResponse, SlackError>> openConversation(
    ConversationOpenParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_open,
      params,
      ConversationsOpenResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<String>, SlackError>>> getConversationMembers(
    ConversationMemberParams params
  ) {
    return new AbstractPagedIterable<Result<List<String>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<String>, SlackError>, String> getPage(
        String offset
      ) {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack conversation members page from {}", offset);
        }
        ConversationMemberParams.Builder requestBuilder = ConversationMemberParams
          .builder()
          .from(params);
        if (params.getLimit().isEmpty()) {
          requestBuilder.setLimit(config.getConversationMembersBatchSize().get());
        }
        Optional.ofNullable(offset).ifPresent(requestBuilder::setCursor);

        ConversationMemberParams params = requestBuilder.build();
        CompletableFuture<Result<ConversationMemberResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.conversations_members,
            params,
            ConversationMemberResponse.class
          );

        CompletableFuture<Result<List<String>, SlackError>> pageFuture =
          resultFuture.thenApply(result ->
            result.mapOk(ConversationMemberResponse::getMemberIds)
          );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public CompletableFuture<Result<ConversationMemberResponse, SlackError>> getConversationMembersPaginated(
    ConversationMemberParams params
  ) {
    return postSlackCommand(
      SlackMethods.conversations_members,
      params,
      ConversationMemberResponse.class
    );
  }

  private CompletableFuture<Optional<Conversation>> findConversationByName(
    String conversationName,
    ConversationsFilter conversationsFilter
  ) {
    return searchNextConversationPage(
      conversationName,
      listConversations(
        ConversationsListParams.builder().from(conversationsFilter).build()
      )
        .iterator()
    );
  }

  private CompletableFuture<Optional<Conversation>> searchNextConversationPage(
    String conversationName,
    Iterator<CompletableFuture<Result<List<Conversation>, SlackError>>> pageIterator
  ) {
    if (!pageIterator.hasNext()) {
      return CompletableFuture.completedFuture(Optional.empty());
    }

    CompletableFuture<Result<List<Conversation>, SlackError>> nextPage =
      pageIterator.next();
    return nextPage
      .thenApply(Result::unwrapOrElseThrow)
      .thenComposeAsync(
        conversations -> {
          Optional<Conversation> matchInPage = conversations
            .stream()
            .filter(conversation ->
              conversation.getName().isPresent() &&
              conversation.getName().get().equals(conversationName)
            )
            .findFirst();
          if (matchInPage.isPresent()) {
            return CompletableFuture.completedFuture(matchInPage);
          }

          return searchNextConversationPage(conversationName, pageIterator);
        },
        recursingExecutor
      );
  }

  @Override
  public CompletableFuture<Result<UsergroupCreateResponse, SlackError>> createUsergroup(
    UsergroupCreateParams params
  ) {
    return postSlackCommand(
      SlackMethods.usergroups_create,
      params,
      UsergroupCreateResponse.class
    );
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackUsergroup>, SlackError>>> listUsergroups(
    UsergroupListParams params
  ) {
    return new AbstractPagedIterable<Result<List<SlackUsergroup>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackUsergroup>, SlackError>, String> getPage(
        String offset
      ) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack usergroup page from {}", offset);
        }

        CompletableFuture<Result<UsergroupListResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.usergroups_list,
            UsergroupListParams.builder().from(params).build(),
            UsergroupListResponse.class
          );

        CompletableFuture<Result<List<SlackUsergroup>, SlackError>> pageFuture =
          resultFuture.thenApply(result ->
            result.mapOk(UsergroupListResponse::getUsergroups)
          );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public CompletableFuture<Result<UsergroupUpdateResponse, SlackError>> updateUsergroup(
    UsergroupUpdateParams params
  ) {
    return postSlackCommand(
      SlackMethods.usergroups_update,
      params,
      UsergroupUpdateResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<UsergroupEnableResponse, SlackError>> enableUsergroup(
    UsergroupEnableParams params
  ) {
    return postSlackCommand(
      SlackMethods.usergroups_enable,
      params,
      UsergroupEnableResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<UsergroupDisableResponse, SlackError>> disableUsergroup(
    UsergroupDisableParams params
  ) {
    return postSlackCommand(
      SlackMethods.usergroups_disable,
      params,
      UsergroupDisableResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<UsergroupUsersUpdateResponse, SlackError>> updateUsergroupUsers(
    UsergroupUsersUpdateParams params
  ) {
    return postSlackCommand(
      SlackMethods.usergroups_users_update,
      params,
      UsergroupUsersUpdateResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<DialogOpenResponse, SlackError>> openDialog(
    DialogOpenParams params
  ) {
    return postSlackCommand(SlackMethods.dialog_open, params, DialogOpenResponse.class);
  }

  @Override
  public CompletableFuture<Result<AddReactionResponse, SlackError>> addReaction(
    ReactionsAddParams params
  ) {
    return postSlackCommand(
      SlackMethods.reactions_add,
      params,
      AddReactionResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<FilesUploadResponse, SlackError>> uploadFile(
    FilesUploadParams params
  ) {
    if (params.getContent().isPresent()) {
      return postSlackCommand(
        SlackMethods.files_upload,
        params,
        FilesUploadResponse.class
      );
    }

    ImmutableMap.Builder<String, String> stringParts = ImmutableMap.builder();
    ImmutableMap.Builder<String, File> fileParts = ImmutableMap.builder();
    ImmutableMap.Builder<String, byte[]> byteArrayParts = ImmutableMap.builder();
    stringParts.put("token", config.getTokenSupplier().get());
    if (!params.getChannels().isEmpty()) {
      stringParts.put(
        "channels",
        params.getChannels().stream().collect(Collectors.joining(","))
      );
    }
    params.getFilename().ifPresent(filename -> stringParts.put("filename", filename));
    params.getFiletype().ifPresent(type -> stringParts.put("filetype", type.getType()));
    params
      .getInitialComment()
      .ifPresent(comment -> stringParts.put("initial_comment", comment));
    params.getThreadTs().ifPresent(thread -> stringParts.put("thread_ts", thread));
    params.getTitle().ifPresent(title -> stringParts.put("title", title));
    params.getFile().ifPresent(file -> fileParts.put("file", file));

    return postSlackCommandMultipartEncoded(
      SlackMethods.files_upload,
      stringParts.build(),
      fileParts.build(),
      byteArrayParts.build(),
      FilesUploadResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<GetUploadUrlExternalResponse, SlackError>> getUploadURLExternal(
    GetUploadUrlExternalParams params
  ) {
    return postSlackCommand(
      SlackMethods.files_getUploadURLExternal,
      params,
      GetUploadUrlExternalResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<CompleteUploadExternalResponse, SlackError>> completeUploadExternal(
    CompleteUploadExternalParams params
  ) {
    return postSlackCommand(
      SlackMethods.files_completeUploadExternal,
      params,
      CompleteUploadExternalResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<FilesSharedPublicUrlResponse, SlackError>> shareFilePublically(
    FilesSharedPublicUrlParams params
  ) {
    return postSlackCommand(
      SlackMethods.files_sharedPublicURL,
      params,
      FilesSharedPublicUrlResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<FilesInfoResponse, SlackError>> getFileInfo(
    FilesInfoParams request
  ) {
    return postSlackCommand(SlackMethods.files_info, request, FilesInfoResponse.class);
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackGroup>, SlackError>>> listGroups(
    GroupsListParams filter
  ) {
    return new AbstractPagedIterable<Result<List<SlackGroup>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackGroup>, SlackError>, String> getPage(
        String offset
      ) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack group page from {}", offset);
        }

        CompletableFuture<Result<GroupsListResponse, SlackError>> resultFuture =
          postSlackCommand(
            SlackMethods.groups_list,
            GroupsListParams.builder().from(filter).build(),
            GroupsListResponse.class
          );

        CompletableFuture<Result<List<SlackGroup>, SlackError>> pageFuture =
          resultFuture.thenApply(result -> result.mapOk(GroupsListResponse::getGroups));

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(
          resultFuture
        );
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(
          Optional::isPresent
        );
        CompletableFuture<String> nextCursorFuture =
          nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextCursorFuture);
      }
    };
  }

  @Override
  public CompletableFuture<Result<GroupsKickResponse, SlackError>> kickUserFromGroup(
    GroupsKickParams params
  ) {
    return postSlackCommand(SlackMethods.groups_kick, params, GroupsKickResponse.class);
  }

  @Override
  public CompletableFuture<Result<TeamInfoResponse, SlackError>> getTeamInfo() {
    return postSlackCommand(
      SlackMethods.team_info,
      Collections.emptyMap(),
      TeamInfoResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<EmojiListResponse, SlackError>> listEmoji() {
    return postSlackCommand(
      SlackMethods.emoji_list,
      Collections.emptyMap(),
      EmojiListResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ModalViewCommandResponse, SlackError>> openView(
    OpenViewParams params
  ) {
    return postSlackCommand(
      SlackMethods.views_open,
      params,
      ModalViewCommandResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ModalViewCommandResponse, SlackError>> updateView(
    UpdateViewParams params
  ) {
    return postSlackCommand(
      SlackMethods.views_update,
      params,
      ModalViewCommandResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<ModalViewCommandResponse, SlackError>> pushView(
    OpenViewParams params
  ) {
    return postSlackCommand(
      SlackMethods.views_push,
      params,
      ModalViewCommandResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<HomeTabViewCommandResponse, SlackError>> publishView(
    PublishViewParams params
  ) {
    return postSlackCommand(
      SlackMethods.views_publish,
      params,
      HomeTabViewCommandResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<BookmarkAddResponse, SlackError>> addBookmark(
    BookmarksAddParams params
  ) {
    return postSlackCommand(
      SlackMethods.bookmarks_add,
      params,
      BookmarkAddResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<BookmarkEditResponse, SlackError>> editBookmark(
    BookmarksEditParams params
  ) {
    return postSlackCommand(
      SlackMethods.bookmarks_edit,
      params,
      BookmarkEditResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<BookmarkRemoveResponse, SlackError>> removeBookmark(
    BookmarksRemoveParams params
  ) {
    return postSlackCommand(
      SlackMethods.bookmarks_remove,
      params,
      BookmarkRemoveResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<BookmarkListResponse, SlackError>> listBookmarks(
    BookmarksListParams params
  ) {
    return postSlackCommand(
      SlackMethods.bookmarks_list,
      params,
      BookmarkListResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<MigrationExchangeResponse, SlackError>> migrationExchange(
    MigrationExchangeParams params
  ) {
    return postSlackCommand(
      SlackMethods.migration_exchange,
      params,
      MigrationExchangeResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<CallsAddResponse, SlackError>> addCall(
    CallsAddParams params
  ) {
    return postSlackCommand(SlackMethods.calls_add, params, CallsAddResponse.class);
  }

  @Override
  public CompletableFuture<Result<CallsEndResponse, SlackError>> endCall(
    CallsEndParams params
  ) {
    return postSlackCommand(SlackMethods.calls_end, params, CallsEndResponse.class);
  }

  @Override
  public CompletableFuture<Result<CallsUpdateResponse, SlackError>> updateCall(
    CallsUpdateParams params
  ) {
    return postSlackCommand(SlackMethods.calls_update, params, CallsUpdateResponse.class);
  }

  @Override
  public CompletableFuture<Result<CallsInfoResponse, SlackError>> getCallInfo(
    CallsInfoParams params
  ) {
    return postSlackCommand(SlackMethods.calls_info, params, CallsInfoResponse.class);
  }

  @Override
  public CompletableFuture<Result<CallsParticipantsAddResponse, SlackError>> addCallParticipants(
    CallsParticipantsAddParams params
  ) {
    return postSlackCommand(
      SlackMethods.calls_participants_add,
      params,
      CallsParticipantsAddResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<CallsParticipantsRemoveResponse, SlackError>> removeCallParticipants(
    CallsParticipantsRemoveParams params
  ) {
    return postSlackCommand(
      SlackMethods.calls_participants_remove,
      params,
      CallsParticipantsRemoveResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<SimpleSlackResponse, SlackError>> assistantSetThreadStatus(
    SetThreadStatusParams params
  ) {
    return postSlackCommand(
      SlackMethods.assistant_threads_setStatus,
      params,
      SimpleSlackResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<SimpleSlackResponse, SlackError>> assistantSetSuggestedPrompts(
    SetSuggestedPromptsParams params
  ) {
    return postSlackCommand(
      SlackMethods.assistant_threads_setSuggestedPrompts,
      params,
      SimpleSlackResponse.class
    );
  }

  @Override
  public CompletableFuture<Result<SimpleSlackResponse, SlackError>> assistantSetTitle(
    SetTitleParams params
  ) {
    return postSlackCommand(
      SlackMethods.assistant_threads_setTitle,
      params,
      SimpleSlackResponse.class
    );
  }

  @Override
  public <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommand(
    SlackMethod method,
    Object params,
    Class<T> returnClazz
  ) {
    if (!methodAcceptor.test(method, params)) {
      LOG.info(
        "Acceptor failed: {}",
        methodAcceptor.getFailureExplanation(method, params)
      );
      return CompletableFuture.completedFuture(
        Result.err(
          SlackError
            .builder()
            .setType(SlackErrorType.PARAMS_FAILED_API_FILTER)
            .setError(SlackErrorType.PARAMS_FAILED_API_FILTER.getCode())
            .build()
        )
      );
    }

    if (method.jsonWhitelistStatus() == JsonStatus.ACCEPTS_JSON) {
      return postSlackCommandJsonEncoded(method, params, returnClazz);
    }

    return postSlackCommandUrlEncoded(
      method,
      HttpRequestHelper.objectToQueryParams(params),
      returnClazz
    );
  }

  private <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommandJsonEncoded(
    SlackMethod method,
    Object params,
    Class<T> responseType
  ) {
    HttpRequest request = buildBaseSlackPost(method)
      .setContentType(ContentType.JSON)
      .setBody(params)
      .addHeader("Authorization", "Bearer " + config.getTokenSupplier().get())
      .build();
    return executeLoggedAs(method, request, responseType);
  }

  private HttpRequest.Builder buildBaseSlackPost(SlackMethod method) {
    return HttpRequest
      .newBuilder()
      .setMethod(Method.POST)
      .setUrl(config.getSlackApiBasePath().get() + "/" + method.getMethod());
  }

  @VisibleForTesting
  <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> executeLoggedAs(
    SlackMethod method,
    HttpRequest request,
    Class<T> responseType
  ) {
    long requestId = REQUEST_COUNTER.getAndIncrement();
    requestDebugger.debug(requestId, method, request);

    Stopwatch timer = Stopwatch.createStarted();
    double acquireSeconds = acquirePermit(method);

    if (acquireSeconds == RATE_LIMIT_SENTINEL_VALUE) {
      responseDebugger.debugProactiveRateLimit(requestId, method, request);
      return CompletableFuture.completedFuture(
        Result.err(SlackError.of(SlackErrorType.RATE_LIMITED.key()))
      );
    }

    return executeLogged(requestId, method, request, timer)
      .thenApply(response -> {
        try {
          return parseSlackResponse(response, responseType, requestId, method, request);
        } catch (JsonProcessingException e) {
          responseDebugger.debugProcessingFailure(
            requestId,
            method,
            request,
            response,
            e
          );
          return Result.err(
            SlackError
              .builder()
              .setError(SlackErrorType.JSON_PARSING_FAILED.getCode())
              .setType(SlackErrorType.JSON_PARSING_FAILED)
              .build()
          );
        } catch (RuntimeException ex) {
          responseDebugger.debugProcessingFailure(
            requestId,
            method,
            request,
            response,
            ex
          );
          throw ex;
        }
      });
  }

  private <T extends SlackResponse> Result<T, SlackError> parseSlackResponse(
    HttpResponse response,
    Class<T> responseType,
    long requestId,
    SlackMethod method,
    HttpRequest request
  ) throws JsonProcessingException {
    JsonNode responseJson = response.getAsJsonNode();
    boolean isOk = responseJson.get("ok").asBoolean();
    if (isOk) {
      return Result.ok(
        ObjectMapperUtils.mapper().treeToValue(responseJson, responseType)
      );
    }

    SlackErrorResponse errorResponse = ObjectMapperUtils
      .mapper()
      .treeToValue(response.getAsJsonNode(), SlackErrorResponse.class);
    responseDebugger.debugSlackApiError(requestId, method, request, response);
    return Result.err(
      errorResponse.getError().orElseGet(() -> errorResponse.getErrors().get(0))
    );
  }

  private <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommandUrlEncoded(
    SlackMethod method,
    Multimap<String, String> params,
    Class<T> responseType
  ) {
    HttpRequest.Builder requestBuilder = buildBaseSlackPost(method)
      .setContentType(ContentType.FORM)
      .addHeader("Authorization", "Bearer " + config.getTokenSupplier().get());
    params
      .entries()
      .forEach(param -> requestBuilder.setFormParam(param.getKey()).to(param.getValue()));
    return executeLoggedAs(method, requestBuilder.build(), responseType);
  }

  private CompletableFuture<HttpResponse> executeLogged(
    long requestId,
    SlackMethod method,
    HttpRequest request,
    Stopwatch timer
  ) {
    CompletableFuture<HttpResponse> responseFuture =
      nioHttpClient.executeCompletableFuture(request);

    responseFuture.whenComplete((httpResponse, throwable) -> {
      if (throwable != null) {
        responseDebugger.debugTransportException(requestId, method, request, throwable);
      } else {
        responseDebugger.debug(requestId, method, timer, request, httpResponse);
      }
    });

    return responseFuture;
  }

  private <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommandMultipartEncoded(
    SlackMethod method,
    Map<String, String> stringParts,
    Map<String, File> fileParts,
    Map<String, byte[]> byteArrayParts,
    Class<T> responseType
  ) {
    Builder requestBuilder = MultipartHttpRequest
      .newBuilder()
      .setMethod(Method.POST)
      .setUrl(config.getSlackApiBasePath().get() + "/" + method.getMethod());

    stringParts.forEach(requestBuilder::addStringPart);
    fileParts.forEach(requestBuilder::addFilePart);
    byteArrayParts.forEach(requestBuilder::addByteArrayPart);

    return executeLoggedAs(method, requestBuilder.build(), responseType);
  }

  private double acquirePermit(SlackMethod method) {
    double acquireSeconds = getSlackRateLimiter()
      .acquire(config.getTokenSupplier().get(), method);
    if (acquireSeconds > RATE_LIMIT_LOG_WARNING_THRESHOLD_SECONDS) {
      LOG.warn(
        "Throttling {}, waited {} seconds to acquire permit to run",
        method,
        acquireSeconds
      );
    }

    return acquireSeconds;
  }

  private SlackRateLimiter getSlackRateLimiter() {
    return config.getSlackRateLimiter().orElse(defaultRateLimiter);
  }

  @Override
  public void close() throws IOException {
    recursingExecutor.close();
    nioHttpClient.close();
  }
}
