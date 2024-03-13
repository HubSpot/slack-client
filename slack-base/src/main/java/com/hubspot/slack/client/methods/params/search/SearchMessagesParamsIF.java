package com.hubspot.slack.client.methods.params.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.ResultSort;
import com.hubspot.slack.client.methods.ResultSortOrder;
import org.immutables.value.Value.Default;
import org.immutables.value.Value.Immutable;

@Immutable
@HubSpotStyle
public interface SearchMessagesParamsIF {
  String getQuery();

  @Default
  default boolean shouldHighlight() {
    return false;
  }

  @Default
  default int getCount() {
    return 20;
  }

  @Default
  default int getPage() {
    return 1;
  }

  @Default
  default ResultSort getSort() {
    return ResultSort.SCORE;
  }

  @Default
  @JsonProperty("sort_dir")
  default ResultSortOrder getSortOrder() {
    return ResultSortOrder.DESC;
  }
}
