package com.hubspot.slack.client.models;

// We need common interface to make unfurl call using any of - old or Block Kit framework.
// Having this we can construct ChatUnfurlParams for both cases.
public interface ChatUnfurlBlocksOrAttachment {
}
