package ra.business.implementation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.design.IDeletable;
import ra.business.entity.enumclasses.USER_ROLE;
import ra.business.entity.movie.Movie;
import ra.business.entity.movie.ShowTime;
import ra.business.entity.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ra.business.implementation.MovieCategoryManagement.movieCategoryList;
import static ra.business.implementation.ShowTimeManagement.showTimeList;

public class MovieManagement implements IDeletable
{
    static List<Movie> movieList;

    public MovieManagement()
    {
        File file = new File(IOFile.MOVIE_PATH);
        if (file.length() == 0)
        {
            movieList = new ArrayList<>();
            IOFile.writeToFile(file, movieList);
        } else
        {
            movieList = IOFile.readFromFile(IOFile.MOVIE_PATH);
        }
    }

    @Override
    public void addItem()
    {
        if (movieCategoryList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập thông tin một số thể loại trước khi thêm phim mới." + CONSOLECOLORS.RESET);
            return;
        }
        if (showTimeList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập danh sách lịch chiếu trước khi thêm phim" + CONSOLECOLORS.RESET);
            return;
        }
        if (checkAvailableShowTime())//Kiểm tra xem có lịch chiếu nào còn trống không
        {
            System.out.println(CONSOLECOLORS.RED + "Không còn lịch chiếu nào trống. Vui lòng thêm lịch chiếu trước khi thêm phim mới" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Nhập số lượng phim muốn thêm mới");
        byte n = InputMethods.nextByte();
        for (int i = 0; i < n; i++)
        {
            if (checkAvailableShowTime())//Kiểm tra xem có lịch chiếu nào còn trống không
            {
                System.out.println(CONSOLECOLORS.RED + "Không còn lịch chiếu nào trống. Vui lòng thêm lịch chiếu trước khi thêm phim mới" + CONSOLECOLORS.RESET);
                return;
            }
            Movie newMovie = new Movie();
            newMovie.inputData(movieList, movieCategoryList, showTimeList, true);
            movieList.add(newMovie);
            System.out.println(CONSOLECOLORS.GREEN + "Thêm phim thành công" + CONSOLECOLORS.RESET);
            //Đảm bảo không xảy ra tình trạng status isTaken của showTime không được cập nhật
            //Đảm bảo phim sẽ được ghi sau mỗi lần thêm mới
            IOFile.writeToFile(IOFile.SHOW_TIME_PATH, showTimeList);
            IOFile.writeToFile(IOFile.MOVIE_PATH, movieList);
        }
    }

    @Override
    public void updateItem()
    {
        int indexUpdate = getIndexById();
        if (indexUpdate == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phim" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Mời nhập thông tin mới cho phim");
        movieList.get(indexUpdate).inputData(movieList, movieCategoryList, showTimeList, false);
        System.out.println(CONSOLECOLORS.GREEN + "Cập nhật phim thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.MOVIE_PATH, movieList);
    }

    @Override
    public void findItem()
    {
        System.out.println("Nhập 1 để tìm kiếm theo tên phim, nhập 2 để tìm kiếm theo thể loại phim:");
        byte choice = InputMethods.nextByte();
        switch (choice)
        {
            case 1:
                searchMovieByName();
                break;
            case 2:
                searchMovieByCategory();
                break;
            default:
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                break;
        }
    }

    @Override
    public void displayAllItem(User currentUser)
    {
        if (movieList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Hiện không có phim nào để hiển thị" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Danh sách các phim hiện có:");
        if (currentUser == null || currentUser.getRole() == USER_ROLE.USER)
        {
            movieList.forEach(m -> m.displayBasicData(showTimeList));
        } else
        {
            movieList.forEach(m -> m.displayData(showTimeList));
        }
    }

    @Override
    public int getIndexById()
    {
        if (movieList.isEmpty())
            return -1;
        System.out.println("Nhập mã phim cần tìm:");
        String searchId = InputMethods.nextLine();
        for (int i = 0; i < movieList.size(); i++)
        {
            if (searchId.equals(movieList.get(i).getMovieId()))
                return i;
        }
        return -1;
    }

    @Override
    public void deleteItem()
    {
        int indexDelete = getIndexById();
        if (indexDelete == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phim" + CONSOLECOLORS.RESET);
            return;
        }
        movieList.remove(indexDelete);
        System.out.println(CONSOLECOLORS.GREEN + "Xóa phim thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.MOVIE_PATH, movieList);
    }

    private void searchMovieByName()
    {
        System.out.println("Hãy nhập tên của phim mà bạn muốn tìm kiếm:");
        String nameSearch = InputMethods.nextLine().toLowerCase();
        List<Movie> movieFoundList = movieList.stream().filter(m -> m.getMovieName().toLowerCase().contains(nameSearch)).toList();
        if (movieFoundList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phim" + CONSOLECOLORS.RESET);
        } else
        {
            System.out.println("Danh sách các phim có tên giống với mô tả:");
            movieFoundList.forEach(m -> m.displayData(showTimeList));
        }
    }

    private void searchMovieByCategory()
    {
        System.out.println("Hãy nhập thể loại phim mà bạn muốn tìm kiếm:");
        String categorySearch = InputMethods.nextLine().toLowerCase();
        //Lấy ra đối tượng MovieCategory của mỗi phim, so sánh trường categoryName xem có giống không
        //Không xảy ra null pointer vì mục categry sẽ không cho xóa khi vẫn còn phim
        List<Movie> movieFoundList = movieList.stream().filter(m -> m.getCategoryInfoById(movieCategoryList).
                getCategoryName().toLowerCase().contains(categorySearch)).toList();
        if (movieFoundList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phim nào" + CONSOLECOLORS.RESET);
        } else
        {
            System.out.println("Danh sách các phim có tên giống với mô tả:");
            movieFoundList.forEach(m -> m.displayData(showTimeList));
        }
    }

    private boolean checkAvailableShowTime()
    {
        boolean allShowTimeTaken = true;
        for (ShowTime showTime : showTimeList)
        {
            if (!showTime.isTaken())
            {
                allShowTimeTaken = false;
                break;
            }
        }
        return allShowTimeTaken;
    }
}
