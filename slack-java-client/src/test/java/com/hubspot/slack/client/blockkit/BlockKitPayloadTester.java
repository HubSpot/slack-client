package com.hubspot.slack.client.blockkit;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Stopwatch;
import com.google.common.io.Resources;
import com.hubspot.algebra.Result;
import com.hubspot.horizon.HttpRequest;
import com.hubspot.horizon.HttpResponse;
import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.SlackClientFactory;
import com.hubspot.slack.client.SlackClientRuntimeConfig;
import com.hubspot.slack.client.interceptors.http.ResponseDebugger;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.methods.SlackMethod;
import com.hubspot.slack.client.methods.params.chat.ChatPostMessageParams;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.response.SlackError;
import com.hubspot.slack.client.models.response.chat.ChatPostMessageResponse;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Test-only harness for <b>runtime-validating</b> Block Kit payloads against the real Slack API.
 *
 * <p>Our (de)serialization round-trip tests only prove that {@code slack-client} can marshal a
 * block/element to and from JSON — they do <i>not</i> prove that Slack actually accepts and renders
 * it (see SISLACK-738 implementation rules, Rule 8). Some elements are documented but rejected at
 * runtime, e.g. Slack returns {@code "unknown rich text element type team"} for the {@code team}
 * element. This service closes that gap: give it a token + channel, hand it a blocks JSON payload
 * (or pre-built {@link Block} objects), and it posts a real message and returns the raw Slack
 * {@link Result} so a test can assert acceptance or inspect the rejection error.
 *
 * <p>Typical use (the Java object is built <i>from the JSON</i>, as required):
 * <pre>{@code
 * try (BlockKitPayloadTester tester = new BlockKitPayloadTester(token, channelId)) {
 *   List<Block> blocks = BlockKitPayloadTester.blocksFromJson(payloadJson);
 *   Result<ChatPostMessageResponse, SlackError> result = tester.postBlocks(blocks);
 *   assertThat(result.isOk()).isTrue();
 * }
 * }</pre>
 */
public class BlockKitPayloadTester implements Closeable {

  private static final String DEFAULT_FALLBACK_TEXT = "Block Kit payload test";

  private final SlackClient slackClient;
  private final String channelId;
  private final boolean ownsClient;
  private final CapturingResponseDebugger errorCapture;

  /**
   * Builds a {@link SlackClient} from the given token and targets the given channel.
   *
   * @param token a Slack token with {@code chat:write} (bot {@code xoxb-} or user {@code xoxp-})
   * @param channelId the channel/conversation ID to post into (the token must be able to post there)
   */
  public BlockKitPayloadTester(String token, String channelId) {
    this.errorCapture = new CapturingResponseDebugger();
    this.slackClient =
      SlackClientFactory
        .defaultFactory()
        .create(
          SlackClientRuntimeConfig
            .builder()
            .setTokenSupplier(() -> token)
            .setResponseDebugger(errorCapture)
            .build()
        );
    this.channelId = channelId;
    this.ownsClient = true;
  }

  /**
   * Uses a caller-supplied client (e.g. with custom config); caller retains ownership/closing.
   * Note: detailed failure reasons from {@code response_metadata.messages} are only captured when
   * this class builds the client itself (the other constructor) — a caller-supplied client would
   * need its own {@code ResponseDebugger} for that detail.
   */
  public BlockKitPayloadTester(SlackClient slackClient, String channelId) {
    this.slackClient = slackClient;
    this.channelId = channelId;
    this.ownsClient = false;
    this.errorCapture = null;
  }

  /**
   * Deserializes a JSON <b>array of blocks</b> into {@code slack-client} {@link Block} objects,
   * using the same {@link ObjectMapperUtils#mapper()} the library itself uses.
   */
  public static List<Block> blocksFromJson(String blocksJson) {
    try {
      return Arrays.asList(
        ObjectMapperUtils.mapper().readValue(blocksJson, Block[].class)
      );
    } catch (IOException e) {
      throw new RuntimeException("Could not parse blocks JSON into Block[]", e);
    }
  }

  /** Loads a classpath resource (a JSON array of blocks) and deserializes it. */
  public static List<Block> blocksFromResource(String resourceName) {
    try {
      String json = Resources.toString(
        Resources.getResource(resourceName),
        StandardCharsets.UTF_8
      );
      return blocksFromJson(json);
    } catch (IOException e) {
      throw new RuntimeException("Could not read blocks resource " + resourceName, e);
    }
  }

  /** Posts pre-built blocks with the default fallback text. */
  public Result<ChatPostMessageResponse, SlackError> postBlocks(List<Block> blocks) {
    return postBlocks(blocks, DEFAULT_FALLBACK_TEXT);
  }

  /**
   * Posts pre-built blocks and returns the raw Slack result.
   *
   * @return {@code ok} if Slack accepted the payload, {@code err} (with the Slack error string)
   *     if it was rejected — e.g. {@code invalid_blocks} / {@code unknown rich text element type ...}
   */
  public Result<ChatPostMessageResponse, SlackError> postBlocks(
    List<Block> blocks,
    String fallbackText
  ) {
    if (errorCapture != null) {
      errorCapture.reset();
    }
    return slackClient
      .postMessage(
        ChatPostMessageParams
          .builder()
          .setChannelId(channelId)
          .setText(fallbackText)
          .setBlocks(blocks)
          .build()
      )
      .join();
  }

  /**
   * The detailed Slack error messages (from {@code response_metadata.messages}) for the most recent
   * failed post, when available. Slack's top-level error is often just {@code invalid_blocks}; the
   * actionable reason (e.g. {@code unknown rich text element type team}) lives here.
   */
  public Optional<String> getLastErrorDetail() {
    return errorCapture == null ? Optional.empty() : errorCapture.getDetail();
  }

  /** Convenience: build blocks from a JSON array string and post them. */
  public Result<ChatPostMessageResponse, SlackError> postBlocksJson(String blocksJson) {
    return postBlocks(blocksFromJson(blocksJson));
  }

  /**
   * Reads a single JSON payload file (a JSON array of blocks), deserializes it, and posts it.
   * Never throws for an individual payload — any read / parse / Slack-rejection problem is captured
   * as a {@link PayloadSendResult} carrying the failure reason, so it reads the same whether used
   * standalone or from {@link #postFromFolder(Path)}.
   */
  public PayloadSendResult postFromFile(Path jsonFile) {
    return postFile(jsonFile, jsonFile.getFileName().toString());
  }

  private PayloadSendResult postFile(Path jsonFile, String displayName) {
    String json;
    try {
      json = new String(Files.readAllBytes(jsonFile), StandardCharsets.UTF_8);
    } catch (IOException e) {
      return PayloadSendResult.failure(
        displayName,
        "Could not read file: " + describe(e)
      );
    }

    List<Block> blocks;
    try {
      blocks = blocksFromJson(json);
    } catch (RuntimeException e) {
      return PayloadSendResult.failure(
        displayName,
        "Could not parse/deserialize payload: " + describe(e)
      );
    }

    try {
      Result<ChatPostMessageResponse, SlackError> result = postBlocks(
        blocks,
        "Block Kit payload test: " + displayName
      );
      if (result.isOk()) {
        return PayloadSendResult.success(displayName, result.unwrapOrElseThrow().getTs());
      }
      return PayloadSendResult.failure(
        displayName,
        reasonFor(result.unwrapErrOrElseThrow())
      );
    } catch (RuntimeException e) {
      return PayloadSendResult.failure(displayName, "Post failed: " + describe(e));
    }
  }

  /**
   * Builds a human-readable failure reason: Slack's top-level error, enriched with the detailed
   * {@code response_metadata.messages} when the client captured them (see {@link #getLastErrorDetail()}).
   */
  private String reasonFor(SlackError error) {
    String base = error.getError();
    return getLastErrorDetail().map(detail -> base + " — " + detail).orElse(base);
  }

  /**
   * Posts every {@code *.json} payload found under the given folder, recursively (so per-element
   * subfolders are included), sorted by relative path. Each result is labelled with its path
   * relative to {@code folder} (e.g. {@code tag/full.json}). Returns an aggregate summary.
   */
  public PayloadSendSummary postFromFolder(Path folder) {
    List<Path> files;
    try (Stream<Path> stream = Files.walk(folder)) {
      files =
        stream
          .filter(Files::isRegularFile)
          .filter(p -> p.getFileName().toString().toLowerCase().endsWith(".json"))
          .sorted()
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("Could not walk folder " + folder, e);
    }

    List<PayloadSendResult> results = new ArrayList<>();
    for (Path file : files) {
      results.add(postFile(file, folder.relativize(file).toString()));
    }
    return new PayloadSendSummary(results);
  }

  /**
   * Convenience: if {@code path} is a directory, behaves like {@link #postFromFolder(Path)};
   * otherwise posts the single file and wraps the one result in a summary.
   */
  public PayloadSendSummary postFromPath(Path path) {
    if (Files.isDirectory(path)) {
      return postFromFolder(path);
    }
    return new PayloadSendSummary(Collections.singletonList(postFromFile(path)));
  }

  private static String describe(Throwable t) {
    Throwable root = t;
    while (root.getCause() != null && root.getCause() != root) {
      root = root.getCause();
    }
    String message = root.getMessage();
    return message == null || message.isEmpty()
      ? root.getClass().getSimpleName()
      : message;
  }

  @Override
  public void close() throws IOException {
    if (ownsClient) {
      slackClient.close();
    }
  }

  /**
   * Captures the detailed error messages Slack returns under {@code response_metadata.messages} on a
   * failed API call, which the {@code slack-client} {@link SlackError} model otherwise discards
   * (it keeps only the top-level {@code error}, e.g. {@code invalid_blocks}).
   */
  private static final class CapturingResponseDebugger implements ResponseDebugger {

    private final AtomicReference<String> lastDetail = new AtomicReference<>();

    void reset() {
      lastDetail.set(null);
    }

    Optional<String> getDetail() {
      return Optional.ofNullable(lastDetail.get());
    }

    @Override
    public void debugSlackApiError(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      HttpResponse failure
    ) {
      String detail = extractMessages(failure);
      if (detail != null) {
        lastDetail.set(detail);
      }
    }

    @Override
    public void debugProcessingFailure(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      HttpResponse response,
      Throwable ex
    ) {
      String detail = extractMessages(response);
      if (detail != null) {
        lastDetail.set(detail);
      }
    }

    @Override
    public void debug(
      long requestId,
      SlackMethod method,
      Stopwatch timer,
      HttpRequest request,
      HttpResponse response
    ) {}

    @Override
    public void debugTransportException(
      long requestId,
      SlackMethod method,
      HttpRequest request,
      Throwable exception
    ) {}

    @Override
    public void debugProactiveRateLimit(
      long requestId,
      SlackMethod method,
      HttpRequest request
    ) {}

    private static String extractMessages(HttpResponse response) {
      try {
        JsonNode root = response.getAsJsonNode();
        JsonNode messages = root.path("response_metadata").path("messages");
        if (messages.isArray() && messages.size() > 0) {
          List<String> parts = new ArrayList<>();
          messages.forEach(node -> parts.add(node.asText()));
          return String.join("; ", parts);
        }
        JsonNode warning = root.path("warning");
        if (warning.isTextual() && !warning.asText().isEmpty()) {
          return warning.asText();
        }
        return null;
      } catch (RuntimeException e) {
        return null;
      }
    }
  }

  /** Outcome of sending one payload file: either success (with the message ts) or failure (reason). */
  public static final class PayloadSendResult {

    private final String fileName;
    private final boolean success;
    private final String detail;

    private PayloadSendResult(String fileName, boolean success, String detail) {
      this.fileName = fileName;
      this.success = success;
      this.detail = detail;
    }

    static PayloadSendResult success(String fileName, String messageTs) {
      return new PayloadSendResult(fileName, true, messageTs);
    }

    static PayloadSendResult failure(String fileName, String reason) {
      return new PayloadSendResult(fileName, false, reason);
    }

    public String getFileName() {
      return fileName;
    }

    public boolean isSuccess() {
      return success;
    }

    /** The posted message's {@code ts}, present only when {@link #isSuccess()}. */
    public Optional<String> getMessageTs() {
      return success ? Optional.of(detail) : Optional.empty();
    }

    /** The failure reason, present only when the send failed. */
    public Optional<String> getError() {
      return success ? Optional.empty() : Optional.of(detail);
    }

    @Override
    public String toString() {
      return success
        ? "OK   " + fileName + " (ts=" + detail + ")"
        : "FAIL " + fileName + " — " + detail;
    }
  }

  /** Aggregate of a batch of {@link PayloadSendResult}s with a human-readable {@link #render()}. */
  public static final class PayloadSendSummary {

    private final List<PayloadSendResult> results;

    PayloadSendSummary(List<PayloadSendResult> results) {
      this.results = Collections.unmodifiableList(new ArrayList<>(results));
    }

    public List<PayloadSendResult> getResults() {
      return results;
    }

    public int getTotal() {
      return results.size();
    }

    public int getSentCount() {
      return Math.toIntExact(
        results.stream().filter(PayloadSendResult::isSuccess).count()
      );
    }

    public int getFailedCount() {
      return getTotal() - getSentCount();
    }

    public List<PayloadSendResult> getFailures() {
      return results.stream().filter(r -> !r.isSuccess()).collect(Collectors.toList());
    }

    /** Multi-line summary: a header count line, then one line per payload (sent first, then failures). */
    public String render() {
      StringBuilder sb = new StringBuilder();
      sb
        .append("Block Kit payload send summary: ")
        .append(getTotal())
        .append(" total, ")
        .append(getSentCount())
        .append(" sent, ")
        .append(getFailedCount())
        .append(" failed");
      results
        .stream()
        .filter(PayloadSendResult::isSuccess)
        .forEach(r ->
          sb
            .append("\n  ✅ ")
            .append(r.getFileName())
            .append(" (ts=")
            .append(r.getMessageTs().orElse(""))
            .append(")")
        );
      for (PayloadSendResult failure : getFailures()) {
        sb
          .append("\n  ❌ ")
          .append(failure.getFileName())
          .append(" — ")
          .append(failure.getError().orElse("unknown error"));
      }
      return sb.toString();
    }
  }
}
