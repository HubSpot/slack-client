package com.hubspot.slack.client.models.interaction;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Map;
import java.util.Optional;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface DialogSubmissionIF extends SlackInteractiveCallback {
  /**
   * This map is a key:value mapping of the values the user submitted to the dialog,
   * where the keys are the field names used when creating the dialog.
   */
  Map<String, Optional<String>> getSubmission();

  /**
   * This state is passed from the dialog creation and is echoed back from the user submission.
   */
  Optional<String> getState();
}
