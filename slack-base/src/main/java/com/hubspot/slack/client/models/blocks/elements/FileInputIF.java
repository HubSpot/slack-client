package com.hubspot.slack.client.models.blocks.elements;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * Slack's file input element lets a user upload files. Works with the {@code input} block.
 * Available on the Modals surface. Requires the {@code files:read} scope; 100MB per-file limit.
 * @see <a href="https://docs.slack.dev/reference/block-kit/block-elements/file-input-element">File input element</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface FileInputIF extends BlockElement {
  String TYPE = "file_input";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  Optional<String> getActionId();

  /** Valid file extensions accepted; all extensions accepted if empty. */
  List<String> getFiletypes();

  /** Max number of files, between 1 and 10 (defaults to 10). */
  Optional<Integer> getMaxFiles();
}
