package com.hubspot.slack.client.methods.params.chat.workobject.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hubspot.immutables.style.HubSpotStyle;
import com.hubspot.slack.client.methods.params.chat.workobject.entity.fields.TagColor;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.FieldDataTypeDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.FieldDataTypeSerializer;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.TagColorDeserializer;
import com.hubspot.slack.client.methods.params.chat.workobject.serializers.TagColorSerializer;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@HubSpotStyle
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public interface EntityPayloadAttributesCustomFieldIF {
  String getKey();
  String getLabel();
  String getValue();

  @JsonSerialize(using = FieldDataTypeSerializer.class)
  @JsonDeserialize(using = FieldDataTypeDeserializer.class)
  FieldDataType getType();

  //Can only be set when the type is string. This icon will be displayed next to field's text value. Not compatible with tag_color.
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Optional<Icon> getIcon();

  //Can only be set when the type is string, date, or timestamp. The field's content will be hyperlinked with the URL specified here
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Optional<String> getLink();

  //Can only be set when the type is string. Allows the string to be highlighted in of one of the following colors: red, yellow, green, gray, blue. E.g. tag_color: "red"
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonSerialize(using = TagColorSerializer.class)
  @JsonDeserialize(using = TagColorDeserializer.class)
  Optional<TagColor> getTagColor();

  //Used when the field's type is slack#/types/image.
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Optional<String> getImageUrl();

  //Used when the field's type is slack#/types/image.
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Optional<String> getSlackFile();

  //Used when the field's type is slack#/types/image.
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  Optional<String> getAltText();
}
