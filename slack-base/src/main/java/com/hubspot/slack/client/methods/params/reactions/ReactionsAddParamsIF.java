package com.hubspot.slack.client.methods.params.reactions;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.immutables.validation.ImmutableConditions;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ReactionsAddParamsIF {
  String getName();
  Optional<String> getChannel();
  Optional<String> getTimestamp();
  Optional<String> getFile();
  Optional<String> getFileComment();

  @Value.Check
  default void reactionRecipientExists() {
    String err = "One of file, fileComment, or the combination of channel and timestamp must be specified";
    ImmutableConditions.checkValid((getChannel().isPresent() && getTimestamp().isPresent())
        || getFile().isPresent()
        || getFileComment().isPresent(), err);
  }
}
