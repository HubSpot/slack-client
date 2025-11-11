package com.hubspot.slack.server.example;

import com.hubspot.slack.client.SlackClient;
import com.hubspot.slack.client.examples.BasicRuntimeConfig;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.methods.params.views.OpenViewParams;
import com.hubspot.slack.client.models.blocks.Section;
import com.hubspot.slack.client.models.blocks.elements.DatePicker;
import com.hubspot.slack.client.models.blocks.objects.Text;
import com.hubspot.slack.client.models.blocks.objects.TextType;
import com.hubspot.slack.client.models.interaction.BlockActions;
import com.hubspot.slack.client.models.interaction.BlockElementAction;
import com.hubspot.slack.client.models.interaction.SlackInteractiveCallback;
import com.hubspot.slack.client.models.response.views.ModalViewCommandResponse;
import com.hubspot.slack.client.models.views.ModalViewPayload;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackMessageHandler extends SimpleChannelInboundHandler<HttpObject> {

  private static final Logger LOG = LoggerFactory.getLogger(SlackMessageHandler.class);

  private final SlackClient slackClient;

  public SlackMessageHandler() {
    slackClient = BasicRuntimeConfig.getClient();
  }

  @Override
  protected void channelRead0(
    ChannelHandlerContext channelHandlerContext,
    HttpObject msg
  ) throws Exception {
    if (msg instanceof HttpContent) {
      HttpContent httpContent = (HttpContent) msg;
      ByteBuf content = httpContent.content();

      try {
        String jsonContent = URLDecoder
          .decode(content.toString(StandardCharsets.UTF_8), StandardCharsets.UTF_8)
          .substring(8);
        SlackInteractiveCallback callback = ObjectMapperUtils
          .mapper()
          .readValue(jsonContent, SlackInteractiveCallback.class);
        LOG.info("Received raw JSON: {}", jsonContent);
        LOG.info("Deserialized Callback: {}", callback);
        if (callback instanceof BlockActions) {
          sendResponse((BlockActions) callback);
        }
      } catch (IOException ex) {
        LOG.error("Could not decode message", ex);
      }
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
    throws Exception {
    LOG.error("Got an error", cause.getCause());
    ctx.close();
  }

  private void sendResponse(BlockActions blockActions) {
    for (BlockElementAction action : blockActions.getElementActions()) {
      LOG.info("You interacted with: {}", action);
    }

    ModalViewCommandResponse response = slackClient
      .openView(
        OpenViewParams.of(
          blockActions.getTriggerId(),
          ModalViewPayload.of(
            Text.of(TextType.PLAIN_TEXT, "Hi " + blockActions.getUser().getUsername()),
            Collections.singletonList(
              Section
                .of(Text.of(TextType.MARKDOWN, "Thanks for clicking on _something_!"))
                .withAccessory(DatePicker.of("my-date-picker"))
            )
          )
        )
      )
      .join()
      .unwrapOrElseThrow();
    LOG.info("Open View Response: {}", response);
  }
}
