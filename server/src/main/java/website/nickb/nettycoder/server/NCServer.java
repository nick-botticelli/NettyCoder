package website.nickb.nettycoder.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class NCServer
{
    private final ObjectMapper objectMapper;

    private final Logger logger;
    private final int port;

    public NCServer(@NotNull Logger logger, int port)
    {
        // Create JSON ObjectMapper (Jackson)
        this.objectMapper = new ObjectMapper();

        this.logger = logger;
        this.port = port;
    }

    /**
     * Initialize Netty HTTP server to begin accepting connections
     */
    public void start() throws InterruptedException
    {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try
        {
            logger.debug("Setting up Netty...");

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>()
            {
                @Override
                protected void initChannel(@NotNull SocketChannel channel)
                {
                    logger.debug("Initializing server channel...");

                    // SSL
                    /*SelfSignedCertificate cert = new SelfSignedCertificate();
                    SslContext cont2 = SslContextBuilder.forServer(cert.privateKey(), cert.certificate()).build();
                    SSLEngine engine = cont2.newEngine(channel.alloc());*/

                    ChannelPipeline pipeline = channel.pipeline();
                    //pipeline.addLast("ssl", new SslHandler(engine));
                    pipeline.addLast(new HttpServerCodec());
                    pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));
                    pipeline.addLast(new NCServerRouter(getInstance()));

                    logger.debug("Initialized server channel.");
                }
            })
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

            logger.debug("Binding to port " + this.port + "...");
            Channel serverChannel = serverBootstrap.bind(this.port).sync().channel();

            logger.debug("Setup channel socket close...");
            serverChannel.closeFuture().sync();
        }
        finally
        {
            logger.debug("Gracefully shutting down Netty...");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * Get current instance for use in anonymous inner classes and lambdas
     *
     * @return Current {@link NCServer} instance object
     */
    @NotNull
    public NCServer getInstance()
    {
        return this;
    }

    /**
     * Jackson JSON ObjectMapper
     *
     * @return JSON {@link ObjectMapper}
     */
    @NotNull
    public ObjectMapper getObjectMapper()
    {
        return objectMapper;
    }

    @NotNull
    public Logger getLogger()
    {
        return logger;
    }

    public int getPort()
    {
        return port;
    }
}
