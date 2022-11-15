package com.hubspot.slack.client.models.blocks.messages;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.interaction.MessageAction;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MessageActionSerializationTest {
    public static final int BLOCKS_COUNT = 1;
    private static final int RICH_MESSAGE_BLOCKS_COUNT = 18;

    @Test
    public void testMessageActionSerialization() throws IOException {
        String rawJson = JsonLoader.loadJsonFromFile("message_action.json");
        MessageAction messageAction = ObjectMapperUtils.mapper().readValue(rawJson, MessageAction.class);

        assertThat(messageAction).isNotNull();

        LiteMessage message = messageAction.getMessage();
        List<Block> blocks = message.getBlocks();
        assertThat(blocks.size()).isEqualTo(BLOCKS_COUNT);

        RichMessageBlock firstBlock = (RichMessageBlock) blocks.get(0);
        List<MessageBlock> elements = firstBlock.getElements();
        assertThat(elements.size()).isEqualTo(RICH_MESSAGE_BLOCKS_COUNT);
    }
}
