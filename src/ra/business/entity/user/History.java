package ra.business.entity.user;

import ra.business.entity.purchase.Ticket;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class History implements Serializable
{
    private String historyId;
    private List<Ticket> ticketPurchased;

    public History()
    {
    }

    public History(String historyId, List<Ticket> ticketPurchased)
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

    public List<Ticket> getTicketPurchased()
    {
        return ticketPurchased;
    }

    public void setTicketPurchased(List<Ticket> ticketPurchased)
    {
        this.ticketPurchased = ticketPurchased;
    }
}
