package com.hubspot.slack.client;

import com.hubspot.algebra.Result;
import com.hubspot.slack.client.methods.SlackMethod;
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
import com.hubspot.slack.client.methods.params.chat.ChatDeleteParams;
import com.hubspot.slack.client.methods.params.chat.ChatDeleteScheduledMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatGetPermalinkParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostEphemeralMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatScheduleMessageParams;
import com.hubspot.slack.client.methods.params.chat.ChatScheduledMessagesListParams;
import com.hubspot.slack.client.methods.params.chat.ChatUnfurlParams;
import com.hubspot.slack.client.methods.params.chat.ChatUpdateMessageParams;
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
import com.hubspot.slack.client.models.response.SimpleSlackResponse;
import com.hubspot.slack.client.models.response.SlackError;
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
import com.hubspot.slack.client.models.response.channels.ChannelsInfoResponse;
import com.hubspot.slack.client.models.response.channels.ChannelsKickResponse;
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
import com.hubspot.slack.client.models.response.conversations.ConversationKickResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationListResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationMemberResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationSetPurposeResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationSetTopicResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsArchiveResponse;
import com.hubspot.slack.client.models.response.conversations.ConversationsCreateResponse;
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
import com.hubspot.slack.client.models.response.files.FileInfoRequest;
import com.hubspot.slack.client.models.response.files.FileInfoResponse;
import com.hubspot.slack.client.models.response.files.FilesSharedPublicUrlResponse;
import com.hubspot.slack.client.models.response.files.FilesUploadResponse;
import com.hubspot.slack.client.models.response.files.GetUploadUrlExternalResponse;
import com.hubspot.slack.client.models.response.group.GroupsKickResponse;
import com.hubspot.slack.client.models.response.im.ImOpenResponse;
import com.hubspot.slack.client.models.response.migration.MigrationExchangeResponse;
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
import com.hubspot.slack.client.models.response.users.UsersProfileResponse;
import com.hubspot.slack.client.models.response.views.HomeTabViewCommandResponse;
import com.hubspot.slack.client.models.response.views.ModalViewCommandResponse;
import com.hubspot.slack.client.models.usergroups.SlackUsergroup;
import com.hubspot.slack.client.models.users.SlackUser;
import java.io.Closeable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

// See https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api for details on deprecations starting 7 January 2020
public interface SlackClient extends Closeable {
  // auth
  CompletableFuture<Result<AuthTestResponse, SlackError>> testAuth();
  CompletableFuture<Result<AuthRevokeResponse, SlackError>> revokeAuth(
    AuthRevokeParams params
  );

  // searching
  CompletableFuture<Result<SearchMessageResponse, SlackError>> searchMessages(
    SearchMessagesParams params
  );
  CompletableFuture<Result<FindRepliesResponse, SlackError>> findReplies(
    FindRepliesParams params
  );
  CompletableFuture<Result<UsersInfoResponse, SlackError>> findUser(
    UsersInfoParams params
  );
  CompletableFuture<Result<UsersInfoResponse, SlackError>> lookupUserByEmail(
    UserEmailParams email
  );
  CompletableFuture<Result<UsersProfileResponse, SlackError>> getUserProfile(
    UsersInfoParams params
  );
  CompletableFuture<Result<UsersProfileResponse, SlackError>> setUserProfile(
    SetUserProfileParams params
  );

  // users
  Iterable<CompletableFuture<Result<List<SlackUser>, SlackError>>> listUsers();
  CompletableFuture<Result<UsersListResponse, SlackError>> listUsersPaginated(
    UsersListParams params
  );

  // channels

  CompletableFuture<Result<DndInfoResponse, SlackError>> getDndInfo(DndInfoParams params);

  CompletableFuture<Result<SimpleSlackResponse, SlackError>> endDnd();

  CompletableFuture<Result<DndSnoozeResponse, SlackError>> setDndSnooze(
    DndSetSnoozeParams params
  );

  CompletableFuture<Result<DndInfoResponse, SlackError>> endDndSnooze();

  /**
   * Will be removed on or after November 25, 2020
   * @deprecated use {@link #listConversations(ConversationsListParams)}
   */
  @Deprecated
  Iterable<CompletableFuture<Result<List<SlackChannel>, SlackError>>> listChannels(
    ChannelsListParams filter
  );

  /**
   * Will be removed on or after November 25, 2020
   * @deprecated use {@link #getConversationHistory(ConversationsHistoryParams)}
   */
  @Deprecated
  Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> channelHistory(
    ChannelsHistoryParams params
  );

  /**
   * Will be removed on or after November 25, 2020
   * @deprecated use {@link #getConversationByName(String, ConversationsFilter)}
   */
  @Deprecated
  CompletableFuture<Result<SlackChannel, SlackError>> getChannelByName(
    String channelName,
    ChannelsFilter channelsFilter
  );

  /**
   * Will be removed on or after November 25, 2020
   * @deprecated use {@link #getConversationInfo(ConversationsInfoParams)}
   */
  @Deprecated
  CompletableFuture<Result<ChannelsInfoResponse, SlackError>> getChannelInfo(
    ChannelsInfoParams params
  );

  /**
   * Will be removed on or after November 25, 2020
   * @deprecated need to implement conversations.kick to mimic this functionality
   */
  @Deprecated
  CompletableFuture<Result<ChannelsKickResponse, SlackError>> kickUserFromChannel(
    ChannelsKickParams channelKickParams
  );

  // groups
  /**
   * Will be removed on or after November 25, 2020
   * @deprecated use {@link #listConversations(ConversationsListParams)}
   */
  @Deprecated
  Iterable<CompletableFuture<Result<List<SlackGroup>, SlackError>>> listGroups(
    GroupsListParams filter
  );

  /**
   * Will be removed on or after November 25, 2020
   * @deprecated need to implement conversations.kick to mimic this functionality
   */
  @Deprecated
  CompletableFuture<Result<GroupsKickResponse, SlackError>> kickUserFromGroup(
    GroupsKickParams params
  );

  // messaging
  /**
   * Will be removed on or after November 25, 2020
   * @deprecated use {@link #openConversation(ConversationOpenParams)}
   */
  @Deprecated
  CompletableFuture<Result<ImOpenResponse, SlackError>> openIm(ImOpenParams params);

  CompletableFuture<Result<ChatPostMessageResponse, SlackError>> postMessage(
    ChatPostMessageParams params
  );
  CompletableFuture<Result<ChatPostEphemeralMessageResponse, SlackError>> postEphemeralMessage(
    ChatPostEphemeralMessageParams params
  );
  CompletableFuture<Result<ChatUpdateMessageResponse, SlackError>> updateMessage(
    ChatUpdateMessageParams params
  );
  CompletableFuture<Result<ChatScheduleMessageResponse, SlackError>> scheduleMessage(
    ChatScheduleMessageParams params
  );
  CompletableFuture<Result<ChatScheduledMessagesListResponse, SlackError>> scheduledMessageList(
    ChatScheduledMessagesListParams params
  );
  CompletableFuture<Result<ChatDeleteScheduledMessageResponse, SlackError>> deleteScheduledMessage(
    ChatDeleteScheduledMessageParams params
  );
  CompletableFuture<Result<ChatGetPermalinkResponse, SlackError>> getPermalink(
    ChatGetPermalinkParams params
  );
  CompletableFuture<Result<ChatDeleteResponse, SlackError>> deleteMessage(
    ChatDeleteParams params
  );
  CompletableFuture<Result<ChatUnfurlResponse, SlackError>> unfurlLinks(
    ChatUnfurlParams params
  );

  // conversations
  Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> listConversations(
    ConversationsListParams params
  );
  CompletableFuture<Result<ConversationListResponse, SlackError>> listConversationsPaginated(
    ConversationsListParams params
  );
  Iterable<CompletableFuture<Result<List<Conversation>, SlackError>>> usersConversations(
    ConversationsUserParams params
  );
  CompletableFuture<Result<ConversationListResponse, SlackError>> usersConversationsPaginated(
    ConversationsUserParams params
  );
  CompletableFuture<Result<ConversationsCreateResponse, SlackError>> createConversation(
    ConversationCreateParams params
  );
  CompletableFuture<Result<ConversationsInviteResponse, SlackError>> inviteToConversation(
    ConversationInviteParams params
  );
  CompletableFuture<Result<SharedChannelInviteResponse, SlackError>> inviteToSharedConversation(
    ConversationInviteSharedParams params
  );
  CompletableFuture<Result<ConversationsUnarchiveResponse, SlackError>> unarchiveConversation(
    ConversationUnarchiveParams params
  );
  Iterable<CompletableFuture<Result<List<LiteMessage>, SlackError>>> getConversationHistory(
    ConversationsHistoryParams params
  );
  CompletableFuture<Result<ConversationsArchiveResponse, SlackError>> archiveConversation(
    ConversationArchiveParams params
  );
  CompletableFuture<Result<ConversationsInfoResponse, SlackError>> getConversationInfo(
    ConversationsInfoParams params
  );
  CompletableFuture<Result<ConversationsRepliesResponse, SlackError>> getConversationReplies(
    ConversationsRepliesParams params
  );
  CompletableFuture<Result<Conversation, SlackError>> getConversationByName(
    String conversationName,
    ConversationsFilter conversationsFilter
  );
  CompletableFuture<Result<ConversationsOpenResponse, SlackError>> openConversation(
    ConversationOpenParams params
  );
  Iterable<CompletableFuture<Result<List<String>, SlackError>>> getConversationMembers(
    ConversationMemberParams params
  );
  CompletableFuture<Result<ConversationMemberResponse, SlackError>> getConversationMembersPaginated(
    ConversationMemberParams params
  );
  CompletableFuture<Result<ConversationsInfoResponse, SlackError>> joinConversation(
    ConversationsJoinParams params
  );
  CompletableFuture<Result<ConversationSetPurposeResponse, SlackError>> setConversationPurpose(
    ConversationSetPurposeParams params
  );
  CompletableFuture<Result<ConversationSetTopicResponse, SlackError>> setConversationTopic(
    ConversationSetTopicParams params
  );
  CompletableFuture<Result<ConversationKickResponse, SlackError>> kickUserFromConversation(
    ConversationKickParams params
  );

  // usergroups
  CompletableFuture<Result<UsergroupCreateResponse, SlackError>> createUsergroup(
    UsergroupCreateParams params
  );
  Iterable<CompletableFuture<Result<List<SlackUsergroup>, SlackError>>> listUsergroups(
    UsergroupListParams params
  );
  CompletableFuture<Result<UsergroupUpdateResponse, SlackError>> updateUsergroup(
    UsergroupUpdateParams params
  );
  CompletableFuture<Result<UsergroupEnableResponse, SlackError>> enableUsergroup(
    UsergroupEnableParams params
  );
  CompletableFuture<Result<UsergroupDisableResponse, SlackError>> disableUsergroup(
    UsergroupDisableParams params
  );

  // usergroups.users
  CompletableFuture<Result<UsergroupUsersUpdateResponse, SlackError>> updateUsergroupUsers(
    UsergroupUsersUpdateParams params
  );

  // dialogs
  CompletableFuture<Result<DialogOpenResponse, SlackError>> openDialog(
    DialogOpenParams params
  );

  // reactions
  CompletableFuture<Result<AddReactionResponse, SlackError>> addReaction(
    ReactionsAddParams param
  );

  // teams
  CompletableFuture<Result<TeamInfoResponse, SlackError>> getTeamInfo();

  // files
  /**
   * This method is being sunset on March 11, 2025
   * @deprecated use {@link #getUploadURLExternal(GetUploadUrlExternalParams)} and {@link #completeUploadExternal(CompleteUploadExternalParams)}
   * @see <a href="https://api.slack.com/changelog/2024-04-a-better-way-to-upload-files-is-here-to-stay">Sunset details</a>
   */
  @Deprecated
  CompletableFuture<Result<FilesUploadResponse, SlackError>> uploadFile(
    FilesUploadParams params
  );

  CompletableFuture<Result<GetUploadUrlExternalResponse, SlackError>> getUploadURLExternal(
    GetUploadUrlExternalParams params
  );
  CompletableFuture<Result<CompleteUploadExternalResponse, SlackError>> completeUploadExternal(
    CompleteUploadExternalParams params
  );
  CompletableFuture<Result<FilesSharedPublicUrlResponse, SlackError>> shareFilePublically(
    FilesSharedPublicUrlParams params
  );
  CompletableFuture<Result<FileInfoResponse, SlackError>> getFileInfo(
    FileInfoRequest request
  );

  // emoji
  CompletableFuture<Result<EmojiListResponse, SlackError>> listEmoji();

  // views
  CompletableFuture<Result<ModalViewCommandResponse, SlackError>> openView(
    OpenViewParams params
  );
  CompletableFuture<Result<ModalViewCommandResponse, SlackError>> updateView(
    UpdateViewParams params
  );
  CompletableFuture<Result<ModalViewCommandResponse, SlackError>> pushView(
    OpenViewParams params
  );
  CompletableFuture<Result<HomeTabViewCommandResponse, SlackError>> publishView(
    PublishViewParams params
  );

  // bookmarks
  CompletableFuture<Result<BookmarkAddResponse, SlackError>> addBookmark(
    BookmarksAddParams params
  );
  CompletableFuture<Result<BookmarkEditResponse, SlackError>> editBookmark(
    BookmarksEditParams params
  );
  CompletableFuture<Result<BookmarkRemoveResponse, SlackError>> removeBookmark(
    BookmarksRemoveParams params
  );
  CompletableFuture<Result<BookmarkListResponse, SlackError>> listBookmarks(
    BookmarksListParams params
  );

  // migration.exchange
  CompletableFuture<Result<MigrationExchangeResponse, SlackError>> migrationExchange(
    MigrationExchangeParams params
  );

  // calls
  CompletableFuture<Result<CallsAddResponse, SlackError>> addCall(CallsAddParams params);
  CompletableFuture<Result<CallsEndResponse, SlackError>> endCall(CallsEndParams params);
  CompletableFuture<Result<CallsUpdateResponse, SlackError>> updateCall(
    CallsUpdateParams params
  );
  CompletableFuture<Result<CallsInfoResponse, SlackError>> getCallInfo(
    CallsInfoParams params
  );
  CompletableFuture<Result<CallsParticipantsAddResponse, SlackError>> addCallParticipants(
    CallsParticipantsAddParams params
  );
  CompletableFuture<Result<CallsParticipantsRemoveResponse, SlackError>> removeCallParticipants(
    CallsParticipantsRemoveParams params
  );

  // extension
  <T extends SlackResponse> CompletableFuture<Result<T, SlackError>> postSlackCommand(
    SlackMethod method,
    Object params,
    Class<T> returnClazz
  );
}
