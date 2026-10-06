package com.hubspot.slack.client.models.blocks;

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

/**
 * Slack's video block embeds a video player in a message, modal, or home tab.
 * Sending one requires the {@code links.embed:write} scope and that {@code video_url} matches one of
 * the app's configured unfurl domains and points to an embeddable HTTPS URL.
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/video-block">Video block</a>
 */
@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface VideoBlockIF extends Block {
  String TYPE = "video";
  String HTTPS_PREFIX = "https://";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  String getAltText();

  Text getTitle();

  String getThumbnailUrl();

  String getVideoUrl();

  Optional<String> getTitleUrl();

  Optional<Text> getDescription();

  Optional<String> getProviderName();

  Optional<String> getProviderIconUrl();

  Optional<String> getAuthorName();

  @Check
  default void check() {
    Preconditions.checkState(
      getTitle().getType() == TextType.PLAIN_TEXT,
      "video title must be a plain_text text object"
    );
    Preconditions.checkState(
      getTitle().getText().length() < 200,
      "video title must be less than 200 characters"
    );
    getDescription()
      .ifPresent(description -> {
        Preconditions.checkState(
          description.getType() == TextType.PLAIN_TEXT,
          "video description must be a plain_text text object"
        );
        Preconditions.checkState(
          description.getText().length() < 200,
          "video description must be less than 200 characters"
        );
      });
    getAuthorName()
      .ifPresent(authorName ->
        Preconditions.checkState(
          authorName.length() < 50,
          "video author_name must be less than 50 characters"
        )
      );
    Preconditions.checkState(
      getVideoUrl().startsWith(HTTPS_PREFIX),
      "video video_url must be an HTTPS URL"
    );
    getTitleUrl()
      .ifPresent(titleUrl ->
        Preconditions.checkState(
          titleUrl.startsWith(HTTPS_PREFIX),
          "video title_url must be an HTTPS URL"
        )
      );
  }
}
