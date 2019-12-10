package com.hubspot.slack.server.example;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.hubspot.horizon.shaded.org.jboss.netty.bootstrap.ServerBootstrap;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ChannelFactory;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ChannelPipeline;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.ChannelPipelineFactory;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.Channels;
import com.hubspot.horizon.shaded.org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import com.hubspot.horizon.shaded.org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import com.hubspot.horizon.shaded.org.jboss.netty.handler.codec.http.HttpServerCodec;

public class DiscardServer {

  public static void main(String[] args) throws Exception {
    ChannelFactory factory =
        new NioServerSocketChannelFactory(
            Executors.newCachedThreadPool(),
            Executors.newCachedThreadPool());

    ServerBootstrap bootstrap = new ServerBootstrap(factory);

    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      public ChannelPipeline getPipeline() {
        return Channels.pipeline(new HttpServerCodec(), new HttpChunkAggregator(2 << 10), new DiscardServerHandler());
      }
    });

    bootstrap.setOption("child.tcpNoDelay", true);
    bootstrap.setOption("child.keepAlive", true);

    bootstrap.bind(new InetSocketAddress(8080));
  }
}
