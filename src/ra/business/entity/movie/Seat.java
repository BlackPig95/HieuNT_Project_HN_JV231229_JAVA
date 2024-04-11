package ra.business.entity.movie;

import ra.business.entity.enumclasses.SEAT_STATUS;

import java.io.Serializable;

public class Seat implements Serializable
{
    private String seatName;
    private SEAT_STATUS seatStatus = SEAT_STATUS.AVAILABLE;
    private String userId;

    public Seat()
    {
    }

    public Seat(String seatName, SEAT_STATUS seatStatus, String userId)
    {
        this.seatName = seatName;
        this.seatStatus = seatStatus;
        this.userId = userId;
    }

    public String getSeatName()
    {
        return seatName;
    }

    public void setSeatName(String seatName)
    {
        this.seatName = seatName;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public SEAT_STATUS getSeatStatus()
    {
        return seatStatus;
    }

    public void setSeatStatus(SEAT_STATUS seatStatus)
    {
        this.seatStatus = seatStatus;
    }
}
