package website.nickb.nettycoder.server.util;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

public final class Constants
{
    public static final String SERVER_NAME = "NettyCoder";
    public static final String SERVER_VERSION = "v0.1.0";
    public static final String SERVER_HEADER = Constants.SERVER_NAME + " " + Constants.SERVER_VERSION;

    public static final int TEN_MIB = 10485760;

    public static final byte[] SERVER_EMPTY_CONTENT = "Empty content".getBytes(StandardCharsets.UTF_8);
    public static final byte[] SERVER_JSON_ERROR = "Error encoding JSON".getBytes(StandardCharsets.UTF_8);
    public static final byte[] SERVER_INVALID_JSON = "Invalid JSON content".getBytes(StandardCharsets.UTF_8);
    public static final byte[] SERVER_ERROR_TYPE = "Error handling data for type".getBytes(StandardCharsets.UTF_8);

    public static final DefaultFullHttpResponse SERVER_EMPTY_CONTENT_RESPONSE
            = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST,
            Unpooled.wrappedBuffer(SERVER_EMPTY_CONTENT));

    public static final DefaultFullHttpResponse SERVER_JSON_ERROR_RESPONSE
            = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR,
            Unpooled.wrappedBuffer(SERVER_JSON_ERROR));

    public static final DefaultFullHttpResponse SERVER_INVALID_JSON_RESPONSE
            = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST,
            Unpooled.wrappedBuffer(SERVER_INVALID_JSON));

    public static final DefaultFullHttpResponse SERVER_ERROR_TYPE_RESPONSE
            = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.INTERNAL_SERVER_ERROR,
            Unpooled.wrappedBuffer(SERVER_ERROR_TYPE));
}
