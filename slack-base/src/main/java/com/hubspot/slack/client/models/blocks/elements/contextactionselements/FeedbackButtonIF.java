package com.hubspot.slack.client.models.blocks.elements.contextactionselements;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface FeedbackButtonIF {
  int MAX_TEXT_LENGTH = 75;
  int MAX_VALUE_LENGTH = 2000;
  int MAX_ACCESSIBILITY_LABEL_LENGTH = 75;

  @Value.Parameter(order = 0)
  Text getText();

  @Value.Parameter(order = 1)
  String getValue();

  Optional<String> getAccessibilityLabel();

  @Check
  default void check() {
    Preconditions.checkState(
      getText().getType() == TextType.PLAIN_TEXT,
      "Feedback button text must be of type plain_text"
    );
    Preconditions.checkState(
      getText().getText().length() <= MAX_TEXT_LENGTH,
      "Feedback button text cannot exceed %s characters",
      MAX_TEXT_LENGTH
    );
    Preconditions.checkState(
      getValue().length() <= MAX_VALUE_LENGTH,
      "Feedback button value cannot exceed %s characters",
      MAX_VALUE_LENGTH
    );
    getAccessibilityLabel()
      .ifPresent(label ->
        Preconditions.checkState(
          label.length() <= MAX_ACCESSIBILITY_LABEL_LENGTH,
          "accessibility_label cannot exceed %s characters",
          MAX_ACCESSIBILITY_LABEL_LENGTH
        )
      );
  }
}
