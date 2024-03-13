package com.hubspot.slack.client.models.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.slack.client.models.blocks.objects.Text;
import java.util.Optional;
import org.immutables.value.Value;

public interface ModalViewPayloadBase extends ViewPayloadBase {
  String TYPE = "modal";

  @Override
  @Value.Derived
  default String getType() {
    return TYPE;
  }

  Text getTitle();

  @JsonProperty("close")
  Optional<Text> getCloseButtonText();

  @JsonProperty("submit")
  Optional<Text> getSubmitButtonText();

  Optional<Boolean> getClearOnClose();

  Optional<Boolean> getNotifyOnClose();
}
