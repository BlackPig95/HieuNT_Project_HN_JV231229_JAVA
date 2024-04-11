package ra.business.entity.movie;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.entity.category.MovieCategory;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Movie implements Serializable
{
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private enum MOVIE_RATING
    {
        G("General"),
        PG13("PG-13"),
        R("Restricted");
        final String ratingName;

        MOVIE_RATING(String ratingName)
        {
            this.ratingName = ratingName;
        }
    }

    private String movieId;
    private String movieName;
    private String description;
    private String trailerUrl;
    private String imageUrl;
    private boolean isSpotlight;
    private String movieCategoryId;
    private MOVIE_RATING movieRating;
    private List<String> showTimeId;
    private String duration;

    public Movie()
    {
        showTimeId = new ArrayList<>();
    }

    public Movie(String movieId, String movieName, String description, String trailerUrl, String imageUrl, boolean isSpotlight, String movieCategoryId, MOVIE_RATING movieRating, List<String> showTimeId, String duration)
    {
        this.movieId = movieId;
        this.movieName = movieName;
        this.description = description;
        this.trailerUrl = trailerUrl;
        this.imageUrl = imageUrl;
        this.isSpotlight = isSpotlight;
        this.movieCategoryId = movieCategoryId;
        this.movieRating = movieRating;
        this.showTimeId = showTimeId;
        this.duration = duration;
    }

    public void inputData(List<Movie> movieList, List<MovieCategory> movieCategoryList, List<ShowTime> showTimeList, boolean isAdding)
    {
        if (isAdding)
        {
            inputMovieId(movieList);
        }
        inputMovieName(movieList);
        inputMovieDescription();
        inputMovieTrailer();
        inputMovieImage();
        inputMovieSpotlight();
        inputCategory(movieCategoryList);
        inputMovieRating();
        inputMovieDuration();
        inputMovieShowTime(showTimeList);
    }

    public void displayData(List<ShowTime> showTimeList)
    {
        StringBuilder onShowTimeList = new StringBuilder();
        if (!showTimeId.isEmpty())
        {   //Tránh null pointer nếu các lịch chiếu trước đó đều bị xóa
            onShowTimeList = getShowInfoFromId(showTimeList);
        }
        System.out.println(CONSOLECOLORS.YELLOW + "=====================================================================================================================" + CONSOLECOLORS.RESET);
        System.out.printf("Mã phim: %s | Tên phim: %s | Thời lượng: %s | Phân loại: %s \n",
                this.movieId, this.movieName, this.duration, this.movieRating);
        System.out.printf("Lịch chiếu: %s \n", !onShowTimeList.isEmpty() ? String.valueOf(onShowTimeList) : "Sắp ra mắt");
        System.out.println(CONSOLECOLORS.YELLOW + "=====================================================================================================================" + CONSOLECOLORS.RESET);
    }

    private StringBuilder getShowInfoFromId(List<ShowTime> showTimeList)
    {
        StringBuilder onShowTimeList = new StringBuilder();
        for (String s : showTimeId)
        {   //Lấy ra object lịch chiếu
            //rồi truy cập vào trường thể hiện thời gian chiếu và phòng chiếu để nối chuỗi
            ShowTime show = getShowTimeFromId(showTimeList, s);
            onShowTimeList.append(show.getShowTimeId()).append(" - ");
            onShowTimeList.append("Ngày: ").append(dateTimeFormatter.format(show.getOnAirTime()));
            onShowTimeList.append(" Phòng: ").append(show.getRoomId());
            onShowTimeList.append(CONSOLECOLORS.BLUE + " | " + CONSOLECOLORS.RESET);
        }
        return onShowTimeList;
    }

    public ShowTime getShowTimeFromId(List<ShowTime> showTimeList, String _showTimeId)
    {
        return showTimeList.stream().filter(s -> s.getShowTimeId().equals(_showTimeId)).findFirst().orElse(null);
    }

    private void inputMovieName(List<Movie> movieList)
    {
        while (true)
        {
            System.out.println("Nhập tên của bộ phim");
            String inputName = InputMethods.nextLine();
            //Lấy ra list tên các bộ phim đã có
            List<String> nameList = movieList.stream().map(m -> m.movieName).toList();
            boolean nameExisted = false;
            for (String name : nameList)
            {
                if (name.equals(inputName))
                {
                    System.out.println(CONSOLECOLORS.RED + "Tên bộ phim này đã được sử dụng. " + CONSTANT.INPUT_AGAIN + CONSOLECOLORS.RESET);
                    nameExisted = true;
                    break;
                }
            }
            if (!nameExisted)//tên chưa tồn tại thì mới cho sử dụng
            {
                this.movieName = inputName;
                break;
            }
        }
    }

    private void inputMovieDescription()
    {
        System.out.println("Nhập mô tả cho bộ phim");
        this.description = InputMethods.nextLine();
    }

    private void inputMovieTrailer()
    {
        System.out.println("Nhập đường dẫn trailer của phim");
        this.trailerUrl = InputMethods.nextLine();
    }

    private void inputMovieImage()
    {
        System.out.println("Nhập đường dẫn cho poster của phim");
        this.imageUrl = InputMethods.nextLine();
    }

    private void inputMovieSpotlight()
    {
        System.out.println("Bộ phim này có phải phim hot không? (Nhập chính xác true hoặc false)");
        this.isSpotlight = InputMethods.nextBoolean();
    }

    private void inputCategory(List<MovieCategory> movieCategoryList)
    {
        System.out.println("Danh sách các thể loại phim hiện có:");
        movieCategoryList.forEach(m -> m.displayData());
        while (true)
        {
            System.out.println("Lựa chọn thể loại cho phim này từ danh sách hiện có bằng cách nhập mã thể loại");
            String genreChoice = InputMethods.nextLine();
            for (MovieCategory genre : movieCategoryList)
            {
                if (genre.getCategoryId().equals(genreChoice))
                {
                    this.movieCategoryId = genreChoice;
                    return;
                }
            }
            System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
        }
    }

    private void inputMovieRating()
    {
        while (true)
        {
            System.out.println("Phân loại của phim này là:");
            System.out.println("1. G: Dành cho mọi lứa tuổi");
            System.out.println("2. PG-13: Dành cho người trên 13 tuổi");
            System.out.println("3. R: Dành cho khán giả trên 18 tuổi");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    this.movieRating = MOVIE_RATING.G;
                    return;
                case 2:
                    this.movieRating = MOVIE_RATING.PG13;
                    return;
                case 3:
                    this.movieRating = MOVIE_RATING.R;
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void inputMovieDuration()
    {
        System.out.println("Thời lượng của bộ phim này:");
        while (true)
        {
            System.out.println("Nhập số giờ:");
            byte hour = InputMethods.nextByte();
            if (hour <= 0 || hour > 5)
            {
                System.out.println(CONSOLECOLORS.RED + "Số giờ chiếu phải lớn hơn 0 và nhỏ hơn 5" + CONSOLECOLORS.RESET);
                continue;
            }
            while (true)
            {
                System.out.println("Nhập số phút:");
                byte minutes = InputMethods.nextByte();
                if (minutes >= 60 || minutes < 0)
                {
                    System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập số phút trong khoảng 0-59" + CONSOLECOLORS.RESET);
                    continue;
                }
                //Nối giờ và phút lại với định dạng hh:mm
                this.duration = String.format("%02d", hour) + ":" + String.format("%02d", minutes);
                return;
            }
        }
    }

    private void inputMovieShowTime(List<ShowTime> showTimeList)
    {
        while (true)
        {
            System.out.println("Danh sách các lịch chiếu hiện có:");
            showTimeList.forEach(s -> s.displayData());
            System.out.println("Thêm lịch chiếu của phim này bằng cách nhập mã lịch chiếu:");
            System.out.println("Nhập chính xác 'esc' khi muốn ngừng thêm lịch chiếu cho phim này");
            String timeChoice = InputMethods.nextLine();
            if (timeChoice.equals("esc") && showTimeId.size() == 0)
            {
                System.out.println(CONSOLECOLORS.RED + "Vui lòng chọn ít nhất một lịch chiếu" + CONSOLECOLORS.RESET);
                continue;
            } else if (timeChoice.equals("esc") && showTimeId.size() > 0)
            {
                return;
            }
            ShowTime chosenTime = showTimeList.stream().filter(s -> s.getShowTimeId().equals(timeChoice)).findFirst().orElse(null);
            if (chosenTime == null)
            {
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
            } else
            {
                boolean alreadyChosen = false;
                for (String time : this.showTimeId)
                {
                    if (chosenTime.getShowTimeId().equals(time))
                    {
                        System.out.println(CONSOLECOLORS.RED + "Lịch chiếu này đã được chọn" + CONSOLECOLORS.RESET);
                        alreadyChosen = true;
                        break;
                    }
                }
                if (!alreadyChosen)
                {
                    this.showTimeId.add(chosenTime.getShowTimeId());
                    System.out.println(CONSOLECOLORS.GREEN + "Thêm lịch chiếu thành công" + CONSOLECOLORS.RESET);
                }
            }
        }
    }

    private void inputMovieId(List<Movie> movieList)
    {
        this.movieId = "M" + String.format("%05d", getIntPartOfMovieId(movieList));
    }

    private int getIntPartOfMovieId(List<Movie> movieList)
    {
        if (movieList.isEmpty())//Nếu movieList null => Chưa có phần tử => Lấy movieId là 0
            return 0;
        //Tách phần chữ số của movieId ra, so sánh và tìm ra Id lớn nhất => Lấy ra movie này
        Movie movieMaxId = movieList.stream().max(Comparator.comparingInt(m ->
                Integer.parseInt(m.movieId.substring(1)))).orElse(null);
        //Trả về movieId hiện tại + thêm 1
        return Integer.parseInt(movieMaxId.movieId.substring(1)) + 1;
    }

    public MovieCategory getCategoryInfoById(List<MovieCategory> movieCategoryList)
    {
        return movieCategoryList.stream().filter(m -> m.getCategoryId().equals(this.movieCategoryId)).
                findFirst().orElse(null);
    }

    //Getter Setter
    public String getMovieId()
    {
        return movieId;
    }

    public void setMovieId(String movieId)
    {
        this.movieId = movieId;
    }

    public String getMovieName()
    {
        return movieName;
    }

    public void setMovieName(String movieName)
    {
        this.movieName = movieName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getTrailerUrl()
    {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl)
    {
        this.trailerUrl = trailerUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public boolean isSpotlight()
    {
        return isSpotlight;
    }

    public void setSpotlight(boolean spotlight)
    {
        isSpotlight = spotlight;
    }

    public String getMovieCategoryId()
    {
        return movieCategoryId;
    }

    public void setMovieCategoryId(String movieCategoryId)
    {
        this.movieCategoryId = movieCategoryId;
    }

    public MOVIE_RATING getMovieRating()
    {
        return movieRating;
    }

    public void setMovieRating(MOVIE_RATING movieRating)
    {
        this.movieRating = movieRating;
    }

    public List<String> getShowTimeId()
    {
        return showTimeId;
    }

    public void setShowTimeId(List<String> showTimeId)
    {
        this.showTimeId = showTimeId;
    }

    public String getDuration()
    {
        return duration;
    }

    public void setDuration(String duration)
    {
        this.duration = duration;
    }
}
