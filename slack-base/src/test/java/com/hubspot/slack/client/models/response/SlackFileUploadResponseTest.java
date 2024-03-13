package com.hubspot.slack.client.models.response;

import com.hubspot.slack.client.TestMappers;
import com.hubspot.slack.client.models.JsonLoader;
import com.hubspot.slack.client.models.response.files.FilesUploadResponse;
import org.junit.Test;

public class SlackFileUploadResponseTest {

  @Test
  public void itDoesDeserializeFilesUploadResponseForContentField() throws Exception {
    String rawJson = JsonLoader.loadJsonFromFile("file_upload_content_param_result.json");

    FilesUploadResponse response = TestMappers.OBJECT_MAPPER.readValue(
      rawJson,
      FilesUploadResponse.class
    );
  }

  @Test
  public void itDoesDeserializeFilesUploadResponseForImage() throws Exception {
    String rawJson = JsonLoader.loadJsonFromFile("file_upload_gif_result.json");

    FilesUploadResponse response = TestMappers.OBJECT_MAPPER.readValue(
      rawJson,
      FilesUploadResponse.class
    );
    response.getFile();
  }
}
