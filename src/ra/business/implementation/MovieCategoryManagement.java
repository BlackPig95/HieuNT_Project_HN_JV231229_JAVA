package ra.business.implementation;

import ra.business.config.AdminPagination;
import ra.business.config.CONSOLECOLORS;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.design.ICategoryDesign;
import ra.business.entity.category.MovieCategory;
import ra.business.entity.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ra.business.implementation.MovieManagement.movieList;

public class MovieCategoryManagement implements ICategoryDesign
{
    static List<MovieCategory> movieCategoryList;

    public MovieCategoryManagement()
    {
        File file = new File(IOFile.MOVIE_CATEGORY_PATH);
        if (file.length() == 0)
        {
            movieCategoryList = new ArrayList<>();
            IOFile.writeToFile(file, movieCategoryList);
        } else
        {
            movieCategoryList = IOFile.readFromFile(IOFile.MOVIE_CATEGORY_PATH);
        }
    }

    @Override
    public void addItem()
    {
        System.out.println("Nhập số lượng thể loại phim muốn thêm mới");
        byte n = InputMethods.nextByte();
        for (int i = 0; i < n; i++)
        {
            MovieCategory newGenre = new MovieCategory();
            newGenre.inputData(movieCategoryList, true);
            movieCategoryList.add(newGenre);
            System.out.println(CONSOLECOLORS.GREEN + "Thêm thể loại thành công" + CONSOLECOLORS.RESET);
        }
        IOFile.writeToFile(IOFile.MOVIE_CATEGORY_PATH, movieCategoryList);
    }

    @Override
    public void updateItem()
    {
        int indexUpdate = getIndexById();
        if (indexUpdate == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy thể loại" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Mời nhập thông tin mới cho thể loại");
        movieCategoryList.get(indexUpdate).inputData(movieCategoryList, false);
        System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thể loại thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.MOVIE_CATEGORY_PATH, movieCategoryList);
    }

    @Override
    public void findItem()
    {
        int indexSearch = getIndexById();
        if (indexSearch == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy thể loại" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Thông tin của thể loại đã tìm thấy:");
        movieCategoryList.get(indexSearch).displayData();
    }

    @Override
    public void displayAllItem(User currentUser)
    {
        if (movieCategoryList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Hiện không có thể loại nào để hiển thị" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Danh sách các thể loại hiện có:");
        AdminPagination.pagination(movieCategoryList);
    }

    @Override
    public int getIndexById()
    {
        if (movieCategoryList.isEmpty())
            return -1;
        System.out.println("Nhập mã thể loại cần tìm:");
        String searchId = InputMethods.nextLine();
        for (int i = 0; i < movieCategoryList.size(); i++)
        {
            if (searchId.equals(movieCategoryList.get(i).getCategoryId()))
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
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy thể loại" + CONSOLECOLORS.RESET);
            return;
        }
        if (movieList.stream().anyMatch(m ->
                m.getMovieCategoryId().equals(movieCategoryList.get(indexDelete).getCategoryId())))
        {
            System.out.println(CONSOLECOLORS.RED + "Không thể xóa thể loại, vì vẫn còn phim thuộc thể loại này" + CONSOLECOLORS.RESET);
            return;
        }
        movieCategoryList.remove(indexDelete);
        System.out.println(CONSOLECOLORS.GREEN + "Xóa thể loại thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.MOVIE_CATEGORY_PATH, movieCategoryList);
    }
}
