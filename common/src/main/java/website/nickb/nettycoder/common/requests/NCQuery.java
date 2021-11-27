package website.nickb.nettycoder.common.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import org.jetbrains.annotations.NotNull;

public class NCQuery
{
    public final long id;
    public final NCQueryOption queryOption;

    @JsonCreator
    public NCQuery(@JsonProperty("id") long id, @JsonProperty("queryOption") @NotNull NCQueryOption queryOption)
    {
        this.id = id;
        this.queryOption = queryOption;
    }

    enum NCQueryOption
    {
        NONE(0),
        FULL(1);

        private final int option;

        NCQueryOption(int responseCode)
        {
            this.option = responseCode;
        }

        @JsonValue
        public int getOption()
        {
            return option;
        }
    }
}
