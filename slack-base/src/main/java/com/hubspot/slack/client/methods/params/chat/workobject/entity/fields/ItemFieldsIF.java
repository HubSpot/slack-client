package com.hubspot.slack.client.methods.params.chat.workobject.entity.fields;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hubspot.immutables.style.HubSpotStyle;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public interface ItemFieldsIF extends EntityPayloadFields {}
