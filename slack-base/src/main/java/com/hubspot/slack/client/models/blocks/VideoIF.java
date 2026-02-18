package com.hubspot.slack.client.models.blocks;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import java.util.Optional;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface VideoIF extends Block {
  String TYPE = "video";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * A tooltip for the video. Required for accessibility
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter(order = 0)
  String getAltText();

  /**
   * Author name to be displayed. Must be less than 50 characters.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getAuthorName();

  /**
   * Description for video in the form of a text object that must have type of plain_text. text within must be less than 200 characters.
   * <br/><br/>
   * Type: Object (Text)
   * <br/>
   * Required: false (preferred)
   */
  Optional<Text> getDescription();

  /**
   * Icon for the video provider, e.g. YouTube icon.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getProviderIconUrl();

  /**
   * The originating application or domain of the video, e.g. YouTube
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getProviderName();

  /**
   * Video title in the form of a text object that must have type of plain_text. text within must be less than 200 characters.
   * <br/><br/>
   * Type: Object (Text)
   * <br/>
   * Required: true
   */
  @Parameter(order = 1)
  Text getTitle();

  /**
   * Hyperlink for the title text. Must correspond to the non-embeddable URL for the video. Must go to an HTTPS URL.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false (preferred)
   */
  Optional<String> getTitleUrl();

  /**
   * The thumbnail image URL
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter(order = 2)
  String getThumbnailUrl();

  /**
   * The URL to be embedded. Must match any existing unfurl domains within the app and point to a HTTPS URL.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter(order = 3)
  String getVideoUrl();

  @Check
  default void check() {
    getAuthorName()
      .ifPresent(name ->
        Preconditions.checkState(
          name.length() <
          BlockElementLengthLimits.MAX_VIDEO_AUTHOR_NAME_LENGTH.getLimit(),
          "Author name must be less than " +
          BlockElementLengthLimits.MAX_VIDEO_AUTHOR_NAME_LENGTH.getLimit() +
          " characters"
        )
      );
    getDescription()
      .ifPresent(description -> {
        Preconditions.checkState(
          description.getType() == TextType.PLAIN_TEXT,
          "The description type of a Video block must be plain_text"
        );
        Preconditions.checkState(
          description.getText().length() <
          BlockElementLengthLimits.MAX_VIDEO_DESCRIPTION_LENGTH.getLimit(),
          "Description length must be less than " +
          BlockElementLengthLimits.MAX_VIDEO_DESCRIPTION_LENGTH.getLimit() +
          " characters"
        );
      });

    Preconditions.checkState(
      getTitle().getType() == TextType.PLAIN_TEXT,
      "The title type of a Video block must be plain_text"
    );
    Preconditions.checkState(
      getTitle().getText().length() <
      BlockElementLengthLimits.MAX_VIDEO_DESCRIPTION_LENGTH.getLimit(),
      "Title length must be less than " +
      BlockElementLengthLimits.MAX_VIDEO_DESCRIPTION_LENGTH.getLimit() +
      " characters"
    );

    getTitleUrl()
      .ifPresent(url ->
        Preconditions.checkState(
          url.toLowerCase().startsWith("https://"),
          "Title url must go to a HTTPS URL"
        )
      );

    Preconditions.checkState(
      getVideoUrl().toLowerCase().startsWith("https://"),
      "Video url must go to a HTTPS URL"
    );
  }
}
