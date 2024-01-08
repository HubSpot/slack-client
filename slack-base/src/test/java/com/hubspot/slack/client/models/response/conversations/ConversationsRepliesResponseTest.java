package com.hubspot.slack.client.models.response.conversations;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.LiteMessage;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;

public class ConversationsRepliesResponseTest {

  @Test
  public void itDeserializesWithReplies() throws IOException {
    ConversationsRepliesResponse response = fetchAndDeserializeModel(
      "conversations.replies.with_replies.json"
    );
    assertThat(response.isOk()).isEqualTo(true);
    assertThat(response.getMessages().size()).isEqualTo(4);
    assertThat(response.getMessages().get(0)).isInstanceOf(LiteMessage.class);
    assertThat(response.getMessages().get(0).getReplyUserIds()).isEmpty();
    assertThat(response.getMessages().get(0).getReplyUsersCount()).isEmpty();
    assertThat(response.getMessages().get(0).getLatestReplyTimestamp()).isEmpty();
  }

  @Test
  public void itDeserializesWithRepliesAndNewFields() throws IOException {
    ConversationsRepliesResponse response = fetchAndDeserializeModel(
      "conversations.replies.with_replies_and_new_fields.json"
    );
    assertThat(response.isOk()).isEqualTo(true);
    assertThat(response.getMessages().size()).isEqualTo(4);
    assertThat(response.getMessages().get(0)).isInstanceOf(LiteMessage.class);
    assertThat(response.getMessages().get(0).getReplyUserIds().get())
      .isEqualTo(Arrays.asList("U061F7AUR", "U061F7AUR", "U061F7AUR"));
    assertThat(response.getMessages().get(0).getReplyUsersCount().get()).isEqualTo(3);
    assertThat(response.getMessages().get(0).getLatestReplyTimestamp().get())
      .isEqualTo("1483125339.020269");
    assertThat(response.getMessages().get(1).getReactions().size()).isEqualTo(2);
    assertThat(response.getMessages().get(1).getReactions().get(0).getName())
      .isEqualTo("astonished");
    assertThat(response.getMessages().get(1).getReactions().get(0).getCount())
      .isEqualTo(3);
    assertThat(response.getMessages().get(1).getReactions().get(1).getName())
      .isEqualTo("facepalm");
    assertThat(response.getMessages().get(1).getReactions().get(1).getCount())
      .isEqualTo(34);
    assertThat(response.getMessages().get(1).getReactions().get(1).getUsers())
      .isEqualTo(
        Arrays.asList("U061F7AUR", "U062F7AUR", "U063F7AUR", "U064F7AUR", "U065F7AUR")
      );
  }

  @Test
  public void itDeserializesNoRepliesAndWithNewFields() throws IOException {
    ConversationsRepliesResponse response = fetchAndDeserializeModel(
      "conversations.replies.no_replies_with_only_new_fields.json"
    );
    assertThat(response.isOk()).isEqualTo(true);
    assertThat(response.getMessages().size()).isEqualTo(4);
    assertThat(response.getMessages().get(0)).isInstanceOf(LiteMessage.class);
    assertThat(response.getMessages().get(0).getReplyUserIds().get())
      .isEqualTo(Arrays.asList("U061F7AUR", "U061F7AUR", "U061F7AUR"));
    assertThat(response.getMessages().get(0).getReplyUsersCount().get()).isEqualTo(3);
    assertThat(response.getMessages().get(0).getLatestReplyTimestamp().get())
      .isEqualTo("1483125339.020269");
  }

  private ConversationsRepliesResponse fetchAndDeserializeModel(String jsonFileName)
    throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils
      .mapper()
      .readValue(rawJson, ConversationsRepliesResponse.class);
  }
}
