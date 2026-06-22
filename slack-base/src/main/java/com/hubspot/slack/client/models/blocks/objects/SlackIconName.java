package com.hubspot.slack.client.models.blocks.objects;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Icon names derived from @<a href="https://docs.slack.dev/reference/block-kit/composition-objects/slack-icon-object">slack docs</a>
 */
public enum SlackIconName {
  ARCHIVE("archive"),
  BOOK("book"),
  BOOKMARK("bookmark"),
  BOT("bot"),
  BUG("bug"),
  CALENDAR("calendar"),
  CALL("call"),
  CARET_LEFT("caret-left"),
  CARET_RIGHT("caret-right"),
  CHECK("check"),
  CLIPBOARD("clipboard"),
  CODE("code"),
  COMMENT("comment"),
  COMPASS("compass"),
  COPY("copy"),
  CUBE("cube"),
  DOWNLOAD("download"),
  EDIT("edit"),
  EMAIL("email"),
  EYE_CLOSED("eye-closed"),
  EYE_OPEN("eye-open"),
  FILE("file"),
  FLAG("flag"),
  FOLDER("folder"),
  GEAR("gear"),
  GLOBE("globe"),
  HEART("heart"),
  HELP("help"),
  IMAGE("image"),
  INFO("info"),
  KEY("key"),
  LIGHTBULB("lightbulb"),
  LINK("link"),
  MAP("map"),
  MOBILE("mobile"),
  NEW_WINDOW("new-window"),
  PIN("pin"),
  PLUS("plus"),
  REFINE("refine"),
  REFRESH("refresh"),
  ROCKET("rocket"),
  SAVE("save"),
  SCREEN("screen"),
  SHARE("share"),
  SPARKLE("sparkle"),
  STAR("star"),
  STAR_FILLED("star-filled"),
  TAG("tag"),
  THUMBS_DOWN("thumbs-down"),
  THUMBS_UP("thumbs-up"),
  TRASH("trash"),
  UPLOAD("upload"),
  USER("user"),
  WARNING("warning");

  private final String value;

  SlackIconName(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }
}
