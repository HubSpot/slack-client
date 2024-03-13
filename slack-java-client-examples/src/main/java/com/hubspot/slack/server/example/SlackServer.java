package com.hubspot.slack.server.example;

import com.hubspot.horizon.shaded.org.jboss.netty.bootstrap.ServerBootstrap;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ChannelFactory;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.Channels;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import com.hubspot.horizon.shaded.org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import com.hubspot.horizon.shaded.org.jboss.netty.handler.codec.http.HttpServerCodec;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class SlackServer {

  public static void main(String[] args) {
    ChannelFactory factory = new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool()
    );

    ServerBootstrap bootstrap = new ServerBootstrap(factory);

    bootstrap.setPipelineFactory(() ->
      Channels.pipeline(
        new HttpServerCodec(),
        new HttpChunkAggregator(2 << 10),
        new SlackMessageHandler()
      )
    );

    bootstrap.setOption("child.tcpNoDelay", true);
    bootstrap.setOption("child.keepAlive", true);

    bootstrap.bind(new InetSocketAddress(8080));
  }
}
