package com.hubspot.slack.client.blockkit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

import com.google.common.base.Strings;
import com.hubspot.slack.client.blockkit.BlockKitPayloadTester.PayloadSendSummary;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Live, manual harness that sends Block Kit payload file(s) to a real channel and prints a summary.
 * Skipped (JUnit {@code Assume}) unless a token, channel, and payload path are supplied, so it never
 * runs in CI or a plain {@code mvn test}.
 *
 * <p>Point it at a single JSON file or a folder of {@code *.json} payloads:
 * <pre>{@code
 * mvn -pl slack-java-client test -Dtest=BlockKitPayloadFromFilesTest \
 *   -Dslack.test.token=xoxb-... -Dslack.test.channel=C0123456789 \
 *   -Dslack.test.payload=/abs/path/to/payloads_dir \
 *   -Dsurefire.failIfNoSpecifiedTests=false
 * }</pre>
 * (or set {@code SLACK_TEST_TOKEN} / {@code SLACK_TEST_CHANNEL} / {@code SLACK_TEST_PAYLOAD}).
 *
 * <p>Each payload file must be a JSON <b>array of blocks</b> (the same shape as the
 * {@code *_rich_text.json} test fixtures). The summary reports how many were sent and, for each
 * failure, the reason (e.g. {@code unknown rich text element type team}).
 */
public class BlockKitPayloadFromFilesTest {

  private static final Logger LOG = LoggerFactory.getLogger(
    BlockKitPayloadFromFilesTest.class
  );

  private String token;
  private String channelId;
  private String payloadPath;

  @Before
  public void setUp() {
    token = config("slack.test.token", "SLACK_TEST_TOKEN");
    channelId = config("slack.test.channel", "SLACK_TEST_CHANNEL");
    payloadPath = config("slack.test.payload", "SLACK_TEST_PAYLOAD");
    assumeTrue(
      "Set -Dslack.test.token, -Dslack.test.channel and -Dslack.test.payload (file or folder) " +
      "to run the file-driven Block Kit payload test",
      !Strings.isNullOrEmpty(token) &&
      !Strings.isNullOrEmpty(channelId) &&
      !Strings.isNullOrEmpty(payloadPath)
    );
  }

  @Test
  public void itSendsPayloadFileOrFolderAndPrintsSummary() throws Exception {
    Path path = Paths.get(payloadPath);
    assertThat(Files.exists(path)).as("payload path does not exist: %s", path).isTrue();

    try (BlockKitPayloadTester tester = new BlockKitPayloadTester(token, channelId)) {
      PayloadSendSummary summary = tester.postFromPath(path);

      LOG.info("Block Kit payload send results:\n{}", summary.render());

      assertThat(summary.getTotal())
        .as("expected at least one *.json payload under %s", path)
        .isGreaterThan(0);

      // Fail the test (and the build) if Slack rejected any payload, so a bad payload is not
      // silently buried in a green build. The assertion message carries the full summary with the
      // per-payload Slack error reasons.
      assertThat(summary.getFailedCount())
        .as(
          "Slack rejected %s of %s payload(s):%n%s",
          summary.getFailedCount(),
          summary.getTotal(),
          summary.render()
        )
        .isZero();
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
