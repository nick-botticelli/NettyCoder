package website.nickb.nettycoder.common.requests;

import it.unimi.dsi.fastutil.ints.Int2ReferenceMap;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import website.nickb.nettycoder.common.util.CollectionsUtil;

public enum NCRequestType
{
    QUERY_TASK(0),
    CREATE_TASK(1),
    CANCEL_TASK(2),
    MODIFY_TASK(3),
    SEARCH_TASK(4);

    public static final int COUNT = 4;

    private final int type;

    NCRequestType(int type)
    {
        this.type = type;
    }

    public int getType()
    {
        return type;
    }

    private static final Int2ReferenceMap<NCRequestType> valueMap;
    static
    {
        valueMap = new Int2ReferenceOpenHashMap<>(CollectionsUtil.getMapInitialCapacity(COUNT));
        for (NCRequestType type : values())
            valueMap.put(type.getType(), type);
    }

    /**
     * Faster alternative to {@link #valueOf(String)} when {@code type} is not valid
     *
     * @param type String representing a possible {@link NCRequestType} value
     * @return {@link NCRequestType} if it exists, {@code null} if the lookup does not exist
     */
    public static NCRequestType getValue(int type)
    {
        return valueMap.get(type);
    }
}
