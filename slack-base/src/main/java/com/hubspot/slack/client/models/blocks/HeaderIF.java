package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface HeaderIF extends Block {
  String TYPE = "header";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  Text getText();

  @Check
  default void check() {
    boolean isTextTypeValid = getText().getType() == TextType.PLAIN_TEXT;
    Preconditions.checkState(
      isTextTypeValid,
      "The text type of a Header block must be plain_text"
    );

    boolean isTextLengthValid = getText().getText().length() <= 3000;
    Preconditions.checkState(
      isTextLengthValid,
      "The text length of a Header block cannot exceed 3000 characters"
    );
  }
}
