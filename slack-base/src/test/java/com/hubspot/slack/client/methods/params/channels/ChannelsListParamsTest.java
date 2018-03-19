package com.hubspot.slack.client.methods.params.channels;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;

public class ChannelsListParamsTest {
  @Test
  public void itDoesSerWithInheritedFieldsPreserved() throws Exception {
    ChannelsListParams params = ChannelsListParams.builder()
        .setLimit(5)
        .setCursor("blah")
        .setShouldExcludeArchived(false)
        .setShouldExcludeMembers(true)
        .build();
    String rawJson = ObjectMapperUtils.mapper().writeValueAsString(params);
    JsonNode node = ObjectMapperUtils.mapper().readTree(rawJson);

    assertThat(node.has("exclude_archived"))
        .describedAs("Should have the exclude_archived field")
        .isTrue();

    assertThat(node.get("exclude_archived").asBoolean())
        .describedAs("If set to false, exclude_archived should be ser'd as false")
        .isFalse();

    assertThat(node.has("exclude_members"))
        .describedAs("Should have the exclude_archived field")
        .isTrue();

    assertThat(node.get("exclude_members").asBoolean())
        .describedAs("If set to true, exclude_members should be ser'd as false")
        .isTrue();
  }
}
