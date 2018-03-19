package com.hubspot.slack.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.hubspot.slack.client.methods.interceptor.HasChannel;

@RunWith(Parameterized.class)
public class HasChannelDeserializerTest extends ReflectionBasedFieldPresenceTest {
  @Parameters(name = "{0}")
  public static Iterable<Class<? extends HasChannel>> classes() {
    return getConcreteFinalSubclasses(HasChannel.class);
  }

  private Class<? extends HasChannel> hasChannelClass;

  public HasChannelDeserializerTest(
      Class<? extends HasChannel> hasChannelClass
  ) {
    this.hasChannelClass = hasChannelClass;
  }

  @Test
  public void pojosDoDeserializeHasChannelsWithChannelField() throws Exception {
    Object hasChannelInstance = buildTestInstance(hasChannelClass);
    verifyHasField(HasChannel.class, hasChannelInstance, "channel");
  }
}
