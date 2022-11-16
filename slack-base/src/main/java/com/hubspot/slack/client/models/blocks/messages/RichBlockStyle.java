package com.hubspot.slack.client.models.blocks.messages;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RichBlockStyle {
    ORDERED,
    BULLET;

    @Override
    @JsonValue
    public String toString() {
        return name().toLowerCase();
    }
}
