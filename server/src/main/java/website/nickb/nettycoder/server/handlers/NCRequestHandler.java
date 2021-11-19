package website.nickb.nettycoder.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import website.nickb.nettycoder.server.requests.NCRequest;
import website.nickb.nettycoder.server.responses.NCResponse;

public abstract class NCRequestHandler
{
    /**
     * Implements main logic for handling a command from a client
     *
     * @return an {@code Object} representing the result
     */
    public abstract NCResponse<?> getResponse(ChannelHandlerContext context, NCRequest<?> request);
}
