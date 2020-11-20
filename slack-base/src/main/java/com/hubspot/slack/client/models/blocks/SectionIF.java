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
import javax.annotation.Nullable;
import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
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

  @Default
  @Nullable
  @Value.Parameter
  default Text getText() {
    return null;
  }

  List<Text> getFields();

  Optional<BlockElement> getAccessory();

  @Check
  default void check() {
    boolean hasNonEmptyTextField =
      getText() != null && !Strings.isNullOrEmpty(getText().getText());
    boolean hasFields = !getFields().isEmpty();
    Preconditions.checkState(
        hasNonEmptyTextField || hasFields,
        "Must include text if not providing a list of fields"
    );
    boolean isTextLengthValid = getText().getText().length() <= 3000;
    Preconditions.checkState(isTextLengthValid, "The text length of a Section block cannot exceed 3000 characters");
  }
}
