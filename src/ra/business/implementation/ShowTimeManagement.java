package ra.business.implementation;

import ra.business.config.AdminPagination;
import ra.business.config.CONSOLECOLORS;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.design.IDeletable;
import ra.business.entity.movie.Movie;
import ra.business.entity.movie.ShowTime;
import ra.business.entity.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ra.business.implementation.RoomManagement.roomList;
import static ra.business.implementation.MovieManagement.movieList;

public class ShowTimeManagement implements IDeletable
{
    static List<ShowTime> showTimeList;

    public ShowTimeManagement()
    {
        File file = new File(IOFile.SHOW_TIME_PATH);
        if (file.length() == 0)
        {
            showTimeList = new ArrayList<>();
            IOFile.writeToFile(file, showTimeList);
        } else
        {
            showTimeList = IOFile.readFromFile(IOFile.SHOW_TIME_PATH);
        }
    }

    @Override
    public void addItem()
    {
        if (roomList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Vui lòng tạo các phòng chiếu trước khi thêm lịch chiếu." + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Nhập số lượng lịch chiếu muốn thêm mới");
        byte n = InputMethods.nextByte();
        for (int i = 0; i < n; i++)
        {
            ShowTime newShowTime = new ShowTime();
            newShowTime.inputData(roomList, showTimeList, true);
            showTimeList.add(newShowTime);
            System.out.println(CONSOLECOLORS.GREEN + "Thêm lịch chiếu thành công" + CONSOLECOLORS.RESET);
        }
        IOFile.writeToFile(IOFile.SHOW_TIME_PATH, showTimeList);
    }

    @Override
    public void updateItem()
    {
        int indexUpdate = getIndexById();
        if (indexUpdate == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy lịch chiếu" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Mời nhập thông tin mới cho lịch chiếu");
        showTimeList.get(indexUpdate).inputData(roomList, showTimeList, false);
        System.out.println(CONSOLECOLORS.GREEN + "Cập nhật lịch chiếu thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.SHOW_TIME_PATH, showTimeList);
    }

    @Override
    public void findItem()
    {
        int indexSearch = getIndexById();
        if (indexSearch == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy lịch chiếu" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Thông tin của lịch chiếu được tìm thấy:");
        showTimeList.get(indexSearch).displayData();
    }

    @Override
    public void displayAllItem(User currentUser)
    {
        if (showTimeList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Hiện không có lịch chiếu nào để hiển thị" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Danh sách các lịch chiếu hiện có:");
        AdminPagination.pagination(showTimeList);
    }

    @Override
    public int getIndexById()
    {
        if (showTimeList.isEmpty())
            return -1;
        System.out.println("Nhập mã lịch chiếu cần tìm:");
        String searchId = InputMethods.nextLine();
        for (int i = 0; i < showTimeList.size(); i++)
        {
            if (searchId.equals(showTimeList.get(i).getShowTimeId()))
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
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy lịch chiếu" + CONSOLECOLORS.RESET);
            return;
        }
        String idDeleted = showTimeList.get(indexDelete).getShowTimeId();
        for (Movie movie : movieList)
        {//Duyệt qua list các phim xem có lịch chiếu nào đang được sử dụng cho phim này không
            for (String id : movie.getShowTimeId())
            {   //Nếu có thì xóa thông tin của lịch chiếu đó trong đối tượng Movie
                //Đảm bảo các đối tượng liên hệ với nhau được cập nhật cùng lúc
                if (id.equals(idDeleted))
                {
                    movie.getShowTimeId().remove(id);
                }
            }
        }
        showTimeList.remove(indexDelete);
        System.out.println(CONSOLECOLORS.GREEN + "Xóa lịch chiếu thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.SHOW_TIME_PATH, showTimeList);
    }
}
