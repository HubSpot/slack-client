package com.hubspot.slack.client.models.response.files;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.files.SlackFile;
import com.hubspot.slack.client.models.response.SlackResponse;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface CompleteUploadExternalResponseIF extends SlackResponse {
  List<SlackFile> getFiles();
}
