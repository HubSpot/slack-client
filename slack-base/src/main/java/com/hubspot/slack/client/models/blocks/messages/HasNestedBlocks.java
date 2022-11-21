package com.hubspot.slack.client.models.blocks.messages;

import com.hubspot.slack.client.models.blocks.Block;

import java.util.List;

public interface HasNestedBlocks extends Block {
    List<Block> getElements();
}
