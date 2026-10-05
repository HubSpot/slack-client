# blockkit-payloads

An example Block Kit payload (`example_section.json`) for the `BlockKitPayloadFromFilesTest` /
`BlockKitPayloadTester` harness.

Each payload file is a **JSON array of blocks** — exactly the `blocks` array you'd pass to
`chat.postMessage`:

```json
[ { "type": "section", "text": { "type": "mrkdwn", "text": "hello" } } ]
```

## How to use

Run the harness and point it at a payload file or a folder of `*.json` payloads. Supply your own
bot token (needs `chat:write`) and the target channel id. Run from the repo root:

```bash
mvn -pl slack-java-client -am test \
  -Dtest=BlockKitPayloadFromFilesTest \
  -Dslack.test.token=xoxb-your-bot-token \
  -Dslack.test.channel=C0123456789 \
  -Dslack.test.payload="$(pwd)/slack-java-client/src/test/resources/blockkit-payloads/example_section.json" \
  -Dsurefire.failIfNoSpecifiedTests=false
```

- Use an **absolute** path for `-Dslack.test.payload` (the test runs with the module as its working
  directory, so a repo-relative path won't resolve — the `$(pwd)/...` above makes it absolute).
- `-Dslack.test.payload` may be a single file or a directory (all `*.json` in it are posted, sorted by name).
- Token/channel/payload can also be given as the `SLACK_TEST_TOKEN` / `SLACK_TEST_CHANNEL` /
  `SLACK_TEST_PAYLOAD` environment variables.
- `-am` is required so the harness compiles against the local `slack-base` (which has the in-progress
  elements); otherwise new element types deserialize to `unknown` and Slack rejects the payload.

The harness posts each payload and prints a summary of how many were sent, with the Slack error
detail for any failures.
