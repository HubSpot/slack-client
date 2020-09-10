package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.elements.BlockElement;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface SectionIF extends Block {
  String TYPE = "section";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  @Value.Parameter
  Optional<Text> getText();

  List<Text> getFields();

  Optional<BlockElement> getAccessory();

  @Check
  default void check() {
    boolean hasNonEmptyTextField =
      getText().isPresent() && !Strings.isNullOrEmpty(getText().get().getText());
    boolean hasFields = !getFields().isEmpty();
    Preconditions.checkState(
      hasNonEmptyTextField || hasFields,
      "Must include text if not providing a list of fields"
    );
  }
}
