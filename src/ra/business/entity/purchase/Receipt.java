package ra.business.entity.purchase;

import ra.business.design.IPurchasable;

import java.util.List;

public class Receipt
{
    List<IPurchasable> purchasedList;

    public Receipt()
    {
    }

    public Receipt(List<IPurchasable> purchasedList)
    {
        this.purchasedList = purchasedList;
    }

    public List<IPurchasable> getPurchasedList()
    {
        return purchasedList;
    }

    public void setPurchasedList(List<IPurchasable> purchasedList)
    {
        this.purchasedList = purchasedList;
    }
}
