package website.nickb.nettycoder.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.jetbrains.annotations.NotNull;

public class NCServerRouter extends SimpleChannelInboundHandler<FullHttpRequest>
{
    private final NCServer server;

    public NCServerRouter(@NotNull NCServer server)
    {
        this.server = server;
    }

    @Override
    protected void channelRead0(@NotNull ChannelHandlerContext ctx, @NotNull FullHttpRequest msg)
    {
    }
}
