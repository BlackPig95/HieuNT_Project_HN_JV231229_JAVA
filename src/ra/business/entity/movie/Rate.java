package ra.business.entity.movie;

public class Rate
{
    private String rateId;
    private String movieId;
    private String userId;
    private byte rate;
    private String comment;

    public Rate()
    {
    }

    public Rate(String rateId, String movieId, String userId, byte rate, String comment)
    {
        this.rateId = rateId;
        this.movieId = movieId;
        this.userId = userId;
        this.rate = rate;
        this.comment = comment;
    }

    public String getRateId()
    {
        return rateId;
    }

    public void setRateId(String rateId)
    {
        this.rateId = rateId;
    }

    public String getMovieId()
    {
        return movieId;
    }

    public void setMovieId(String movieId)
    {
        this.movieId = movieId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public byte getRate()
    {
        return rate;
    }

    public void setRate(byte rate)
    {
        this.rate = rate;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }
}
