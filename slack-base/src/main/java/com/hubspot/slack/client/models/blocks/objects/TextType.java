package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TextType {
  @JsonProperty("plain_text")
  PLAIN_TEXT,

  @JsonProperty("mrkdwn")
  MARKDOWN,
}
