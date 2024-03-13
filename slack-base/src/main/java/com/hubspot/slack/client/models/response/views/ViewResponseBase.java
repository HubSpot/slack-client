package com.hubspot.slack.client.models.response.views;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.slack.client.models.response.views.json.StateBlockDeserializer;
import com.hubspot.slack.client.models.response.views.json.StateBlockSerializer;
import com.hubspot.slack.client.models.views.ViewPayloadBase;
import java.util.Optional;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
  {
    @JsonSubTypes.Type(value = ModalViewResponse.class, name = ModalViewResponse.TYPE),
    @JsonSubTypes.Type(
      value = HomeTabViewResponse.class,
      name = HomeTabViewResponse.TYPE
    ),
  }
)
public interface ViewResponseBase extends ViewPayloadBase {
  String getAppId();

  String getBotId();

  @JsonProperty("id")
  String getCurrentViewId();

  String getRootViewId();

  String getTeamId();

  @JsonProperty("state")
  @JsonDeserialize(using = StateBlockDeserializer.class)
  @JsonSerialize(using = StateBlockSerializer.class)
  StateBlock getStateValues();

  String getHash();

  Optional<String> getPreviousViewId();
}
