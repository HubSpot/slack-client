package com.hubspot.slack.client.models.attachments;

import com.hubspot.slack.client.SerializationTestBase;
import com.hubspot.slack.client.models.Attachment;
import org.junit.Test;

import java.io.IOException;

public class AttachmentSerializationTest extends SerializationTestBase {

    @Test
    public void testAttachmentsSerialization() throws IOException {
        testSerialization("attachment_without_blocks.json", Attachment.class);
    }

    @Test
    public void testAttachmentsWithBlocksSerialization() throws IOException {
        testSerialization("attachment_with_blocks.json", Attachment.class);
    }
}
