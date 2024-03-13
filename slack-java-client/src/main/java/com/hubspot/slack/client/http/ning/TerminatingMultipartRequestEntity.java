package com.hubspot.slack.client.http.ning;

import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.multipart.MultipartUtils;
import com.ning.http.client.multipart.Part;
import com.ning.http.client.providers.jdk.MultipartRequestEntity;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * MultipartRequestEntity that writes the end boundary properly
 */
public class TerminatingMultipartRequestEntity extends MultipartRequestEntity {

  public TerminatingMultipartRequestEntity(
    List<Part> parts,
    FluentCaseInsensitiveStringsMap requestHeaders
  ) {
    super(parts, requestHeaders);
  }

  @Override
  public void writeRequest(OutputStream out) throws IOException {
    super.writeRequest(out);
    out.write(MultipartUtils.getMessageEnd(getMultipartBoundary()));
  }
}
