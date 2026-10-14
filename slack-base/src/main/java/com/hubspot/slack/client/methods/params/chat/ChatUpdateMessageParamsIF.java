package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.jackson.ChatUpdateMessageParamsSerializer;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
@JsonSerialize(using = ChatUpdateMessageParamsSerializer.class)
public interface ChatUpdateMessageParamsIF extends MessageParams {
  @JsonProperty("channel")
  String getChannelId();

  Optional<String> getText();

  String getTs();

  /**
   * IDs of already-uploaded files to attach to the message. Upload them without a channel
   * (files.completeUploadExternal with no channel_id) so Slack doesn't post a separate file
   * message. Slack replaces the message's blocks on update, so re-send the existing blocks too.
   */
  ImmutableList<String> getFileIds();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonProperty("as_user")
  Optional<Boolean> getAsUser();

  @Default
  @JsonProperty("link_names")
  default boolean shouldLinkNames() {
    return true;
  }

  @Default
  default String getParse() {
    return "none";
  }

  /**
   * When {@code true}, force the {@code attachments} field to serialize as an empty array
   * {@code []} even when there are no attachments, so a {@code chat.update} actively clears any
   * attachments on the target message. Left {@code false} (the default), an empty attachments list
   * is omitted and Slack keeps the message's existing attachments. Not part of the Slack payload —
   * it only drives serialization (see {@code ChatUpdateMessageParamsSerializerModifier}).
   */
  @JsonIgnore
  @Default
  default boolean shouldClearAttachments() {
    return false;
  }

  /**
   * When {@code true}, force the {@code blocks} field to serialize as an empty array {@code []}
   * even when there are no blocks, so a {@code chat.update} actively clears any blocks on the
   * target message. Left {@code false} (the default), an empty blocks list is omitted and Slack
   * keeps the message's existing blocks. Not part of the Slack payload — it only drives
   * serialization (see {@code ChatUpdateMessageParamsSerializerModifier}).
   */
  @JsonIgnore
  @Default
  default boolean shouldClearBlocks() {
    return false;
  }

  @Check
  default void check() {
    Preconditions.checkState(
      (getText().isPresent() && !Strings.isNullOrEmpty(getText().get())) ||
      !getAttachments().isEmpty() ||
      !getBlocks().isEmpty(),
      "Must include text if not providing attachments or blocks"
    );
  }
}
