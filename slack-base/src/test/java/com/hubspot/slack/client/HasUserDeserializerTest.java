package com.hubspot.slack.client;

import com.hubspot.slack.client.methods.interceptor.HasChannel;
import com.hubspot.slack.client.methods.interceptor.HasUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HasUserDeserializerTest extends ReflectionBasedFieldPresenceTest {

  @Parameters(name = "{0}")
  public static Iterable<Class<? extends HasUser>> classes() {
    return getConcreteFinalSubclasses(HasUser.class);
  }

  private Class<? extends HasUser> hasUserClass;

  public HasUserDeserializerTest(Class<? extends HasUser> hasUserClass) {
    this.hasUserClass = hasUserClass;
  }

  @Test
  public void pojosDoDeserializeHasUsersWithUserField() throws Exception {
    Object hasUserInstance = buildTestInstance(hasUserClass);
    verifyHasField(HasChannel.class, hasUserInstance, "user");
  }
}
