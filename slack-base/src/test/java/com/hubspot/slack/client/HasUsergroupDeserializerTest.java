package com.hubspot.slack.client;

import com.hubspot.slack.client.methods.interceptor.HasUsergroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HasUsergroupDeserializerTest extends ReflectionBasedFieldPresenceTest {

  @Parameters(name = "{0}")
  public static Iterable<Class<? extends HasUsergroup>> classes() {
    return getConcreteFinalSubclasses(HasUsergroup.class);
  }

  private Class<? extends HasUsergroup> hasUsergroupClass;

  public HasUsergroupDeserializerTest(Class<? extends HasUsergroup> hasUsergroupClass) {
    this.hasUsergroupClass = hasUsergroupClass;
  }

  @Test
  public void pojosDoDeserializeHasUsergroupsWithUsergroupField() throws Exception {
    Object hasUsergroupInstance = buildTestInstance(hasUsergroupClass);
    verifyHasField(HasUsergroup.class, hasUsergroupInstance, "usergroup");
  }
}
