package com.hubspot.slack.client.models.blocks.messages;

import static com.hubspot.slack.client.testutils.TestBlocksBuilder.buildList;
import static com.hubspot.slack.client.testutils.TestBlocksBuilder.buildMessageBlockText;
import static com.hubspot.slack.client.testutils.TestBlocksBuilder.buildMessageBlockTextWithStyle;
import static com.hubspot.slack.client.testutils.TestBlocksBuilder.buildPreformattedText;
import static com.hubspot.slack.client.testutils.TestBlocksBuilder.buildQuote;
import static com.hubspot.slack.client.testutils.TestBlocksBuilder.buildTextSection;
import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.LiteMessage;
import com.hubspot.slack.client.models.blocks.Block;
import com.hubspot.slack.client.models.blocks.UnknownBlock;
import com.hubspot.slack.client.models.interaction.MessageAction;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class MessageActionSerializationTest {
    public static final int BLOCKS_COUNT = 1;

    @Test
    public void testMessageActionSerialization() throws IOException {
        String rawJson = JsonLoader.loadJsonFromFile("message_action.json");
        MessageAction messageAction = ObjectMapperUtils.mapper().readValue(rawJson, MessageAction.class);

        assertThat(messageAction).isNotNull();

        LiteMessage message = messageAction.getMessage();
        List<Block> blocks = message.getBlocks();
        assertThat(blocks.size()).isEqualTo(BLOCKS_COUNT);

        HasNestedBlocks actualRootBlock = (HasNestedBlocks) blocks.get(0);

        HasNestedBlocks expectedRootBlock = buildExpectedRootBlock();

        assertThat(actualRootBlock.getType()).isEqualTo(expectedRootBlock.getType());
        assertThat(actualRootBlock.getElements())
                .containsExactlyElementsOf(expectedRootBlock.getElements());
    }

    @Test
    public void itDeserializesMessageActionWithDummyBlockTypesParsedAsUnknownBlocks () throws IOException {
        String rawJson = JsonLoader.loadJsonFromFile("message_action_with_unknown_nested_blocks.json");
        MessageAction messageAction = ObjectMapperUtils.mapper().readValue(rawJson, MessageAction.class);

        assertThat(messageAction).isNotNull();

        LiteMessage message = messageAction.getMessage();
        List<Block> blocks = message.getBlocks();
        assertThat(blocks.size()).isEqualTo(BLOCKS_COUNT);

        HasNestedBlocks actualRootBlock = (HasNestedBlocks) blocks.get(0);
        List<Block> nestedElements = actualRootBlock.getElements();

        assertThat(actualRootBlock.getType()).isEqualTo(RichText.TYPE);

        RichTextSection richTextSectionWithUnknownBlockAsElement = (RichTextSection) nestedElements.get(0);
        assertThat(richTextSectionWithUnknownBlockAsElement.getElements()).hasSize(1);
        assertThat(richTextSectionWithUnknownBlockAsElement.getElements().get(0))
                .isInstanceOf(UnknownBlock.class);

        Block secondNestedElement = nestedElements.get(1);
        assertThat(secondNestedElement).isInstanceOf(UnknownBlock.class);
    }

    private HasNestedBlocks buildExpectedRootBlock() {
        Text simpleCaseTextBlock = buildMessageBlockText("Simple case");
        Text boldTextBlock = buildMessageBlockTextWithStyle("A bold text.",
                TextStyle.builder().setBold(true).build());
        Text strikeTextBlock = buildMessageBlockTextWithStyle("Strike text.",
                TextStyle.builder().setStrikethrough(true).build());

        RichTextSection firstTextSection = buildTextSection(simpleCaseTextBlock, boldTextBlock, strikeTextBlock);

        Text firstListItem = buildMessageBlockText("Numbered list root level.");
        RichTextSection richTextSectionInsideList = buildTextSection(firstListItem);
        RichTextList richTextListFirst = buildList(0, 0, ListType.ORDERED, richTextSectionInsideList);

        Text secondListItem = buildMessageBlockText("Letter list from ordered(numbered) list");
        RichTextSection richTextSecondSectionInsideList = buildTextSection(secondListItem);
        RichTextList richTextListSecond = buildList(1, 0, ListType.ORDERED, richTextSecondSectionInsideList);

        Text firstRomeListItem = buildMessageBlockText("Rome list level 1");
        Text secondRomeListItem = buildMessageBlockTextWithStyle("Rome list italic bold strike",
                TextStyle.builder().setStrikethrough(true).setBold(true).setItalic(true).build());
        RichTextSection firstRomeTextSection = buildTextSection(firstRomeListItem);
        RichTextSection secondRomeTextSection = buildTextSection(secondRomeListItem);
        RichTextList richTextListRomeFirst = buildList(2, 0, ListType.ORDERED, firstRomeTextSection, secondRomeTextSection);

        Text letterListItem = buildMessageBlockText("Letter list item after rome list.");
        RichTextSection richTextFirstSectionInsideLetterList = buildTextSection(letterListItem);
        RichTextList richTextLetterListFirst = buildList(1, 0, ListType.ORDERED, richTextFirstSectionInsideLetterList)
                .withOffset(2);

        Text firstBulletListItem = buildMessageBlockText("Bulleted list root");
        RichTextSection richTextSectionInsideBulletList = buildTextSection(firstBulletListItem);
        RichTextList richTextBulletListFirst = buildList(0, 0, ListType.BULLET, richTextSectionInsideBulletList);

        Text quoteTest = buildMessageBlockText("Dobrii vechir, everybady!");
        RichTextQuote richTextQuote = buildQuote(quoteTest);

        Text codeText = buildMessageBlockTextWithStyle("code line",
                TextStyle.builder().setCode(true).build());
        RichTextSection codeTextSection = buildTextSection(codeText);

        Text preformattedText = buildMessageBlockText("var bar far;\n\npub sub mob dob;\ncucu");
        RichTextPreformatted richTextPreformatted = buildPreformattedText(preformattedText);

        User user = User.builder().setUserId("U01DVDVMS12").build();
        RichTextSection userSection = buildTextSection(user);

        return RichText.builder()
                .addElements(firstTextSection)
                .addElements(richTextListFirst)
                .addElements(richTextListSecond)
                .addElements(richTextListRomeFirst)
                .addElements(richTextLetterListFirst)
                .addElements(richTextBulletListFirst)
                .addElements(richTextQuote)
                .addElements(codeTextSection)
                .addElements(richTextPreformatted)
                .addElements(userSection)
                .build();
    }
}
