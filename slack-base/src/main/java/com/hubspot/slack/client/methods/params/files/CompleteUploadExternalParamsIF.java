package com.hubspot.slack.client.methods.params.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value.Immutable;

import java.util.List;
import java.util.Optional;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface CompleteUploadExternalParamsIF {
  List<FileIInfo> getFiles();
  Optional<String> getChannelId();
  Optional<String> getThreadTs();
  Optional<String> getInitialComment();
}
