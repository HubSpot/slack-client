package com.hubspot.slack.client.methods;

/**
 * Slack api method names inferred by converting '_' to '.', so make sure they match.
 */
public enum SlackMethod {
  api_test(MethodWriteMode.READ, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),

  apps_permission_info(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  apps_permission_request(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  auth_revoke(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  auth_test(MethodWriteMode.READ, SpecialTier.of(200), JsonStatus.ACCEPTS_JSON),

  bots_info(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),

  channels_archive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  channels_create(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  channels_history(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  channels_info(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  channels_invite(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  channels_join(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  channels_kick(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  channels_leave(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  channels_list(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  channels_mark(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  channels_rename(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  channels_replies(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  channels_setPurpose(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  channels_setTopic(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  channels_unarchive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  chat_delete(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  chat_getPermalink(MethodWriteMode.READ, SpecialTier.of(200), JsonStatus.FORM_ENCODING_ONLY),
  chat_meMessage(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  chat_postEphemeral(MethodWriteMode.WRITE, RateLimitingTiers.TIER_4, JsonStatus.ACCEPTS_JSON),
  // Rate limiting is 1 per second per channel, or several hundred for workspace per minute
  chat_postMessage(MethodWriteMode.WRITE, SpecialTier.of(300), JsonStatus.ACCEPTS_JSON),
  chat_unfurl(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  chat_update(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),

  conversations_archive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  conversations_close(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  conversations_create(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  conversations_history(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  conversations_info(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  conversations_invite(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  conversations_join(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  conversations_kick(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  conversations_leave(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  conversations_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  conversations_members(MethodWriteMode.READ, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),
  conversations_open(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  conversations_rename(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  conversations_replies(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  conversations_setPurpose(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  conversations_setTopic(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  conversations_unarchive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  dialog_open(MethodWriteMode.WRITE, RateLimitingTiers.TIER_4, JsonStatus.ACCEPTS_JSON),

  dnd_EndDnd(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  dnd_endSnooze(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  dnd_info(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  dnd_setSnooze(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  dnd_teamInfo(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  emoji_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  files_comments_add(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  files_comments_delete(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  files_comments_edit(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  files_delete(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  files_info(MethodWriteMode.READ, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),
  files_list(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  files_revokePublicURL(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  files_sharedPublicURL(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  files_upload(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  groups_archive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_create(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_createChild(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  groups_history(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  groups_info(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  groups_invite(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  groups_kick(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_leave(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_list(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  groups_mark(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  groups_open(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_rename(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_replies(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  groups_setPurpose(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_setTopic(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  groups_unarchive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  im_close(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  im_history(MethodWriteMode.READ, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),
  im_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  im_mark(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  im_open(MethodWriteMode.WRITE, RateLimitingTiers.TIER_4, JsonStatus.ACCEPTS_JSON),
  im_replies(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  migration_exchange(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  mpim_close(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  mpim_history(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  mpim_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  mpim_mark(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  mpim_open(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  mpim_replies(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  oauth_access(MethodWriteMode.WRITE, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),
  oauth_token(MethodWriteMode.WRITE, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),

  pins_add(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  pins_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  pins_remove(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  reactions_add(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),
  reactions_get(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  reactions_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  reactions_remove(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  reminders_add(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  reminders_complete(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  reminders_delete(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  reminders_info(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  reminders_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  rtm_connect(MethodWriteMode.WRITE, RateLimitingTiers.TIER_1, JsonStatus.FORM_ENCODING_ONLY),
  rtm_start(MethodWriteMode.WRITE, RateLimitingTiers.TIER_1, JsonStatus.FORM_ENCODING_ONLY),

  search_all(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  search_files(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  search_messages(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  stars_add(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  stars_list(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  stars_remove(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  team_accessLogs(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  team_billableInfo(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  team_info(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  team_integrationLogs(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),

  team_profile_get(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),

  usergroups_create(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  usergroups_disable(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  usergroups_enable(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  usergroups_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  usergroups_update(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  usergroups_users_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  usergroups_users_update(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  users_deletePhoto(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  users_getPresence(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  users_identity(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  users_info(MethodWriteMode.READ, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),
  users_list(MethodWriteMode.READ, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  users_lookupByEmail(MethodWriteMode.READ, RateLimitingTiers.TIER_3, JsonStatus.FORM_ENCODING_ONLY),
  users_setActive(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),
  users_setPhoto(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.FORM_ENCODING_ONLY),
  users_setPresence(MethodWriteMode.WRITE, RateLimitingTiers.TIER_2, JsonStatus.ACCEPTS_JSON),

  users_profile_get(MethodWriteMode.READ, RateLimitingTiers.TIER_4, JsonStatus.FORM_ENCODING_ONLY),
  users_profile_set(MethodWriteMode.WRITE, RateLimitingTiers.TIER_3, JsonStatus.ACCEPTS_JSON),

  ;

  private final MethodWriteMode writeMode;
  private final RateLimitingTier rateLimitingTier;
  private final JsonStatus jsonStatus;

  SlackMethod(
      MethodWriteMode writeMode,
      RateLimitingTier rateLimitingTier,
      JsonStatus jsonStatus
  ) {
    this.writeMode = writeMode;
    this.rateLimitingTier = rateLimitingTier;
    this.jsonStatus = jsonStatus;
  }

  public String getMethod() {
    return name().replace('_', '.');
  }

  public MethodWriteMode getWriteMode() {
    return writeMode;
  }

  public JsonStatus jsonWhitelistStatus() {
    return jsonStatus;
  }

  public RateLimitingTier getRateLimitingTier() {
    return rateLimitingTier;
  }
}
