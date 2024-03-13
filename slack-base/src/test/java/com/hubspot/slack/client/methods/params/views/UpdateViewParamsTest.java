package com.hubspot.slack.client.methods.params.views;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.hubspot.immutables.validation.InvalidImmutableStateException;
import com.hubspot.slack.client.models.blocks.Divider;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import com.hubspot.slack.client.models.views.ModalViewPayload;
import org.junit.Test;

public class UpdateViewParamsTest {

  @Test
  public void itBuildsWithViewIdButNotExternalId() {
    UpdateViewParams
      .builder()
      .setViewId("viewId")
      .setView(
        ModalViewPayload
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "TEST VIEW"))
          .addBlocks(Divider.builder().build())
          .build()
      )
      .build();
  }

  @Test
  public void itBuildsWithExternalIdButNotExternalId() {
    UpdateViewParams
      .builder()
      .setExternalId("externalId")
      .setView(
        ModalViewPayload
          .builder()
          .setTitle(Text.of(TextType.PLAIN_TEXT, "TEST VIEW"))
          .addBlocks(Divider.builder().build())
          .build()
      )
      .build();
  }

  @Test
  public void itFailsWithoutViewIdOrExternalId() {
    try {
      UpdateViewParams
        .builder()
        .setView(
          ModalViewPayload
            .builder()
            .setTitle(Text.of(TextType.PLAIN_TEXT, "TEST VIEW"))
            .addBlocks(Divider.builder().build())
            .build()
        )
        .build();

      fail(
        "Should have thrown an error for UpdateViewParams built without view_id or external_id!"
      );
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("Must include either view_id or external_id");
    }
  }

  @Test
  public void itFailsWitViewIdAndExternalId() {
    try {
      UpdateViewParams
        .builder()
        .setViewId("viewId")
        .setExternalId("externalId")
        .setView(
          ModalViewPayload
            .builder()
            .setTitle(Text.of(TextType.PLAIN_TEXT, "TEST VIEW"))
            .addBlocks(Divider.builder().build())
            .build()
        )
        .build();

      fail(
        "Should have thrown an error for UpdateViewParams built without view_id or external_id!"
      );
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("Must include either view_id or external_id");
    }
  }
}
