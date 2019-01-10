package com.hubspot.slack.client;

import java.io.Closeable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.hubspot.algebra.Result;
import com.hubspot.slack.client.methods.SlackMethod;
import com.hubspot.slack.client.methods.params.auth.AuthRevokeParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsFilter;
import com.hubspot.slack.client.methods.params.channels.ChannelsHistoryParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsInfoParams;
import com.hubspot.slack.client.methods.params.channels.ChannelsListParams;
import com.hubspot.slack.client.methods.params.channels.FindRepliesParams;
import com.hubspot.slack.client.methods.params.chat.ChatDeleteParams;
import com.hubspot.slack.client.methods.params.chat.ChatGetPermalinkParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostEphemeralMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatUpdateMessageParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationArchiveParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationCreateParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationInviteParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationOpenParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationUnarchiveParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsFilter;
import com.hubspot.slack.client.methods.params.conversations.ConversationsHistoryParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsInfoParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsListParams;
import com.hubspot.slack.client.methods.params.conversations.ConversationsUserParams;
import com.hubspot.slack.client.methods.params.dialog.DialogOpenParams;
import com.hubspot.slack.client.methods.params.files.FilesSharedPublicUrlParams;
import com.hubspot.slack.client.methods.params.files.FilesUploadParams;
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
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.SlackResponse;
import com.hubspot.slack.client.models.response.auth.AuthRevokeResponse;
import com.hubspot.slack.client.models.response.auth.AuthTestResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsInfoResponse;
import com.hubspot.slack.client.models.response.chat.ChatDeleteResponse;
import com.hubspot.slack.client.models.response.chat.ChatGetPermalinkResponse;
import com.hubspot.slack.client.models.response.chat.ChatPostEphemeralMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;
import com.hubspot.slack.client.models.response.chat.ChatUpdateMessageResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsArchiveResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsCreateResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsInfoResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsInviteResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsOpenResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsUnarchiveResponse;
import com.hubspot.slack.client.models.response.dialog.DialogOpenResponse;
import com.hubspot.slack.client.models.response.files.FilesSharedPublicUrlResponse;
import com.hubspot.slack.client.models.response.files.FilesUploadResponse;
import com.hubspot.slack.client.models.response.im.ImOpenResponse;
import com.hubspot.slack.client.models.response.reactions.AddReactionResponse;
import com.hubspot.slack.client.models.response.search.SearchMessageResponse;
import com.hubspot.slack.client.models.response.team.TeamInfoResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupCreateResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupDisableResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupEnableResponse;
import com.hubspot.slack.client.models.response.usergroups.UsergroupUpdateResponse;
import com.hubspot.slack.client.models.response.usergroups.users.UsergroupUsersUpdateResponse;
import com.hubspot.slack.client.models.response.users.UsersInfoResponse;
import com.hubspot.slack.client.models.response.users.UsersListResponse;
import com.hubspot.slack.client.models.usergroups.SlackUsergroup;
import com.hubspot.slack.client.models.users.SlackUser;

public interface SlackClient extends Closeable {
  // auth
  CompletableFuture<Result<AuthTestResponse, SlackError>> testAuth();
  CompletableFuture<Result<AuthRevokeResponse, SlackError>> revokeAuth(AuthRevokeParams params);

  // searching
  CompletableFuture<Result<SearchMessageResponse, SlackError>> searchMessages(SearchMessagesParams params);
  CompletableFuture<Result<FindRepliesResponse, SlackError>> findReplies(FindRepliesParams params);
  CompletableFuture<Result<UsersInfoResponse, SlackError>> findUser(UsersInfoParams params);
  CompletableFuture<Result<UsersInfoResponse, SlackError>> lookupUserByEmail(UserEmailParams email);

  // users
  Iterable<CompletableFuture<Result<List<SlackUser>, SlackError>>> listUsers();
  CompletableFuture<Result<UsersListResponse, SlackError>> listUsersPaginated(UsersListParams params);

  // channels
  Iterable<CompletableFuture<Result<List<SlackChannel>, SlackError>>> listChannels(ChannelsListParams filter);
  Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> channelHistory(ChannelsHistoryParams params);
  CompletableFuture<Result<SlackChannel, SlackError>> getChannelByName(String channelName, ChannelsFilter channelsFilter);
  CompletableFuture<Result<ChannelsInfoResponse, SlackError>> getChannelInfo(ChannelsInfoParams params);

  // groups
  Iterable<CompletableFuture<Result<List<SlackGroup>, SlackError>>> listGroups(GroupsListParams filter);

  // messaging
  CompletableFuture<Result<ImOpenResponse, SlackError>> openIm(ImOpenParams params);
  CompletableFuture<Result<ChatPostMessageResponse, SlackError>> postMessage(ChatPostMessageParams params);
  CompletableFuture<Result<ChatPostEphemeralMessageResponse, SlackError>> postEphemeralMessage(ChatPostEphemeralMessageParams params);
  CompletableFuture<Result<ChatUpdateMessageResponse, SlackError>> updateMessage(ChatUpdateMessageParams params);
  CompletableFuture<Result<ChatGetPermalinkResponse, SlackError>> getPermalink(ChatGetPermalinkParams params);
  CompletableFuture<Result<ChatDeleteResponse, SlackError>> deleteMessage(ChatDeleteParams params);

  // conversations
  Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> listConversations(ConversationsListParams params);
  Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> usersConversations(ConversationsUserParams params);
  CompletableFuture<Result<ConversationsCreateResponse, SlackError>> createConversation(ConversationCreateParams params);
  CompletableFuture<Result<ConversationsInviteResponse, SlackError>> inviteToConversation(ConversationInviteParams params);
  CompletableFuture<Result<ConversationsUnarchiveResponse, SlackError>> unarchiveConversation(ConversationUnarchiveParams params);
  Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> getConversationHistory(ConversationsHistoryParams params);
  CompletableFuture<Result<ConversationsArchiveResponse, SlackError>> archiveConversation(ConversationArchiveParams params);
  CompletableFuture<Result<ConversationsInfoResponse, SlackError>> getConversationInfo(ConversationsInfoParams params);
  CompletableFuture<Result<Conversation, SlackError>> getConversationByName(String conversationName, ConversationsFilter conversationsFilter);
  CompletableFuture<Result<ConversationsOpenResponse, SlackError>> openConversation(ConversationOpenParams params);

  // usergroups
  CompletableFuture<Result<UsergroupCreateResponse, SlackError>> createUsergroup(UsergroupCreateParams params);
  Iterable<CompletableFuture<Result<List<SlackUsergroup>, SlackError>>> listUsergroups(UsergroupListParams params);
  CompletableFuture<Result<UsergroupUpdateResponse, SlackError>> updateUsergroup(UsergroupUpdateParams params);
  CompletableFuture<Result<UsergroupEnableResponse, SlackError>> enableUsergroup(UsergroupEnableParams params);
  CompletableFuture<Result<UsergroupDisableResponse, SlackError>> disableUsergroup(UsergroupDisableParams params);

  // usergroups.users
  CompletableFuture<Result<UsergroupUsersUpdateResponse, SlackError>> updateUsergroupUsers(UsergroupUsersUpdateParams params);

  // dialogs
  CompletableFuture<Result<DialogOpenResponse, SlackError>> openDialog(DialogOpenParams params);

  // reactions
  CompletableFuture<Result<AddReactionResponse, SlackError>> addReaction(ReactionsAddParams param);

  // teams
  CompletableFuture<Result<TeamInfoResponse, SlackError>> getTeamInfo();

  // files
  CompletableFuture<Result<FilesUploadResponse, SlackError>> uploadFile(FilesUploadParams params);
  CompletableFuture<Result<FilesSharedPublicUrlResponse, SlackError>> shareFilePublically(FilesSharedPublicUrlParams params);

  // extension
  <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommand(
      SlackMethod method,
      Object params,
      Class<T> returnClazz
  );
}
