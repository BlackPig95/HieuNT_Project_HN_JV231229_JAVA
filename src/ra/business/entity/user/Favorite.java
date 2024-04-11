package ra.business.entity.user;

import java.util.List;

public class Favorite
{
    private String favoriteId;
    private String userId;
    private List<String> movieId;

    public Favorite()
    {
    }

    public Favorite(String favoriteId, String userId, List<String> movieId)
    {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.movieId = movieId;
    }

    public String getFavoriteId()
    {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId)
    {
        this.favoriteId = favoriteId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public List<String> getMovieId()
    {
        return movieId;
    }

    public void setMovieId(List<String> movieId)
    {
        this.movieId = movieId;
    }
}
