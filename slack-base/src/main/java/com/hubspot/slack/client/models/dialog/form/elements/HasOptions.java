package com.hubspot.slack.client.models.dialog.form.elements;

import java.util.List;

public interface HasOptions {
  List<SlackFormOption> getOptions();
}
