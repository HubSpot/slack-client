package com.hubspot.slack.server.example;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hubspot.horizon.shaded.org.jboss.netty.buffer.ChannelBuffer;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.Channel;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ChannelHandlerContext;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ExceptionEvent;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.MessageEvent;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.SimpleChannelHandler;
import com.hubspot.horizon.shaded.org.jboss.netty.handler.codec.http.HttpRequest;
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

public class SlackMessageHandler extends SimpleChannelHandler {

  private static final Logger LOG = LoggerFactory.getLogger(SlackMessageHandler.class);

  private final SlackClient slackClient;

  public SlackMessageHandler() {
    slackClient = BasicRuntimeConfig.getClient();
  }


  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    HttpRequest request = (HttpRequest) e.getMessage();
    ChannelBuffer content = request.getContent();
    try {
      String jsonContent = URLDecoder.decode(content.toString(StandardCharsets.UTF_8), "utf-8").substring(8);
      SlackInteractiveCallback callback = ObjectMapperUtils.mapper().readValue(jsonContent, SlackInteractiveCallback.class);
      LOG.info("Received raw JSON: {}", jsonContent);
      LOG.info("Deserialized Callback: {}", callback);
      if (callback instanceof BlockActions) {
        sendResponse((BlockActions) callback);
      }
    } catch (IOException ex) {
      LOG.error("Could not decode message", ex);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    LOG.error("Got an error", e.getCause());
    Channel ch = e.getChannel();
    ch.close();
  }

  private void sendResponse(BlockActions blockActions) {
    for (BlockElementAction action : blockActions.getElementActions()) {
      LOG.info("You interaced with: {}", action);
    }

    ModalViewCommandResponse response = slackClient.openView(
        OpenViewParams.of(
            blockActions.getTriggerId(),
            ModalViewPayload.of(
                Text.of(TextType.PLAIN_TEXT, "Hi " + blockActions.getUser().getUsername()),
                Arrays.asList(Section.of(
                    Text.of(TextType.MARKDOWN, "Thanks for clicking on _something_!"))
                    .withAccessory(DatePicker.of("my-date-picker"))
                )
            ))
    ).join().unwrapOrElseThrow();
    LOG.info("Open View Response: {}", response);
  }
}
