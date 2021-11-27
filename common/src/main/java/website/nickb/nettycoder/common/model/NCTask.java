package website.nickb.nettycoder.common.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class NCTask
{
    public final UUID uuid;

    public final List<NCInput> inputs;

    @JsonCreator
    public NCTask(@JsonProperty("id") @NotNull UUID uuid)
    {
        this.uuid = uuid;
        this.inputs = new ReferenceArrayList<>();
    }
}
