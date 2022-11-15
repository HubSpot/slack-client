package com.hubspot.slack.client.models.blocks.messages;

import java.util.List;

public interface RichMessageBlock extends MessageBlock{
    List<MessageBlock> getElements();
}
