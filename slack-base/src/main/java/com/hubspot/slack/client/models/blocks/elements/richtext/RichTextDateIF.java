package com.hubspot.slack.client.models.blocks.elements.richtext;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import java.util.Optional;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Parameter;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface RichTextDateIF extends RichTextElement {
  String TYPE = "date";

  @Override
  default String getType() {
    return TYPE;
  }

  /**
   * A Unix timestamp for the date to be displayed in seconds.
   * <br/><br/>
   * Type: Number
   * <br/>
   * Required: true
   */
  @Parameter(order = 0)
  long getTimestamp();

  /**
   * A template string containing curly-brace-enclosed tokens to substitute your provided timestamp.
   * <ul>
   * <li><code>{day_divider_pretty}</code>: Shows today, yesterday or tomorrow if applicable. Otherwise, if the date is in current year, uses the {date_long} format without the year. Otherwise, falls back to using the {date_long} format.</li>
   * <li><code>{date_num}</code>: Shows date as YYYY-MM-DD.</li>
   * <li><code>{date_slash}</code>: Shows date as DD/MM/YYYY (subject to locale preferences).</li>
   * <li><code>{date_long}</code>: Shows date as a long-form sentence including day-of-week, e.g. Monday, December 23rd, 2013.</li>
   * <li><code>{date_long_full}</code>: Shows date as a long-form sentence without day-of-week, e.g. August 9, 2020.</li>
   * <li><code>{date_long_pretty}</code>: Shows yesterday, today or tomorrow, otherwise uses the {date_long} format.</li>
   * <li><code>{date}</code>: Same as {date_long_full} but without the year.</li>
   * <li><code>{date_pretty}</code>: Shows today, yesterday or tomorrow if applicable, otherwise uses the {date} format.</li>
   * <li><code>{date_short}</code>: Shows date using short month names without day-of-week, e.g. Aug 9, 2020.</li>
   * <li><code>{date_short_pretty}</code>: Shows today, yesterday or tomorrow if applicable, otherwise uses the {date_short} format.</li>
   * <li><code>{time}</code>: Depending on user preferences, shows just the time-of-day portion of the timestamp using either 12 or 24 hour clock formats, e.g. 2:34 PM or 14:34.</li>
   * <li><code>{time_secs}</code>: Depending on user preferences, shows just the time-of-day portion of the timestamp using either 12 or 24 hour clock formats, including seconds, e.g. 2:34:56 PM or 14:34:56.</li>
   * <li><code>{ago}</code>: A human-readable period of time, e.g. 3 minutes ago, 4 hours ago, 2 days ago.</li>
   * </ul>
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: true
   */
  @Parameter(order = 1)
  String getFormat();

  /**
   * URL to link the entire format string to.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getUrl();

  /**
   * Text to display in place of the date should parsing, formatting or displaying fail.
   * <br/><br/>
   * Type: String
   * <br/>
   * Required: false
   */
  Optional<String> getFallback();
}
