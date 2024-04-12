package com.hubspot.slack.client.interceptors.http;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Stage;
import org.junit.Test;

public class SlackClientModuleTest {

    @Test
    public void itGuices() {
        Guice.createInjector(Stage.TOOL, new StrictGuiceModule());
    }

    private static class StrictGuiceModule extends AbstractModule {

        @Override
        protected void configure() {
            binder().requireAtInjectOnConstructors();

            install(new SlackClientModule());
        }
    }
}
