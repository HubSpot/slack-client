package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fields.EntityPayloadFields;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface EntityPayloadIF {
  EntityPayloadAttributes getAttributes();
  EntityPayloadFields getFields();
  List<EntityPayloadAttributesCustomField> getCustomFields();
  List<String> getDisplayOrder();
}
