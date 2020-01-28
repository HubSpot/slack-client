package com.hubspot.slack.client.methods.params.views;

import java.util.Optional;

import org.immutables.value.Value;
import org.immutables.value.Value.Check;
import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.models.views.ModalViewPayload;

@Immutable
@HubSpotStyle
@JsonNaming(SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface UpdateViewParamsIF {
  @Value.Parameter
  ModalViewPayload getView();
  Optional<String> getExternalId();
  Optional<String> getHash();
  Optional<String> getViewId();

  @Check
  default void check() {
    final boolean externalIdSet = !Strings.isNullOrEmpty(getExternalId().orElse(null));
    final boolean viewIdSet = !Strings.isNullOrEmpty(getViewId().orElse(null));
    Preconditions.checkState((externalIdSet || viewIdSet) && !(externalIdSet && viewIdSet),
        "Must include either view_id or external_id");
  }
}
