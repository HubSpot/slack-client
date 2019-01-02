package com.hubspot.slack.client.models.response;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.hubspot.slack.client.enums.EnumIndex;
import com.hubspot.slack.client.enums.UnmappedKeyException;

public enum SlackErrorType {
  ACCOUNT_INACTIVE("account_inactive"),
  ALREADY_REACTED("already_reacted"),
  APP_MISSING_ACTION_URL("app_missing_action_url"),
  AS_USER_NOT_SUPPORTED("as_user_not_supported"),

  CANT_DELETE_MESSAGE("cant_delete_message"),
  CANT_UPDATE_MESSAGE("cant_update_message"),
  CANNOT_CREATE_DIALOG("cannot_create_dialog"),
  CHANNEL_NOT_FOUND("channel_not_found"),
  COMPLIANCE_EXPORTS_PREVENT_DELETION("compliance_exports_prevent_deletion"),

  EDIT_WINDOW_CLOSED("edit_window_closed"),

  FAILED_SENDING_DIALOG("failed_sending_dialog"),
  FATAL_ERROR("fatal_error"),
  FILE_NOT_FOUND("file_not_found"),

  JSON_NOT_OBJECT("json_not_object"),

  MISSING_DIALOG("missing_dialog"),
  MISSING_POST_TYPE("missing_post_type"),
  MISSING_SCOPE("missing_scope"),
  MISSING_TRIGGER("missing_trigger"),
  MESSAGE_TOO_LONG("msg_too_long"),
  MESSAGE_NOT_FOUND("message_not_found"),

  NAME_ALREADY_EXISTS("name_already_exists"),
  NAME_TAKEN("name_taken"),
  NO_PERMISSION("no_permission"),
  NO_TEXT("no_text"),
  NOT_AUTHED("not_authed"),
  NOT_ALLOWED("not_allowed"),
  NOT_ALLOWED_TOKEN_TYPE("not_allowed_token_type"),

  ORG_LOGIN_REQUIRED("org_login_required"),

  INVALID_AUTH("invalid_auth"),
  INVALID_ARG_NAME("invalid_arg_name"),
  INVALID_ARRAY_ARG("invalid_array_arg"),
  INVALID_CHARSET("invalid_charset"),
  INVALID_FORM_DATA("invalid_form_data"),
  INVALID_POST_TYPE("invalid_post_type"),
  INVALID_JSON("invalid_json"),
  INVALID_TRIGGER("invalid_trigger"),
  INVALID_TS_OLDEST("invalid_ts_oldest"),
  INVALID_TS_LATEsT("invalid_ts_latest"),
  IS_ARCHIVED("is_archived"),

  REQUEST_TIMEOUT("request_timeout"),
  RATE_LIMITED("ratelimited"),

  TEAM_ADDED_TO_ORG("team_added_to_org"),
  TOO_MANY_ATTACHMENTS("too_many_attachments"),
  TRIGGER_EXCHANGED("trigger_exchanged"),
  TRIGGER_EXPIRED("trigger_expired"),

  TOKEN_REVOKED("token_revoked"),

  USER_NOT_FOUND("user_not_found"), // all APIs except users.lookupByEmail
  USERS_NOT_FOUND("users_not_found"), // users.lookupByEmail
  USERS_NOT_VISIBLE("user_not_visible"),
  USER_NOT_IN_CHANNEL("user_not_in_channel"),
  USER_IS_BOT("user_is_bot"),
  USER_IS_RESTRICTED("user_is_restricted"),
  USER_DISABLED("user_disabled"),

  VALIDATION_ERRORS("validation_errors"),

  // non-slack errors
  JSON_PARSING_FAILED("_json_parsing_failed"),
  WRITE_RESTRICTED_TO_PROD("_write_restricted_to_prod"),
  PARAMS_FAILED_API_FILTER("_method_failed_filter"),
  UNKNOWN("unknown"),
  ;

  private final String code;

  SlackErrorType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public String key() {
    return name().toLowerCase();
  }


  private static final ImmutableMap<String, SlackErrorType> CODE_INDEX = Maps.uniqueIndex(Arrays.asList(SlackErrorType.values()), SlackErrorType::getCode);

  private static final EnumIndex<String, SlackErrorType> TYPE_INDEX = new EnumIndex<>(SlackErrorType.class, SlackErrorType::key);

  @JsonCreator
  public static SlackErrorType get(String code) {
    try {
      return TYPE_INDEX.get(code.toLowerCase());
    } catch (UnmappedKeyException uke) {
      return CODE_INDEX.getOrDefault(code.toLowerCase(), SlackErrorType.UNKNOWN);
    }
  }
}
