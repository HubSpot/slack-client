package com.hubspot.slack.client.methods.params.files;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface CompleteUploadExternalParamsIF {
  List<FileInfo> getFiles();
  Optional<String> getChannelId();
  Optional<String> getThreadTs();
  Optional<String> getInitialComment();
}
