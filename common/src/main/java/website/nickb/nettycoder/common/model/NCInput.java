package website.nickb.nettycoder.common.model;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NCInput
{
    public final UUID uuid;
    public final String name;

    // Milliseconds
    public final long timeStart, timeEnd;

    public NCInput(@NotNull UUID uuid, @NotNull String name, long timeStart, long timeEnd)
    {
        this.uuid = uuid;
        this.name = name;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }
}
