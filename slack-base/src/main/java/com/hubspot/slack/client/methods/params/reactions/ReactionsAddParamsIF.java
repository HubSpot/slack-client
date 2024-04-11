package com.hubspot.slack.client.methods.params.reactions;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.immutables.validation.ImmutableConditions;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ReactionsAddParamsIF {
  String getName();

  Optional<String> getChannel();

  Optional<String> getTimestamp();

  /**
   * @deprecated As of 22nd August 2019, Slack has removed support for file comments -
   * <a href="https://api.slack.com/changelog/2018-05-file-threads-soon-tread#whats_changed">
   * file threads are the replacement
   * </a>
   */
  @Deprecated
  Optional<String> getFile();

  /**
   * @deprecated As of 22nd August 2019, Slack has removed support for file comments -
   * <a href="https://api.slack.com/changelog/2018-05-file-threads-soon-tread#whats_changed">
   * file threads are the replacement
   * </a>
   */
  @Deprecated
  Optional<String> getFileComment();

  @Value.Check
  default void reactionRecipientExists() {
    String err =
      "One of file, fileComment, or the combination of channel and timestamp must be specified";
    ImmutableConditions.checkValid(
      (getChannel().isPresent() && getTimestamp().isPresent()) ||
      getFile().isPresent() ||
      getFileComment().isPresent(),
      err
    );
  }
}
