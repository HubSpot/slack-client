package com.hubspot.slack.client.methods.params.chat.workobject;

import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
public interface MetadataIF {
  List<WorkObject> getEntities();
}
