package ra.business.entity.user;

import java.time.LocalDate;
import java.util.List;

public class History
{
    private String historyId;
    private List<String> movieId;
    private LocalDate createdAt;

    public History()
    {
    }

    public History(String historyId, List<String> movieId, LocalDate createdAt)
    {
        this.historyId = historyId;
        this.movieId = movieId;
        this.createdAt = createdAt;
    }

    public String getHistoryId()
    {
        return historyId;
    }

    public void setHistoryId(String historyId)
    {
        this.historyId = historyId;
    }

    public List<String> getMovieId()
    {
        return movieId;
    }

    public void setMovieId(List<String> movieId)
    {
        this.movieId = movieId;
    }

    public LocalDate getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt)
    {
        this.createdAt = createdAt;
    }
}
