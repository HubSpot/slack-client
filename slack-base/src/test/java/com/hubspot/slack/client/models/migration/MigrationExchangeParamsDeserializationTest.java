package com.hubspot.slack.client.models.migration;

import static org.assertj.core.api.Assertions.assertThat;

import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.migration.MigrationExchangeResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

public class MigrationExchangeParamsDeserializationTest {

  private static final String VALID_USER_OLD_ID = "U1X3XFPJL";
  private static final String VALID_USER_MIGRATED_ID = "U01SZDTN10E";
  private static final String INVALID_USER_ID = "INVALID_ID";

  @Test
  public void itDeserializesMigrationExchangeResponseWithValidAndinvalidUserIds()
    throws IOException {
    MigrationExchangeResponse migrationExchangeResponse = fetchAndDeserializeMigrationExchangeResponse(
      "migration_exchange_response_with_valid_and_invalid_user_ids.json"
    );
    Map<String, String> userIdMap = migrationExchangeResponse.getUserIdMap();
    Set<String> invalidUserIds = migrationExchangeResponse.getInvalidUserIds();
    assertMainResponseFields(migrationExchangeResponse);
    assertThat(userIdMap).containsKey(VALID_USER_OLD_ID);
    assertThat(userIdMap).containsValue(VALID_USER_MIGRATED_ID);
    assertThat(invalidUserIds).containsExactly(INVALID_USER_ID);
  }

  @Test
  public void itDeserializesMigrationExchangeResponseWithOnlyValidUserIds()
    throws IOException {
    MigrationExchangeResponse migrationExchangeResponse = fetchAndDeserializeMigrationExchangeResponse(
      "migration_exchange_response_with_only_valid_user_ids.json"
    );
    Map<String, String> userIdMap = migrationExchangeResponse.getUserIdMap();
    Set<String> invalidUserIds = migrationExchangeResponse.getInvalidUserIds();
    assertMainResponseFields(migrationExchangeResponse);
    assertThat(userIdMap).containsKey(VALID_USER_OLD_ID);
    assertThat(userIdMap).containsValue(VALID_USER_MIGRATED_ID);
    assertThat(invalidUserIds.isEmpty());
  }

  @Test
  public void itDeserializesMigrationExchangeResponseWithOnlyInvalidUserIds()
    throws IOException {
    MigrationExchangeResponse migrationExchangeResponse = fetchAndDeserializeMigrationExchangeResponse(
      "migration_exchange_response_with_only_invalid_user_ids.json"
    );
    Map<String, String> userIdMap = migrationExchangeResponse.getUserIdMap();
    Set<String> invalidUserIds = migrationExchangeResponse.getInvalidUserIds();
    assertMainResponseFields(migrationExchangeResponse);
    assertThat(userIdMap.isEmpty());
    assertThat(invalidUserIds).containsExactly(INVALID_USER_ID);
  }

  private MigrationExchangeResponse fetchAndDeserializeMigrationExchangeResponse(
    String jsonFileName
  ) throws IOException {
    String rawJson = JsonLoader.loadJsonFromFile(jsonFileName);
    return ObjectMapperUtils.mapper().readValue(rawJson, MigrationExchangeResponse.class);
  }

  private void assertMainResponseFields(
    MigrationExchangeResponse migrationExchangeResponse
  ) {
    assertThat(migrationExchangeResponse.isOk()).isTrue();
    assertThat(migrationExchangeResponse.getTeamId()).isEqualTo("T024G0P55");
    assertThat(migrationExchangeResponse.getEnterpriseId()).isEqualTo("E01L6J4CPS8");
  }
}
