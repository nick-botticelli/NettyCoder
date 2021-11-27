package website.nickb.nettycoder.server.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Immutable data structure representing the NettyCoder server configuration file
 */
public final class NCConfig
{
    public final int version;

    public final int port;

    public final int maxContentLength;

    @JsonCreator
    public NCConfig(@JsonProperty("version") int version, @JsonProperty("port") int port,
                    @JsonProperty("max-content-length") int maxContentLength)
    {
        this.version = version;
        this.port = port;
        this.maxContentLength = maxContentLength;
    }
}
