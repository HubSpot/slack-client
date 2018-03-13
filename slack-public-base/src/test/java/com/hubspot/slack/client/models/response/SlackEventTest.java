package com.hubspot.slack.client.models.response;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.hubspot.slack.client.models.events.SlackEvent;
import com.hubspot.slack.client.models.events.SlackEventBotMessage;
import com.hubspot.slack.client.models.events.SlackEventMessage;

public class SlackEventTest {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
      .registerModule(new Jdk8Module())
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private static final String SIMPLE_MESSAGE = "{\n" +
      "    \"type\": \"message\",\n" +
      "    \"channel\": \"C2147483705\",\n" +
      "    \"user\": \"U2147483697\",\n" +
      "    \"text\": \"Hello world\",\n" +
      "    \"ts\": \"1355517523.000005\"\n" +
      "}";

  private static final String BOT_MESSAGE = "{\n" +
      "    \"channel\": \"C2147483705\",\n" +
      "    \"type\": \"message\",\n" +
      "    \"subtype\": \"bot_message\",\n" +
      "    \"ts\": \"1358877455.000010\",\n" +
      "    \"text\": \"Pushing is the answer\",\n" +
      "    \"bot_id\": \"BB12033\",\n" +
      "    \"username\": \"github\",\n" +
      "    \"icons\": {}\n" +
      "}";

  @Test
  public void itDoesDeserializeSimpleMessage() throws Exception {
    SlackEvent event = OBJECT_MAPPER.readValue(SIMPLE_MESSAGE, SlackEventMessage.class);

    assertThat(event.getClass()).isAssignableFrom(SlackEventMessage.class);
  }

  @Test
  public void itDoesDeserializeBotMessage() throws Exception {
    SlackEvent event = OBJECT_MAPPER.readValue(BOT_MESSAGE, SlackEvent.class);

    assertThat(event.getClass()).isAssignableFrom(SlackEventBotMessage.class);
  }

  @Test
  public void itDoesSerializeSimpleMessage() throws Exception {
    SlackEvent event = OBJECT_MAPPER.readValue(BOT_MESSAGE, SlackEvent.class);

    SlackEvent reParsed = OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsBytes(event), SlackEvent.class);

    assertThat(event).isEqualTo(reParsed);
  }
}
