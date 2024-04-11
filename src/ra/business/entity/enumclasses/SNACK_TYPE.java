package ra.business.entity.enumclasses;

public enum SNACK_TYPE
{
    POPCORN("1. Bỏng ngô"), SOFT_DRINK("2. Nước ngọt"),
    OTHER_FOOD("3. Đồ ăn khác"), OTHER_DRINK("4. Đồ uống khác");
    String name;

    SNACK_TYPE(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
