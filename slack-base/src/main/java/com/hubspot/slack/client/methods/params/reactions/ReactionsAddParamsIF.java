package com.hubspot.slack.client.methods.params.reactions;

import java.util.Optional;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface ReactionsAddParamsIF {
 String getName();
 /**
  * One of file, file_comment, or the combination of channel and timestamp must be specified.
  **/
 Optional<String> getChannel();
 Optional<String> getFile();
 Optional<String> getFileComment();
 Optional<String> getTimestamp();
}
