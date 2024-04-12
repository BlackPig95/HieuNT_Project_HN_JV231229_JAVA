package ra.business.implementation;

import ra.business.config.*;
import ra.business.design.IDeletable;
import ra.business.entity.movie.Room;
import ra.business.entity.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ra.business.implementation.ShowTimeManagement.showTimeList;

public class RoomManagement implements IDeletable
{
    static List<Room> roomList;

    public RoomManagement()
    {
        File file = new File(IOFile.ROOM_PATH);
        if (file.length() == 0)
        {
            roomList = new ArrayList<>();
            IOFile.writeToFile(file, roomList);
        } else
        {
            roomList = IOFile.readFromFile(IOFile.ROOM_PATH);
        }
    }

    @Override
    public void addItem()
    {
        System.out.println("Nhập số lượng phòng chiếu muốn thêm mới");
        byte n = InputMethods.nextByte();
        for (int i = 0; i < n; i++)
        {
            Room newRoom = new Room();
            newRoom.inputData(roomList, true);
            roomList.add(newRoom);
            System.out.println(CONSOLECOLORS.GREEN + "Thêm phòng chiếu thành công" + CONSOLECOLORS.RESET);
        }
        IOFile.writeToFile(IOFile.ROOM_PATH, roomList);
    }

    @Override
    public void updateItem()
    {
        int indexUpdate = getIndexById();
        if (indexUpdate == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phòng chiếu" + CONSOLECOLORS.RESET);
            return;
        }
        while (true)
        {
            System.out.println("Đang cập nhật thông tin của phòng chiếu số: " + roomList.get(indexUpdate).getRoomId());
            System.out.println("1. Cập nhật mã số phòng");
            System.out.println("2. Cập nhật số lượng ghế trong phòng");
            System.out.println("3. Cập nhật tất cả thông tin");
            System.out.println("0. Thoát");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    roomList.get(indexUpdate).inputRoomId(roomList, false);
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thông tin phòng chiếu thành công" + CONSOLECOLORS.RESET);
                    break;
                case 2:
                    roomList.get(indexUpdate).inputRoomSeat();
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thông tin phòng chiếu thành công" + CONSOLECOLORS.RESET);
                    break;
                case 3:
                    System.out.println("Mời nhập thông tin mới cho phòng chiếu");
                    roomList.get(indexUpdate).inputData(roomList, false);
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thông tin phòng chiếu thành công" + CONSOLECOLORS.RESET);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
            IOFile.writeToFile(IOFile.ROOM_PATH, roomList);
        }
    }

    @Override
    public void findItem()
    {
        int indexSearch = getIndexById();
        if (indexSearch == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phòng chiếu" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Thông tin của phòng chiếu được tìm thấy:");
        roomList.get(indexSearch).displayData();
    }

    @Override
    public void displayAllItem(User currentUser)
    {
        if (roomList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Hiện không có phòng chiếu nào để hiển thị" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Danh sách các phòng chiếu hiện có:");
        AdminPagination.pagination(roomList);
    }

    @Override
    public int getIndexById()
    {
        if (roomList.isEmpty())
            return -1;
        System.out.println("Nhập mã phòng chiếu cần tìm:");
        byte searchId = InputMethods.nextByte();
        for (int i = 0; i < roomList.size(); i++)
        {
            if (searchId == roomList.get(i).getRoomId())
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
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy phòng chiếu" + CONSOLECOLORS.RESET);
            return;
        }   //So sánh qua Id để không bị sai reference
        if (showTimeList.stream().anyMatch(s -> s.getRoom().getRoomId() == roomList.get(indexDelete).getRoomId()))
        {
            System.out.println(CONSOLECOLORS.RED +
                    "Không thể xóa phòng chiếu, vì vẫn còn lịch chiếu phim ở phòng này" + CONSOLECOLORS.RESET);
            return;
        }
        roomList.remove(indexDelete);
        System.out.println(CONSOLECOLORS.GREEN + "Xóa phòng chiếu thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.ROOM_PATH, roomList);
    }
}
