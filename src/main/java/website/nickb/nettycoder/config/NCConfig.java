package website.nickb.nettycoder.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Immutable data structure representing the NettyCoder configuration file
 */
public final class NCConfig
{
    public final int version;

    public final int port;

    @JsonCreator
    public NCConfig(@JsonProperty("version") int version, @JsonProperty("port") int port)
    {
        this.version = version;
        this.port = port;
    }
}
