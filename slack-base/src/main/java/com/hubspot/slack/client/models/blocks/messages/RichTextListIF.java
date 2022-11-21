package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public interface RichTextListIF extends HasNestedBlocks {
    String TYPE = "rich_text_list";
    @Override
    @Value.Derived
    default String getType() {
        return TYPE;
    }

    ListType getStyle();

    int getIndent();

    int getBorder();

    Optional<Integer> getOffset();
}
