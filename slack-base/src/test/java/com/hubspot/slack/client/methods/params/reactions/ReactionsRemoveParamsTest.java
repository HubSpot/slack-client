package com.hubspot.slack.client.methods.params.reactions;

import com.hubspot.immutables.validation.InvalidImmutableStateException;
import org.junit.Test;

public class ReactionsRemoveParamsTest {

  @Test(expected = InvalidImmutableStateException.class)
  public void itFailsForMissingName() {
    ReactionsRemoveParams
      .builder()
      .setChannel("C9520P0L9")
      .setTimestamp("1521832290718")
      .build();
  }

  @Test(expected = InvalidImmutableStateException.class)
  public void itFailsForMissingChannel() {
    ReactionsRemoveParams
      .builder()
      .setName("thumbsup")
      .setTimestamp("1521832290718")
      .build();
  }

  @Test(expected = InvalidImmutableStateException.class)
  public void itFailsForMissingTimestamp() {
    ReactionsRemoveParams.builder().setName("thumbsup").setChannel("C9520P0L9").build();
  }
}
