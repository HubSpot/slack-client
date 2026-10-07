package com.hubspot.slack.client.blockkit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import com.google.common.base.Strings;
import com.hubspot.algebra.Result;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Live, manual runtime-validation of Block Kit payloads against the real Slack API via
 * {@link BlockKitPayloadTester}. Skipped automatically (JUnit {@code Assume}) unless a token and
 * channel are supplied, so it never runs in CI or a plain {@code mvn test}.
 *
 * <p>Run it yourself with:
 * <pre>{@code
 * mvn -pl slack-java-client test -Dtest=BlockKitPayloadTesterLiveTest \
 *   -Dslack.test.token=xoxb-... -Dslack.test.channel=C0123456789
 * }</pre>
 * (or set {@code SLACK_TEST_TOKEN} / {@code SLACK_TEST_CHANNEL} environment variables).
 */
public class BlockKitPayloadTesterLiveTest {

  private static final Logger LOG = LoggerFactory.getLogger(
    BlockKitPayloadTesterLiveTest.class
  );

  private String token;
  private String channelId;

  @Before
  public void setUp() {
    token = config("slack.test.token", "SLACK_TEST_TOKEN");
    channelId = config("slack.test.channel", "SLACK_TEST_CHANNEL");
    assumeTrue(
      "Set -Dslack.test.token and -Dslack.test.channel (or SLACK_TEST_TOKEN / " +
      "SLACK_TEST_CHANNEL) to run the live Block Kit payload tests",
      !Strings.isNullOrEmpty(token) && !Strings.isNullOrEmpty(channelId)
    );
  }

  /** Sanity check: a trivially-valid payload is accepted — proves the harness itself works. */
  @Test
  public void itPostsAKnownGoodSectionPayload() throws Exception {
    String payloadJson =
      "[{\"type\":\"section\",\"text\":{\"type\":\"mrkdwn\"," +
      "\"text\":\"BlockKitPayloadTester :white_check_mark: known-good payload\"}}]";

    try (BlockKitPayloadTester tester = new BlockKitPayloadTester(token, channelId)) {
      Result<ChatPostMessageResponse, SlackError> result = tester.postBlocksJson(
        payloadJson
      );
      LOG.info("known-good section result: {}", result);
      assertThat(result.isOk())
        .as("Slack should accept a simple section payload")
        .isTrue();
    }
  }

  /**
   * Documents the observed runtime rejection of the documented-but-unsupported {@code team}
   * element (Slack returns {@code "unknown rich text element type team"}). If this ever starts
   * passing, Slack has shipped support and the WARNING on {@code RichTextTeamElementIF} /
   * {@code block-kit-elements.md} should be removed.
   */
  @Test
  public void itReportsSlackRejectionForTeamElement() throws Exception {
    String payloadJson =
      "[{\"type\":\"rich_text\",\"elements\":[{\"type\":\"rich_text_section\"," +
      "\"elements\":[{\"type\":\"team\",\"team_id\":\"T123ABC456\"}]}]}]";

    try (BlockKitPayloadTester tester = new BlockKitPayloadTester(token, channelId)) {
      Result<ChatPostMessageResponse, SlackError> result = tester.postBlocksJson(
        payloadJson
      );
      LOG.info("team-element result: {}", result);
      assertThat(result.isErr())
        .as("Slack is expected to reject the documented-but-unsupported `team` element")
        .isTrue();
      LOG.warn(
        "Slack rejected `team` element as expected: {}",
        result.unwrapErrOrElseThrow().getError()
      );
    }
  }

  private static String config(String sysProp, String envVar) {
    String fromProp = System.getProperty(sysProp);
    if (!Strings.isNullOrEmpty(fromProp)) {
      return fromProp;
    }
    return System.getenv(envVar);
  }
}
