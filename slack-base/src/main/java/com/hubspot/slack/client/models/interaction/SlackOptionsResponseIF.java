package com.hubspot.slack.client.models.interaction;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.immutables.value.Value.Derived;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.BaseSlackOptionsResponse;
import com.hubspot.slack.client.models.actions.Option;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormOption;
import com.hubspot.slack.client.models.dialog.form.elements.SlackFormOptionGroup;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
public interface SlackOptionsResponseIF extends BaseSlackOptionsResponse {
  String TYPE = "slack_options_response";

  // NOTE: slack annoyingly has a different model for dialog options and attachment options
  // https://api.slack.com/dialogs | https://api.slack.com/docs/message-menus

  static SlackOptionsResponse emptyAttachmentOptions() {
    return SlackOptionsResponse
        .builder()
        .setAttachmentOptions(Collections.emptyList())
        .build();
  }

  static SlackOptionsResponse ofAttachmentOptions(List<Option> options) {
    return SlackOptionsResponse.builder().setAttachmentOptions(options).build();
  }

  static SlackOptionsResponse emptyDialogOptions() {
    return SlackOptionsResponse
        .builder()
        .setDialogOptions(Collections.emptyList())
        .build();
  }

  static SlackOptionsResponse ofDialogOptions(
      List<SlackFormOption> dialogOptions
  ) {
    return SlackOptionsResponse.builder().setDialogOptions(dialogOptions).build();
  }

  static SlackOptionsResponse emptyDialogOptionGroups() {
    return SlackOptionsResponse
        .builder()
        .setDialogOptionGroups(Collections.emptyList())
        .build();
  }

  static SlackOptionsResponse ofDialogOptionGroups(
      List<SlackFormOptionGroup> optionGroups
  ) {
    return SlackOptionsResponse.builder().setDialogOptionGroups(optionGroups).build();
  }

  Optional<List<Option>> getAttachmentOptions();

  // attachment option groups aren't yet supported in the slack-client

  Optional<List<SlackFormOption>> getDialogOptions();

  Optional<List<SlackFormOptionGroup>> getDialogOptionGroups();

  @Derived
  @Nullable
   default Object getOptions() {
    return Stream.of(getAttachmentOptions(), getDialogOptions())
        .findFirst()
        .orElse(null)
        .orElse(null);
  }

  @Derived
  @Nullable
  default Object getOptionGroups() {
    return getDialogOptionGroups().orElse(null);
  }
}
