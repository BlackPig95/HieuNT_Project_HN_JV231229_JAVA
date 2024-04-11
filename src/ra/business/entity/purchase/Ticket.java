package ra.business.entity.purchase;

import ra.business.design.IPurchasable;
import ra.business.entity.movie.ShowTime;

import java.util.List;

public class Ticket implements IPurchasable
{
    private String ticketId = "";
    private int price = 55000;
    private String movieId;
    private String showTimeId;

    public Ticket()
    {
    }

    public Ticket(String ticketId, int price, String movieId, String showTimeId)
    {
        this.ticketId = ticketId;
        this.price = price;
        this.movieId = movieId;
        this.showTimeId = showTimeId;
    }

    public String getTicketId()
    {
        return ticketId;
    }

    public void setTicketId(String ticketId)
    {
        this.ticketId = ticketId;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getMovieId()
    {
        return movieId;
    }

    public void setMovieId(String movieId)
    {
        this.movieId = movieId;
    }

    public String getShowTimeId()
    {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId)
    {
        this.showTimeId = showTimeId;
    }

    public ShowTime getShowTimeFromId(List<ShowTime> showTimeList)
    {
        return showTimeList.stream().filter(s -> s.getShowTimeId().equals(this.showTimeId)).findFirst().orElse(null);
    }
}
