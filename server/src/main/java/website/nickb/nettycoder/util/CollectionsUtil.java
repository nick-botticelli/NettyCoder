package website.nickb.nettycoder.util;

public final class CollectionsUtil
{
    public static int getMapInitialCapacity(int expectedSize)
    {
        return (int) ((float) expectedSize / 0.75F + 1.0F);
    }
}
