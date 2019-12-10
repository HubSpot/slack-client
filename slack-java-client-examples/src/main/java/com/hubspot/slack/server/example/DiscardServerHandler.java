package com.hubspot.slack.server.example;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hubspot.horizon.shaded.org.jboss.netty.buffer.ChannelBuffer;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.Channel;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ChannelHandlerContext;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ExceptionEvent;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.MessageEvent;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.SimpleChannelHandler;
import com.hubspot.horizon.shaded.org.jboss.netty.handler.codec.http.HttpRequest;
import com.hubspot.slack.client.jackson.ObjectMapperUtils;
import com.hubspot.slack.client.models.interaction.SlackInteractiveCallback;

public class DiscardServerHandler extends SimpleChannelHandler {

  private static final Logger LOG = LoggerFactory.getLogger(DiscardServerHandler.class);

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
    HttpRequest request = (HttpRequest) e.getMessage();
    ChannelBuffer content = request.getContent();
    try {
      String jsonContent = URLDecoder.decode(content.toString(StandardCharsets.UTF_8), "utf-8").substring(8);
      SlackInteractiveCallback callback = ObjectMapperUtils.mapper().readValue(jsonContent, SlackInteractiveCallback.class);
      LOG.info("Interactive Callback: {}", callback);
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
}
