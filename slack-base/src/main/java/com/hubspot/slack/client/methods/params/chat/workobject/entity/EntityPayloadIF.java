package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.actions.WorkObjectActionBlock;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fields.EntityPayloadFields;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface EntityPayloadIF {
  EntityPayloadAttributes getAttributes();
  Optional<EntityPayloadFields> getFields();
  List<EntityPayloadAttributesCustomField> getCustomFields();
  List<String> getDisplayOrder();
  WorkObjectActionBlock getActions();
}
