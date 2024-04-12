package ra.business.entity.purchase;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.design.IPurchasable;
import ra.business.entity.movie.Movie;
import ra.business.entity.movie.ShowTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class Ticket implements IPurchasable
{
    private String ticketId = "";
    private int price = 55000;
    private Movie movie;
    private ShowTime showTime;
    private List<String> seatNameList;
    private LocalDateTime timePurchased;

    public Ticket()
    {
    }

    public Ticket(String ticketId, int price, Movie movie, ShowTime showTime, List<String> seatNameList, LocalDateTime timePurchased)
    {
        this.ticketId = ticketId;
        this.price = price;
        this.movie = movie;
        this.showTime = showTime;
        this.seatNameList = seatNameList;
        this.timePurchased = timePurchased;
    }

    @Override
    public String showBasicData()
    {
        //Dùng để in ra danh sách ghế ngồi
        StringBuilder listSeat = new StringBuilder();
        if (!this.seatNameList.isEmpty())
        {
            this.seatNameList.forEach(s -> listSeat.append(s).append(" | "));
        }
        return CONSOLECOLORS.YELLOW
                + "--------------------------------------------------------------------------------------"
                + CONSOLECOLORS.RESET
                + "\nMã vé: " + this.ticketId + " | Giá vé: " + this.price
                + " | Tổng tiền vé: " + (this.seatNameList.size() * this.price)
                + CONSOLECOLORS.YELLOW
                + "\n--------------------------------------------------------------------------------------"
                + CONSOLECOLORS.RESET
                + "\nThông tin phim: "
                + "\nTên phim: " + movie.getMovieName()
                + "\nPhòng chiếu: " + this.showTime.getRoom().getRoomId()
                + "\nGhế ngồi: " + (!listSeat.isEmpty() ? listSeat : "Không có")
                + "\nGiờ chiếu: " + this.showTime.getOnAirTime().format(CONSTANT.DTF)
                + CONSOLECOLORS.YELLOW
                + "\n--------------------------------------------------------------------------------------"
                + CONSOLECOLORS.RESET;
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

    public Movie getMovie()
    {
        return movie;
    }

    public void setMovie(Movie movie)
    {
        this.movie = movie;
    }

    public ShowTime getShowTime()
    {
        return showTime;
    }

    public void setShowTime(ShowTime showTime)
    {
        this.showTime = showTime;
    }

    public List<String> getSeatNameList()
    {
        return seatNameList;
    }

    public void setSeatNameList(List<String> seatNameList)
    {
        this.seatNameList = seatNameList;
    }

    public LocalDateTime getTimePurchased()
    {
        return timePurchased;
    }

    public void setTimePurchased(LocalDateTime timePurchased)
    {
        this.timePurchased = timePurchased;
    }
}
