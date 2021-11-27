package website.nickb.nettycoder.server.handlers.impl;

import io.netty.channel.ChannelHandlerContext;
import website.nickb.nettycoder.common.requests.NCRequest;
import website.nickb.nettycoder.common.responses.NCResponse;
import website.nickb.nettycoder.server.handlers.NCRequestHandler;

public class NCQueryRequestHandler extends NCRequestHandler
{
    @Override
    public NCResponse<?> getResponse(ChannelHandlerContext context, NCRequest<?> request)
    {
        return null;
    }
}
