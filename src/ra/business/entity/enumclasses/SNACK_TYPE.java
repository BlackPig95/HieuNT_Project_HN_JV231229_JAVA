package ra.business.entity.enumclasses;

public enum SNACK_TYPE
{
    POPCORN("1. Bỏng ngô"), SOFT_DRINK("2. Nước ngọt");
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
