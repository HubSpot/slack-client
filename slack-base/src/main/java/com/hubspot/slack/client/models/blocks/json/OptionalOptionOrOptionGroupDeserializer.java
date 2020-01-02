package com.hubspot.slack.client.models.blocks.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hubspot.slack.client.models.blocks.objects.ConfirmationDialog;
import com.hubspot.slack.client.models.blocks.objects.Option;
import com.hubspot.slack.client.models.blocks.objects.OptionGroup;
import com.hubspot.slack.client.models.blocks.objects.OptionOrOptionGroup;

import java.io.IOException;
import java.util.Optional;

public class OptionalOptionOrOptionGroupDeserializer extends StdDeserializer<Optional<OptionOrOptionGroup>> {
    protected OptionalOptionOrOptionGroupDeserializer() {
        super(OptionOrOptionGroup.class);
    }

    @Override
    public Optional<OptionOrOptionGroup> deserialize(JsonParser p, DeserializationContext context) throws IOException {
        ObjectCodec codec = p.getCodec();
        JsonNode node = codec.readTree(p);

        if (node.has("value")) {
            return Optional.ofNullable(codec.treeToValue(node, Option.class));
        } else if (node.has("options")) {
            return Optional.ofNullable(codec.treeToValue(node, OptionGroup.class));
        } else {
            return Optional.ofNullable(codec.treeToValue(node, ConfirmationDialog.class));
        }
    }

}
