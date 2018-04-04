package com.hubspot.slack.client;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Multimap;
import com.google.common.util.concurrent.RateLimiter;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import com.hubspot.algebra.Result;
import com.hubspot.horizon.HttpConfig;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpRequest.ContentType;
import com.hubspot.horizon.HttpRequest.Method;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.horizon.ning.NingAsyncHttpClient;
import com.hubspot.slack.client.http.NioHttpClient;
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
import com.hubspot.slack.client.methods.params.channels.AbstractChannelsInfoParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsFilter;
import com.hubspot.slack.client.methods.params.channels.ChannelsHistoryParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsListParams;
import com.hubspot.slack.client.methods.params.channels.FindRepliesParams;
import com.hubspot.slack.client.methods.params.channels.PagingDirection;
import com.hubspot.slack.client.methods.params.chat.ChatDeleteParams;
import com.hubspot.slack.client.methods.params.chat.ChatGetPermalinkParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostEphemeralMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatUpdateMessageParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationArchiveParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationCreateParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationInviteParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationUnarchiveParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsFilter;
import com.hubspot.slack.client.methods.params.conversations.ConversationsHistoryParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsInfoParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsListParams;
import com.hubspot.slack.client.methods.params.dialog.DialogOpenParams;
import com.hubspot.slack.client.methods.params.group.GroupsListParams;
import com.hubspot.slack.client.methods.params.im.ImOpenParams;
import com.hubspot.slack.client.methods.params.reactions.ReactionsAddParams;
import com.hubspot.slack.client.methods.params.search.SearchMessagesParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupCreateParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupDisableParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupEnableParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupListParams;
import com.hubspot.slack.client.methods.params.usergroups.UsergroupUpdateParams;
import com.hubspot.slack.client.methods.params.usergroups.users.UsergroupUsersUpdateParams;
import com.hubspot.slack.client.methods.params.users.UserEmailParams;
import com.hubspot.slack.client.methods.params.users.UsersInfoParams;
import com.hubspot.slack.client.methods.params.users.UsersListParams;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.SlackChannel;
import com.hubspot.slack.client.models.conversations.Conversation;
import com.hubspot.slack.client.models.group.SlackGroup;
import com.hubspot.slack.client.models.response.FindRepliesResponse;
import com.hubspot.slack.client.models.response.ResponseMetadata;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.SlackErrorType;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.response.auth.AuthTestResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsHistoryResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsInfoResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsListResponse;
import com.hubspot.slack.client.models.response.chat.ChatDeleteResponse;
import com.hubspot.slack.client.models.response.chat.ChatGetPermalinkResponse;
import com.hubspot.slack.client.models.response.chat.ChatPostEphemeralMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatUpdateMessageResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationListResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsArchiveResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsCreateResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsHistoryResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsInfoResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsInviteResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsUnarchiveResponse;
import com.hubspot.slack.client.models.response.dialog.DialogOpenResponse;
import com.hubspot.slack.client.models.response.group.GroupsListResponse;
import com.hubspot.slack.client.models.response.im.ImOpenResponse;
import com.hubspot.slack.client.models.response.reactions.AddReactionResponse;
import com.hubspot.slack.client.models.response.search.SearchMessageResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupCreateResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupDisableResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupEnableResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupListResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupUpdateResponse;
import com.hubspot.slack.client.models.response.usergroups.users.UsergroupUsersUpdateResponse;
import com.hubspot.slack.client.models.response.users.UsersInfoResponse;
import com.hubspot.slack.client.models.response.users.UsersListResponse;
import com.hubspot.slack.client.models.usergroups.SlackUsergroup;
import com.hubspot.slack.client.models.users.SlackUser;
import com.hubspot.slack.client.paging.AbstractPagedIterable;
import com.hubspot.slack.client.paging.LazyLoadingPage;

public class SlackWebClient implements SlackClient {
  private static final Logger LOG = LoggerFactory.getLogger(SlackWebClient.class);
  private static final HttpConfig DEFAULT_CONFIG = HttpConfig.newBuilder()
      .setObjectMapper(ObjectMapperUtils.mapper())
      .build();
  private static final AtomicLong REQUEST_COUNTER = new AtomicLong(0);
  private static final ConcurrentMap<SlackMethod, RateLimiter> RATE_LIMITERS = new ConcurrentHashMap<>();

  public interface Factory {
    SlackWebClient build(
        @Assisted SlackClientRuntimeConfig config
    );
  }

  private final NioHttpClient nioHttpClient;
  private final SlackClientRuntimeConfig config;

  private final SlackMethodAcceptor methodAcceptor;
  private final RequestDebugger requestDebugger;
  private final ResponseDebugger responseDebugger;

  @AssistedInject
  public SlackWebClient(
      DefaultHttpRequestDebugger defaultHttpRequestDebugger,
      DefaultHttpResponseDebugger defaultHttpResponseDebugger,
      NioHttpClient.Factory nioHttpClientFactory,
      @Assisted SlackClientRuntimeConfig config
  ) {
    this.nioHttpClient = nioHttpClientFactory.wrap(
        new NingAsyncHttpClient(
            config.getHttpConfig().orElse(DEFAULT_CONFIG)
        )
    );
    this.config = config;

    this.methodAcceptor = config.getMethodFilter()
        .orElse(new SlackMethodAcceptor() {
          @Override
          public String getFailureExplanation(SlackMethod method, Object params) {
            throw new IllegalStateException("We can't fail with the pasthrough acceptor");
          }

          @Override
          public boolean test(SlackMethod slackMethod, Object o) {
            return true;
          }
        });
    this.requestDebugger = config.getRequestDebugger().orElse(defaultHttpRequestDebugger);
    this.responseDebugger = config.getResponseDebugger().orElse(defaultHttpResponseDebugger);
  }

  @Override
  public CompletableFuture<Result<AuthTestResponse, SlackError>> testAuth() {
    return postSlackCommand(SlackMethods.auth_test, Collections.emptyMap(), AuthTestResponse.class);
  }

  @Override
  public CompletableFuture<Result<SearchMessageResponse, SlackError>> searchMessages(SearchMessagesParams params) {
    return postSlackCommand(SlackMethods.search_messages, params, SearchMessageResponse.class);
  }

  @Override
  public CompletableFuture<Result<FindRepliesResponse, SlackError>> findReplies(FindRepliesParams params) {
    switch (params.getChannelType()) {
      case GROUP:
        return postSlackCommand(SlackMethods.groups_replies, params, FindRepliesResponse.class);
      case CHANNEL:
        return postSlackCommand(SlackMethods.channels_replies, params, FindRepliesResponse.class);
      default:
        throw new IllegalArgumentException(params.getChannelType() + " is not a supported channel type for reply fetching");
    }
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackUser>, SlackError>>> listUsers() {
    return new AbstractPagedIterable<Result<List<SlackUser>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackUser>, SlackError>, String> getPage(String offset) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching user page from {}", offset);
        }

        UsersListParams.Builder requestBuilder = UsersListParams.builder()
            .setLimit(config.getUsersListBatchSize().get());
        Optional.ofNullable(offset)
            .ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<UsersListResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.users_list,
            requestBuilder.build(),
            UsersListResponse.class
        );

        CompletableFuture<Result<List<SlackUser>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(UsersListResponse::getMembers)
        );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(resultFuture);
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(Optional::isPresent);
        CompletableFuture<String> nextCursorFuture = nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(
            pageFuture,
            hasMoreFuture,
            nextCursorFuture
        );
      }
    };
  }

  private <T extends SlackResponse> CompletableFuture<Optional<String>> extractNextCursor(CompletableFuture<Result<T, SlackError>> responseFuture) {
    return responseFuture.thenApply(result ->
        result.mapOk(response ->
            response.getResponseMetadata()
                .flatMap(ResponseMetadata::getNextCursor)
                .map(Strings::emptyToNull)
        )
    ).thenApply(result ->
        result.match(
            err -> Optional.empty(), ok -> ok
        )
    );
  }

  @Override
  public CompletableFuture<Result<UsersInfoResponse, SlackError>> lookupUserByEmail(UserEmailParams params) {
    return postSlackCommand(SlackMethods.users_lookupByEmail, params, UsersInfoResponse.class);
  }

  @Override
  public CompletableFuture<Result<UsersInfoResponse, SlackError>> findUser(UsersInfoParams params) {
    return postSlackCommand(SlackMethods.users_info, params, UsersInfoResponse.class);
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackChannel>, SlackError>>> listChannels(ChannelsListParams filter) {
    return new AbstractPagedIterable<Result<List<SlackChannel>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackChannel>, SlackError>, String> getPage(String offset) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching channel page from {}", offset);
        }

        ChannelsListParams.Builder requestBuilder = ChannelsListParams.builder()
            .from(filter)
            .setLimit(config.getChannelsListBatchSize().get());
        Optional.ofNullable(offset)
            .ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<ChannelsListResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.channels_list,
            requestBuilder.build(),
            ChannelsListResponse.class
        );

        CompletableFuture<Result<List<SlackChannel>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(ChannelsListResponse::getChannels)
        );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(resultFuture);
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(Optional::isPresent);
        CompletableFuture<String> nextCursorFuture = nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(
            pageFuture,
            hasMoreFuture,
            nextCursorFuture
        );
      }
    };
  }

  @Override
  public Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> channelHistory(ChannelsHistoryParams params) {
    PagingDirection pagingDirection = params.getPagingDirection();
    return new AbstractPagedIterable<Result<List<LiteMessage>, SlackError>, Long>() {
      @Override
      protected Long getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<LiteMessage>, SlackError>, Long> getPage(Long offset) throws Exception {
        ChannelsHistoryParams.Builder requestBuilder = ChannelsHistoryParams.builder()
            .from(params);
        if (!params.getCount().isPresent()) {
          requestBuilder.setCount(config.getChannelsHistoryMessageBatchSize().get());
        }

        Optional.ofNullable(offset)
            .ifPresent(presentOffset -> {
              if (pagingDirection == PagingDirection.FORWARD_IN_TIME) {
                requestBuilder.setOldestTimestamp(presentOffset.toString());
              } else {
                requestBuilder.setNewestTimestamp(presentOffset.toString());
              }
            });

        ChannelsHistoryParams currentRequest = requestBuilder.build();
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching channel history page for {} from [{}, {}]",
              currentRequest.getChannelId(), currentRequest.getOldestTimestamp(), currentRequest.getNewestTimestamp()
          );
        }

        CompletableFuture<Result<ChannelsHistoryResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.channels_history,
            currentRequest,
            ChannelsHistoryResponse.class
        );

        CompletableFuture<Result<List<LiteMessage>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(ChannelsHistoryResponse::getMessages)
        );

        CompletableFuture<Boolean> hasMoreFuture = resultFuture.thenApply(
            result -> result.match(
                err -> false,
                ok -> ok.hasMore()
            )
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
    return pageFuture.thenApply(page -> {
      return page.match(
          err -> null,
          messages -> {
            Set<String> timestamps = messages.stream()
                .map(LiteMessage::getTimestamp)
                .collect(Collectors.toSet());
            if (pagingDirection == PagingDirection.FORWARD_IN_TIME && !messages.isEmpty()) {
              return timestamps.stream()
                  .max(Comparator.comparing(Function.identity()))
                  .map(largestTs -> (long) Double.parseDouble(largestTs))
                  .get();
            } else if (!messages.isEmpty()) {
              return timestamps.stream()
                  .min(Comparator.comparing(Function.identity()))
                  .map(smallestTs -> (long) Double.parseDouble(smallestTs))
                  .get();
            }

            return null;
          }
      );
    });
  }

  @Override
  public CompletableFuture<Result<SlackChannel, SlackError>> getChannelByName(String channelName, ChannelsFilter channelsFilter) {
    return findChannelByName(channelName, channelsFilter).thenApply(channelMaybe -> {
      if (channelMaybe.isPresent()) {
        return Result.ok(channelMaybe.get());
      } else {
        return Result.err(
            SlackError.builder()
                .setError("No channel found with name: " + channelName)
                .setType(SlackErrorType.CHANNEL_NOT_FOUND)
                .build()
        );
      }
    });
  }

  private CompletableFuture<Optional<SlackChannel>> findChannelByName(String name, ChannelsFilter channelsFilter) {
    return searchNextPage(name,
        listChannels(
            ChannelsListParams.builder()
                .from(channelsFilter)
                .build()
        ).iterator()
    );
  }

  private CompletableFuture<Optional<SlackChannel>> searchNextPage(
      String channelName,
      Iterator<CompletableFuture<Result<List<SlackChannel>, SlackError>>> pageIterator
  ) {
    if (!pageIterator.hasNext()) {
      return CompletableFuture.completedFuture(Optional.empty());
    }

    CompletableFuture<Result<List<SlackChannel>, SlackError>> nextPage = pageIterator.next();
    return nextPage.thenApply(Result::unwrapOrElseThrow)
        .thenCompose(channels -> {
          Optional<SlackChannel> matchInPage = channels.stream()
              .filter(channel -> channel.getName().equals(channelName))
              .findFirst();
          if (matchInPage.isPresent()) {
            return CompletableFuture.completedFuture(matchInPage);
          }

          return searchNextPage(channelName, pageIterator);
        });
  }

  @Override
  public CompletableFuture<Result<ChannelsInfoResponse, SlackError>> getChannelInfo(AbstractChannelsInfoParams params) {
    return postSlackCommand(SlackMethods.channels_info, params, ChannelsInfoResponse.class);
  }

  @Override
  public CompletableFuture<Result<ImOpenResponse, SlackError>> openIm(ImOpenParams params) {
    return postSlackCommand(SlackMethods.im_open, params, ImOpenResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatPostMessageResponse, SlackError>> postMessage(ChatPostMessageParams params) {
    return postSlackCommand(SlackMethods.chat_postMessage, params, ChatPostMessageResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatPostEphemeralMessageResponse, SlackError>> postEphemeralMessage(ChatPostEphemeralMessageParams params) {
    return postSlackCommand(SlackMethods.chat_postEphemeral, params, ChatPostEphemeralMessageResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatUpdateMessageResponse, SlackError>> updateMessage(ChatUpdateMessageParams params) {
    return postSlackCommand(SlackMethods.chat_update, params, ChatUpdateMessageResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatGetPermalinkResponse, SlackError>> getPermalink(ChatGetPermalinkParams params) {
    return postSlackCommand(SlackMethods.chat_getPermalink, params, ChatGetPermalinkResponse.class);
  }

  @Override
  public CompletableFuture<Result<ChatDeleteResponse, SlackError>> deleteMessage(ChatDeleteParams params) {
    return postSlackCommand(SlackMethods.chat_delete, params, ChatDeleteResponse.class);
  }

  @Override
  public Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> listConversations(ConversationsListParams params) {
    return new AbstractPagedIterable<Result<List<Conversation>, SlackError>, String>() {

      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<Conversation>, SlackError>, String> getPage(String offset) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack conversation page from {}", offset);
        }

        ConversationsListParams.Builder requestBuilder = ConversationsListParams.builder()
            .from(params)
            .setLimit(config.getChannelsListBatchSize().get());
        Optional.ofNullable(offset)
            .ifPresent(requestBuilder::setCursor);

        CompletableFuture<Result<ConversationListResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.conversations_list,
            requestBuilder.build(),
            ConversationListResponse.class
        );

        CompletableFuture<Result<List<Conversation>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(ConversationListResponse::getConversations)
        );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(resultFuture);
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(Optional::isPresent);
        CompletableFuture<String> nextCursorFuture = nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(
            pageFuture,
            hasMoreFuture,
            nextCursorFuture
        );
      }
    };
  }

  @Override
  public CompletableFuture<Result<ConversationsCreateResponse, SlackError>> createConversation(ConversationCreateParams params) {
    return postSlackCommand(SlackMethods.conversations_create, params, ConversationsCreateResponse.class);
  }

  @Override
  public CompletableFuture<Result<ConversationsInviteResponse, SlackError>> inviteToConversation(ConversationInviteParams params) {
    return postSlackCommand(SlackMethods.conversations_invite, params, ConversationsInviteResponse.class);
  }

  @Override
  public CompletableFuture<Result<ConversationsUnarchiveResponse, SlackError>> unarchiveConversation(ConversationUnarchiveParams params) {
    return postSlackCommand(SlackMethods.conversations_unarchive, params, ConversationsUnarchiveResponse.class);
  }

  @Override
  public Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> getConversationHistory(ConversationsHistoryParams params) {
    PagingDirection pagingDirection = params.getPagingDirection();
    return new AbstractPagedIterable<Result<List<LiteMessage>, SlackError>, Long>() {
      @Override
      protected Long getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<LiteMessage>, SlackError>, Long> getPage(Long offset) throws Exception {
        ConversationsHistoryParams.Builder requestBuilder = ConversationsHistoryParams.builder()
            .from(params);
        if (!params.getLimit().isPresent()) {
          requestBuilder.setLimit(config.getConversationsHistoryMessageBatchSize().get());
        }

        Optional.ofNullable(offset)
            .ifPresent(presentOffset -> {
              if (pagingDirection == PagingDirection.FORWARD_IN_TIME) {
                requestBuilder.setOldestTimestamp(presentOffset.toString());
              } else {
                requestBuilder.setNewestTimestamp(presentOffset.toString());
              }
            });

        ConversationsHistoryParams currentRequest = requestBuilder.build();
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching conversation history page for {} from [{}, {}]",
              currentRequest.getChannelId(), currentRequest.getOldestTimestamp(), currentRequest.getNewestTimestamp()
          );
        }

        CompletableFuture<Result<ConversationsHistoryResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.conversations_history,
            currentRequest,
            ConversationsHistoryResponse.class
        );

        CompletableFuture<Result<List<LiteMessage>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(ConversationsHistoryResponse::getMessages)
        );

        CompletableFuture<Boolean> hasMoreFuture = resultFuture.thenApply(
            result -> result.match(
                err -> false,
                ok -> ok.hasMore()
            )
        );
        CompletableFuture<Long> nextOffset = nextOffset(pagingDirection, pageFuture);

        return new LazyLoadingPage<>(pageFuture, hasMoreFuture, nextOffset);
      }
    };
  }

  @Override
  public CompletableFuture<Result<ConversationsArchiveResponse, SlackError>> archiveConversation(ConversationArchiveParams params) {
    return postSlackCommand(SlackMethods.conversations_archive, params, ConversationsArchiveResponse.class);
  }

  @Override
  public CompletableFuture<Result<ConversationsInfoResponse, SlackError>> getConversationInfo(ConversationsInfoParams params) {
    return postSlackCommand(SlackMethods.conversations_info, params, ConversationsInfoResponse.class);
  }

  @Override
  public CompletableFuture<Result<Conversation, SlackError>> getConversationByName(String conversationName, ConversationsFilter conversationsFilter) {
    return findConversationByName(conversationName, conversationsFilter)
        .thenApply(conversation -> {
          if (conversation.isPresent()) {
            return Result.ok(conversation.get());
          } else {
            return Result.err(SlackError.builder()
                .setType(SlackErrorType.CHANNEL_NOT_FOUND)
                .setError("No conversation found with name: " + conversationName)
                .build());
          }
        });
  }

  private CompletableFuture<Optional<Conversation>> findConversationByName(String conversationName, ConversationsFilter conversationsFilter) {
    return searchNextConversationPage(conversationName,
        listConversations(ConversationsListParams.builder()
            .from(conversationsFilter)
            .build()
        ).iterator());
  }

  private CompletableFuture<Optional<Conversation>> searchNextConversationPage(
      String conversationName,
      Iterator<CompletableFuture<Result<List<Conversation>, SlackError>>> pageIterator
  ) {
    if (!pageIterator.hasNext()) {
      return CompletableFuture.completedFuture(Optional.empty());
    }

    CompletableFuture<Result<List<Conversation>, SlackError>> nextPage = pageIterator.next();
    return nextPage.thenApply(Result::unwrapOrElseThrow)
        .thenCompose(conversations -> {
          Optional<Conversation> matchInPage = conversations.stream()
              .filter(conversation -> conversation.getName().isPresent() && conversation.getName().get().equals(conversationName))
              .findFirst();
          if (matchInPage.isPresent()) {
            return CompletableFuture.completedFuture(matchInPage);
          }

          return searchNextConversationPage(conversationName, pageIterator);
        });
  }

  @Override
  public CompletableFuture<Result<UsergroupCreateResponse, SlackError>> createUsergroup(UsergroupCreateParams params) {
    return postSlackCommand(SlackMethods.usergroups_create, params, UsergroupCreateResponse.class);
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackUsergroup>, SlackError>>> listUsergroups(UsergroupListParams params) {
    return new AbstractPagedIterable<Result<List<SlackUsergroup>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackUsergroup>, SlackError>, String> getPage(String offset) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack usergroup page from {}", offset);
        }

        CompletableFuture<Result<UsergroupListResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.usergroups_list,
            UsergroupListParams.builder()
                .from(params)
                .build(),
            UsergroupListResponse.class
        );

        CompletableFuture<Result<List<SlackUsergroup>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(UsergroupListResponse::getUsergroups)
        );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(resultFuture);
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(Optional::isPresent);
        CompletableFuture<String> nextCursorFuture = nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(
            pageFuture,
            hasMoreFuture,
            nextCursorFuture
        );
      }
    };
  }

  @Override
  public CompletableFuture<Result<UsergroupUpdateResponse, SlackError>> updateUsergroup(UsergroupUpdateParams params) {
    return postSlackCommand(SlackMethods.usergroups_update, params, UsergroupUpdateResponse.class);
  }

  @Override
  public CompletableFuture<Result<UsergroupEnableResponse, SlackError>> enableUsergroup(UsergroupEnableParams params) {
    return postSlackCommand(SlackMethods.usergroups_enable, params, UsergroupEnableResponse.class);
  }

  @Override
  public CompletableFuture<Result<UsergroupDisableResponse, SlackError>> disableUsergroup(UsergroupDisableParams params) {
    return postSlackCommand(SlackMethods.usergroups_disable, params, UsergroupDisableResponse.class);
  }

  @Override
  public CompletableFuture<Result<UsergroupUsersUpdateResponse, SlackError>> updateUsergroupUsers(UsergroupUsersUpdateParams params) {
    return postSlackCommand(SlackMethods.usergroups_users_update, params, UsergroupUsersUpdateResponse.class);
  }

  @Override
  public CompletableFuture<Result<DialogOpenResponse, SlackError>> openDialog(DialogOpenParams params) {
    return postSlackCommand(SlackMethods.dialog_open, params, DialogOpenResponse.class);
  }

  @Override
  public CompletableFuture<Result<AddReactionResponse, SlackError>> addReaction(ReactionsAddParams params) {
    return postSlackCommand(SlackMethods.reactions_add, params, AddReactionResponse.class);
  }

  @Override
  public Iterable<CompletableFuture<Result<List<SlackGroup>, SlackError>>> listGroups(GroupsListParams filter) {
    return new AbstractPagedIterable<Result<List<SlackGroup>, SlackError>, String>() {
      @Override
      protected String getInitialOffset() {
        return null;
      }

      @Override
      protected LazyLoadingPage<Result<List<SlackGroup>, SlackError>, String> getPage(String offset) throws Exception {
        if (LOG.isTraceEnabled()) {
          LOG.trace("Fetching slack group page from {}", offset);
        }

        CompletableFuture<Result<GroupsListResponse, SlackError>> resultFuture = postSlackCommand(
            SlackMethods.groups_list,
            GroupsListParams.builder()
                .from(filter)
                .build(), GroupsListResponse.class
        );

        CompletableFuture<Result<List<SlackGroup>, SlackError>> pageFuture = resultFuture.thenApply(
            result -> result.mapOk(GroupsListResponse::getGroups)
        );

        CompletableFuture<Optional<String>> nextCursorMaybeFuture = extractNextCursor(resultFuture);
        CompletableFuture<Boolean> hasMoreFuture = nextCursorMaybeFuture.thenApply(Optional::isPresent);
        CompletableFuture<String> nextCursorFuture = nextCursorMaybeFuture.thenApply(cursorMaybe -> cursorMaybe.orElse(null));

        return new LazyLoadingPage<>(
            pageFuture,
            hasMoreFuture,
            nextCursorFuture
        );
      }
    };
  }

  public <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommand(
      SlackMethod method,
      Object params,
      Class<T> returnClazz) {
    if (!methodAcceptor.test(method, params)) {
      LOG.info("Acceptor failed: {}", methodAcceptor.getFailureExplanation(method, params));
      return CompletableFuture.completedFuture(
          Result.err(SlackError.builder()
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
    return HttpRequest.newBuilder()
        .setMethod(Method.POST)
        .setUrl(config.getSlackApiBasePath().get() + "/" + method.getMethod());
  }

  private <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> executeLoggedAs(
      SlackMethod method,
      HttpRequest request,
      Class<T> responseType
  ) {
    long requestId = REQUEST_COUNTER.getAndIncrement();
    return executeLogged(requestId, method, request)
        .thenApply(response -> {
          try {
            JsonNode responseJson = response.getAsJsonNode();
            boolean isOk = responseJson.get("ok").asBoolean();
            if (isOk) {
              return Result.ok(ObjectMapperUtils.mapper().treeToValue(responseJson, responseType));
            }

            SlackError error = ObjectMapperUtils.mapper().treeToValue(response.getAsJsonNode(), SlackError.class);
            responseDebugger.debugSlackApiError(requestId, method, request, response);
            return Result.err(error);
          } catch (JsonProcessingException e) {
            responseDebugger.debugProcessingFailure(requestId, method, request, response, e);
            return Result.err(SlackError.builder()
                .setError(SlackErrorType.JSON_PARSING_FAILED.getCode())
                .setType(SlackErrorType.JSON_PARSING_FAILED)
                .build()
            );
          } catch (RuntimeException ex) {
            responseDebugger.debugProcessingFailure(requestId, method, request, response, ex);
            throw ex;
          }
        });
  }

  private <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommandUrlEncoded(
      SlackMethod method,
      Multimap<String, String> params,
      Class<T> responseType
  ) {
    HttpRequest.Builder requestBuilder = buildBaseSlackPost(method)
        .setContentType(ContentType.FORM);
    params.entries()
        .forEach(param -> requestBuilder.setFormParam(param.getKey()).to(param.getValue()));
    requestBuilder.setQueryParam("token").to(config.getTokenSupplier().get());
    return executeLoggedAs(method, requestBuilder.build(), responseType);
  }

  private CompletableFuture<HttpResponse> executeLogged(
      long requestId,
      SlackMethod method,
      HttpRequest request
  ) {
    requestDebugger.debug(requestId, method, request);
    Stopwatch timer = Stopwatch.createStarted();

    acquirePermit(method);
    CompletableFuture<HttpResponse> responseFuture = nioHttpClient.executeCompletableFuture(request);

    responseFuture.whenComplete((httpResponse, throwable) -> {
      if (throwable != null) {
        responseDebugger.debugTransportException(requestId, method, request, throwable);

      } else {
        responseDebugger.debug(requestId, method, timer, request, httpResponse);
      }
    });

    return responseFuture;
  }

  private void acquirePermit(SlackMethod method) {
    double permissibleQps = method.getRateLimitingTier().getMinutelyAllowance() / 60.0;
    double acquireTime = RATE_LIMITERS.computeIfAbsent(
        method,
        ignored -> RateLimiter.create(permissibleQps)
    ).acquire();
    if (acquireTime > 10.0) {
      LOG.warn("Throttling {}, waited {}ms to acquire permit to run", method, acquireTime);
    }
  }
}
