package website.nickb.nettycoder.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import website.nickb.nettycoder.server.handlers.NCRequestHandler;
import website.nickb.nettycoder.server.requests.NCRequest;
import website.nickb.nettycoder.server.requests.NCRequestType;
import website.nickb.nettycoder.server.responses.NCResponse;
import website.nickb.nettycoder.util.Constants;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class NCServerRouter extends SimpleChannelInboundHandler<FullHttpRequest>
{
    private final NCServer server;
    private final ObjectMapper objMapper;

    private final Map<NCRequestType, NCRequestHandler> requestHandlers;

    public NCServerRouter(@NotNull NCServer server)
    {
        this.server = server;
        this.objMapper = server.getObjectMapper();
        this.requestHandlers = new EnumMap<>(NCRequestType.class);

        // Initialize request handlers
    }

    @Override
    protected void channelRead0(@NotNull ChannelHandlerContext ctx, @NotNull FullHttpRequest msg)
    {
        if (msg.decoderResult() != DecoderResult.SUCCESS)
        {
            ctx.close();
            return;
        }

        // Check if content is null or empty
        ByteBuf content = msg.content();
        byte[] data = content.array();

        // TODO: Check if content is required
        if (data == null || data.length == 0)
        {
            finishAndSendResponse(ctx, msg, Constants.SERVER_EMPTY_CONTENT_RESPONSE);
            return;
        }

        // Deserialize JSON request
        NCRequest<?> requestObj = null;
        try
        {
            requestObj = objMapper.readValue(data, NCRequest.class);
        }
        catch (IOException exc)
        {
            finishAndSendResponse(ctx, msg, Constants.SERVER_INVALID_JSON_RESPONSE);
            return;
        }

        // Check de-serialized JSON for errors
        if (requestObj == null /*|| requestObj.getType() == null*/ || requestObj.getParams() == null)
        {
            finishAndSendResponse(ctx, msg, Constants.SERVER_INVALID_JSON_RESPONSE);
            return;
        }

        // Get response for request
        NCResponse<?> responseObj = requestHandlers.get(requestObj.getType()).getResponse(ctx, requestObj);

        if (responseObj == null)
        {
            finishAndSendResponse(ctx, msg, Constants.SERVER_ERROR_TYPE_RESPONSE);
            return;
        }

        // Serialize Response object into JSON data
        byte[] responseData;
        try
        {
            responseData = objMapper.writeValueAsBytes(responseObj);
        }
        catch (JsonProcessingException exc)
        {
            finishAndSendResponse(ctx, msg, Constants.SERVER_JSON_ERROR_RESPONSE);
            return;
        }

        finishAndSendResponse(ctx, msg, createResponse(HttpResponseStatus.OK, responseData));
    }

    /**
     * Create an HTTP response
     *
     * @param data bytes to encode in the body of an error response
     * @return a {@link DefaultFullHttpResponse} for creating a finished HTTP response
     */
    public static DefaultFullHttpResponse createResponse(HttpResponseStatus status, byte[] data)
    {
        return new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.wrappedBuffer(data));
    }

    /**
     * Finish creating and send the {@link FullHttpResponse}
     *
     * @param response the (un-finished) {@link FullHttpResponse} to send to the client
     */
    public void finishAndSendResponse(@NotNull ChannelHandlerContext context, @NotNull FullHttpRequest request,
                                      @NotNull FullHttpResponse response)
    {
        response.headers().set(HttpHeaderNames.SERVER, Constants.SERVER_HEADER);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        ChannelFuture channelFuture;
        if (io.netty.handler.codec.http.HttpUtil.isKeepAlive(request))
        {
            response.headers().set(HttpHeaderNames.CONNECTION, "keep-alive");
            channelFuture = context.writeAndFlush(response);
        }
        else
        {
            channelFuture = context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        if (!channelFuture.isSuccess())
            server.getLogger().error("HttpHandler#sendResponse() failed: " + channelFuture.cause());
    }

    /**
     * Add a request handler for the specified request type
     *
     * @param type the type to add a request handler for (1 request handler per type)
     * @param requestHandler The request handler to set to the type
     * @return the previous value associated with the specified {@code type}, or {@code null} if there was no previous
     * value
     */
    @Nullable
    public NCRequestHandler addRequestHandler(@NotNull NCRequestType type, @NotNull NCRequestHandler requestHandler)
    {
        return requestHandlers.put(type, requestHandler);
    }
}
