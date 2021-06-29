package com.hubspot.slack.client.models.migration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.migration.MigrationExchangeResponse;

public class MigrationExchangeParamsDeserializationTest {

  private static final String VALID_USER_OLD_ID = "U1X3XFPJL";
  private static final String VALID_USER_MIGRATED_ID = "U01SZDTN10E";
  private static final String INVALID_USER_ID = "INVALID_ID";

  @Test
  public void itDeserializesMigrationExchangeResponseWithValidAndinvalidUserIds() throws IOException {
    MigrationExchangeResponse migrationExchangeResponse =
        fetchAndDeserializeMigrationExchangeResponse("migration_exchange_response_with_valid_and_invalid_user_ids.json");
    Map<String, String> userIdMap = migrationExchangeResponse.getUserIdMap();
    Optional<Set<String>> invalidUserIds = migrationExchangeResponse.getInvalidUserIds();
    assertTrue(doesMainParamsPresent(migrationExchangeResponse));
    assertTrue(userIdMap.containsKey(VALID_USER_OLD_ID) && userIdMap.containsValue(VALID_USER_MIGRATED_ID));
    assertTrue(invalidUserIds.isPresent() && invalidUserIds.get().contains(INVALID_USER_ID));
  }

  @Test
  public void itDeserializesMigrationExchangeResponseWithOnlyValidUserIds() throws IOException {
    MigrationExchangeResponse migrationExchangeResponse =
        fetchAndDeserializeMigrationExchangeResponse("migration_exchange_response_with_only_valid_user_ids.json");
    Map<String, String> userIdMap = migrationExchangeResponse.getUserIdMap();
    Optional<Set<String>> invalidUserIds = migrationExchangeResponse.getInvalidUserIds();
    assertTrue(doesMainParamsPresent(migrationExchangeResponse));
    assertTrue(userIdMap.containsKey(VALID_USER_OLD_ID) && userIdMap.containsValue(VALID_USER_MIGRATED_ID));
    assertFalse(invalidUserIds.isPresent());
  }

  @Test
  public void itDeserializesMigrationExchangeResponseWithOnlyInvalidUserIds() throws IOException {
    MigrationExchangeResponse migrationExchangeResponse =
        fetchAndDeserializeMigrationExchangeResponse("migration_exchange_response_with_only_invalid_user_ids.json");
    Map<String, String> userIdMap = migrationExchangeResponse.getUserIdMap();
    Optional<Set<String>> invalidUserIds = migrationExchangeResponse.getInvalidUserIds();
    assertTrue(doesMainParamsPresent(migrationExchangeResponse));
    assertTrue(userIdMap.isEmpty());
    assertTrue(invalidUserIds.isPresent() && invalidUserIds.get().contains(INVALID_USER_ID));
  }

  private MigrationExchangeResponse fetchAndDeserializeMigrationExchangeResponse(String jsonFileName) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, MigrationExchangeResponse.class);
  }

  private boolean doesMainParamsPresent(MigrationExchangeResponse migrationExchangeResponse) {
    return migrationExchangeResponse.isOk() &&
        !migrationExchangeResponse.getTeamId().isEmpty() &&
        !migrationExchangeResponse.getEnterpriseId().isEmpty();
  }
}
