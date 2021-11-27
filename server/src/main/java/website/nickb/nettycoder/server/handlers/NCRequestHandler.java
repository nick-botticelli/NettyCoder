package website.nickb.nettycoder.server.handlers;

import io.netty.channel.ChannelHandlerContext;
import website.nickb.nettycoder.common.requests.NCRequest;
import website.nickb.nettycoder.common.responses.NCResponse;

public abstract class NCRequestHandler
{
    /**
     * Implements main logic for handling a command from a client
     *
     * @return a generic {@link Object} representing the result
     */
    public abstract NCResponse<?> getResponse(ChannelHandlerContext context, NCRequest<?> request);
}
