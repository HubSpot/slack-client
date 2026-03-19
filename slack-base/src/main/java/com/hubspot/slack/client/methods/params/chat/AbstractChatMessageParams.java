package com.hubspot.slack.client.methods.params.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hubspot.slack.client.models.Metadata;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;

@JsonNaming(SnakeCaseStrategy.class)
public abstract class AbstractChatMessageParams implements MessageParams {

  @JsonProperty("channel")
  public abstract String getChannelId();

  public abstract Optional<String> getText();

  public abstract Optional<String> getThreadTs();

  public abstract Optional<String> getUsername();

  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  public abstract Optional<Boolean> getAsUser();

  public abstract Optional<String> getIconEmoji();

  public abstract Optional<String> getIconUrl();

  public abstract Optional<Boolean> getLinkNames();

  public abstract Optional<Boolean> getUnfurlLinks();

  public abstract Optional<Boolean> getUnfurlMedia();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public abstract Optional<Boolean> getReplyBroadcast();

  public abstract Optional<String> getParse();

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public abstract Optional<Metadata> getMetadata();

  @Default
  public Optional<Boolean> getMrkdwn() {
    return Optional.of(true);
  }

  @Check
  public void check() {
    MessageParams.super.check();
    Preconditions.checkState(
      (getText().isPresent() && !Strings.isNullOrEmpty(getText().get())) ||
      !getAttachments().isEmpty() ||
      !getBlocks().isEmpty(),
      "Must include text if not providing attachments or blocks"
    );
  }
}
