package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hubspot.slack.client.models.blocks.Block;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface MessageBlock extends Block {
}
