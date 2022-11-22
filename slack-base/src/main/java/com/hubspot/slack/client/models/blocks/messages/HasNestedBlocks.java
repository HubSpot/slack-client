package com.hubspot.slack.client.models.blocks.messages;

import com.hubspot.slack.client.models.blocks.Block;

import java.util.List;
import java.util.Optional;

public interface HasNestedBlocks extends Block {
    List<Block> getElements();

    Optional<Integer> getIndent();

    Optional<Integer> getBorder();

    Optional<Integer> getOffset();
}
