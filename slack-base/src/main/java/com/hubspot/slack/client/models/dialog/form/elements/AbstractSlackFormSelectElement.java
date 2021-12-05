package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.actions.SlackDataSource;
import com.hubspot.slack.client.models.dialog.form.SlackFormElementTypes;
import com.hubspot.slack.client.models.dialog.form.elements.helper.SlackDialogElementNormalizer;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(Include.NON_EMPTY)
public abstract class AbstractSlackFormSelectElement extends SlackDialogFormElement {
  @Default
  @Override
  public SlackFormElementTypes getType() {
    return SlackFormElementTypes.SELECT;
  }

  @Default
  public SlackDataSource getDataSource() {
    return SlackDataSource.STATIC;
  }

  public abstract List<SlackFormOption> getOptions();
  public abstract List<SlackFormOptionGroup> getOptionGroups();
  public abstract Optional<String> getValue();
  public abstract List<SlackFormOption> getSelectedOptions();
  public abstract Optional<Integer> getMinQueryLength();

  @Check
  public AbstractSlackFormSelectElement validate() {
    AbstractSlackFormSelectElement normalized = SlackDialogElementNormalizer.normalize(this);
    super.validateBaseElementProperties(normalized);
    List<String> errors = new ArrayList<>();

    List<SlackFormOption> normalizedOptions = normalized.getOptions();
    int numOptions = normalizedOptions.size();
    List<SlackFormOptionGroup> normalizedOptionGroups = normalized.getOptionGroups();
    int numOptionGroups = normalizedOptionGroups.size();

    if (numOptions > SlackDialogFormElementLengthLimits.MAX_OPTIONS_NUMBER.getLimit()) {
      errors.add("Cannot have more than 100 options");
    }

    if (numOptionGroups > SlackDialogFormElementLengthLimits.MAX_OPTION_GROUPS_NUMBER.getLimit()) {
      errors.add("Cannot have more than 100 option groups");
    }

    if (normalized.getDataSource().equals(SlackDataSource.STATIC)) {
      if (numOptions == 0 && numOptionGroups == 0) {
        errors.add("Either options or option groups are required for static data source types");
      }
    }

    Optional<String> normalizedValue = normalized.getValue();
    if (normalizedValue.isPresent() && normalized.getDataSource().equals(SlackDataSource.EXTERNAL)) {
      errors.add("Cannot use value for external data source, must use selected options");
    }

    if (normalizedValue.isPresent()) {
      boolean valueIsSomeOptionValue = getOptions().stream()
          .anyMatch(option -> option.getValue().equalsIgnoreCase(normalizedValue.get()));
      if (!valueIsSomeOptionValue) {
        errors.add("Value must exactly match the value field for one provided option");
      }
    }

    List<SlackFormOption> normalizedSelectedOptions = normalized.getSelectedOptions();
    if (!normalizedSelectedOptions.isEmpty()) {
      if (normalizedSelectedOptions.size() != 1) {
        errors.add("Selected options must be a single element array");
      }
      if (!normalizedOptionGroups.isEmpty()) {
        boolean selectedOptionIsInOptionsGroup = normalizedOptionGroups.stream()
            .map(SlackFormOptionGroup::getOptions)
            .collect(Collectors.toList())
            .stream()
            .flatMap(List::stream)
            .anyMatch(option -> option.equals(normalizedSelectedOptions.get(0)));
        if (!selectedOptionIsInOptionsGroup) {
          errors.add("Selected option must exactly match an option in the provided options groups");
        }
      } else if (!normalizedOptions.isEmpty()) {
        boolean selectedOptionIsInOptions = normalizedOptions.stream()
            .anyMatch(option -> option.equals(normalizedSelectedOptions.get(0)));
        if (!selectedOptionIsInOptions) {
          errors.add("Selected option must exactly match an option in the provided options");
        }
      }
    }

    if (!errors.isEmpty()) {
      throw new IllegalStateException(errors.toString());
    }
    return normalized;
  }
}
