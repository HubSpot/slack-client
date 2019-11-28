package com.hubspot.slack.client.methods.params.views;

import java.util.Optional;

import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.ModalViewPayload;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
public interface UpdateViewParamsIF {
  ModalViewPayload getView();
  Optional<String> getExternalId();
  Optional<String> getHash();
  Optional<String> getViewId();

  @Check
  default void check() {
    Preconditions.checkState(Strings.isNullOrEmpty(getExternalId().orElse(null)) && Strings.isNullOrEmpty(getViewId().orElse(null)),
        "Must include either view_id or external_id");
  }
}
