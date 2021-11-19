package website.nickb.nettycoder.server.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Immutable data structure representing a request from a client
 *
 * @param <T> generic type of data in the request
 */
public class NCRequest<T>
{
    private final NCRequestType type;
    private final T params;

    @JsonCreator
    public NCRequest(@JsonProperty("type") @NotNull NCRequestType type, @JsonProperty("params") @Nullable T params)
    {
        this.type = type;
        this.params = params;
    }

    @NotNull
    public NCRequestType getType()
    {
        // TODO: Can this be null?
        return type;
    }

    @Nullable
    public T getParams()
    {
        return params;
    }
}
