package website.nickb.nettycoder.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import website.nickb.nettycoder.server.util.Constants;

public class NCServer
{
    private final ObjectMapper objectMapper;

    private final Logger logger;
    private final int port;
    private final int maxContentLength;

    public NCServer(@NotNull Logger logger, int port)
    {
        this(logger, port, Constants.TEN_MIB);
    }

    public NCServer(@NotNull Logger logger, int port, int maxContentLength)
    {
        // Create JSON ObjectMapper (Jackson)
        this.objectMapper = new ObjectMapper();

        this.logger = logger;
        this.port = port;
        this.maxContentLength = maxContentLength;
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

            HttpServerCodec codec = new HttpServerCodec();
            HttpContentCompressor compress = new HttpContentCompressor();
            HttpContentDecompressor decompress = new HttpContentDecompressor();
            HttpObjectAggregator aggregator = new HttpObjectAggregator(maxContentLength);
            NCServerRouter request = new NCServerRouter(this);

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>()
            {
                @Override
                protected void initChannel(@NotNull SocketChannel channel)
                {
                    logger.debug("Initializing channel...");

                    // TODO: Add optional SSL support
                    ChannelPipeline pipeline = channel.pipeline();
                    pipeline.addLast("codec", codec);
                    pipeline.addLast("compress", compress);
                    pipeline.addLast("decompress", decompress);
                    pipeline.addLast("aggregator", aggregator);
                    pipeline.addLast("request", request);
                }
            })
            .option(ChannelOption.SO_BACKLOG, 128) // TODO: Add config value
            .childOption(ChannelOption.SO_KEEPALIVE, true); // TODO: Add config value

            logger.debug("Binding to port " + this.port + "...");
            Channel serverChannel = serverBootstrap.bind(this.port).sync().channel();

            logger.debug("Setup channel socket close future...");
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
