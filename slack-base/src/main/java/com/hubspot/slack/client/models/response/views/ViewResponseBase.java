package com.hubspot.slack.client.models.response.views;

import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hubspot.slack.client.models.views.ViewPayloadBase;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = ModalViewResponse.class, name = ModalViewResponse.TYPE),
    @JsonSubTypes.Type(value = HomeTabViewResponse.class, name = HomeTabViewResponse.TYPE),
})
public interface ViewResponseBase extends ViewPayloadBase {
  String getAppId();

  String getBotId();

  @JsonProperty("id")
  String getCurrentViewId();

  String getRootViewId();

  String getTeamId();

  Optional<String> getPreviousViewId();
}
