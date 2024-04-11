package ra.business.entity.purchase;

public class Receipt
{
    private String ticketId;
    private String foodId;

    public Receipt()
    {
    }

    public Receipt(String ticketId, String foodId)
    {
        this.ticketId = ticketId;
        this.foodId = foodId;
    }

    public String getTicketId()
    {
        return ticketId;
    }

    public void setTicketId(String ticketId)
    {
        this.ticketId = ticketId;
    }

    public String getFoodId()
    {
        return foodId;
    }

    public void setFoodId(String foodId)
    {
        this.foodId = foodId;
    }
}
