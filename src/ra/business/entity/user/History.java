package ra.business.entity.user;

import ra.business.entity.purchase.Ticket;

import java.time.LocalDate;
import java.util.List;

public class History
{
    private String historyId;
    private Ticket ticketPurchased;

    public History()
    {
    }

    public History(String historyId, Ticket ticketPurchased)
    {
        this.historyId = historyId;
        this.ticketPurchased = ticketPurchased;
    }

    public String getHistoryId()
    {
        return historyId;
    }

    public void setHistoryId(String historyId)
    {
        this.historyId = historyId;
    }

    public Ticket getTicketPurchased()
    {
        return ticketPurchased;
    }

    public void setTicketPurchased(Ticket ticketPurchased)
    {
        this.ticketPurchased = ticketPurchased;
    }
}
