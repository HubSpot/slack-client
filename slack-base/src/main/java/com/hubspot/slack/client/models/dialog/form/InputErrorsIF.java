package com.hubspot.slack.client.models.dialog.form;

import com.hubspot.immutables.style.HubSpotStyle;
import java.util.List;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface InputErrorsIF {
  List<InputError> getErrors();
}
