package com.hubspot.slack.client.methods.params.migration;

import java.io.IOException;

import org.junit.Test;

import com.hubspot.slack.client.SerializationTestBase;

public class MigrationExchangeParamsSerializationTest extends SerializationTestBase {
  @Test
  public void testMigrationExchangeSerialization() throws IOException {
    testSerialization("migration_exchange_params.json", MigrationExchangeParams.class);
  }
}
