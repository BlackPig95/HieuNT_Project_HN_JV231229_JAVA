package ra.business.implementation;

import ra.business.config.*;
import ra.business.design.IDeletable;
import ra.business.entity.purchase.Snack;
import ra.business.entity.user.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static ra.business.implementation.RoomManagement.roomList;


public class SnackManagement implements IDeletable
{
    static List<Snack> snackList;

    public SnackManagement()
    {
        File file = new File(IOFile.SNACK_PATH);
        if (file.length() == 0)
        {
            snackList = new ArrayList<>();
            IOFile.writeToFile(file, snackList);
        } else
        {
            snackList = IOFile.readFromFile(IOFile.SNACK_PATH);
        }
    }

    @Override
    public void addItem()
    {
        System.out.println("Nhập số lượng đồ ăn/ đồ uống muốn thêm mới");
        byte n = InputMethods.nextByte();
        for (int i = 0; i < n; i++)
        {
            Snack newSnack = new Snack();
            newSnack.inputData(snackList, true);
            snackList.add(newSnack);
            System.out.println(CONSOLECOLORS.GREEN + "Thêm đồ ăn/ đồ uống thành công" + CONSOLECOLORS.RESET);
        }
        IOFile.writeToFile(IOFile.SNACK_PATH, snackList);
    }

    @Override
    public void updateItem()
    {
        int indexUpdate = getIndexById();
        if (indexUpdate == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy đồ ăn/ đồ uống" + CONSOLECOLORS.RESET);
            return;
        }
        Snack updatedSnack = snackList.get(indexUpdate);
        while (true)
        {
            System.out.println("Đang cập nhật thông tin cho: " + updatedSnack.getSnackName());
            System.out.println("1. Đổi phân loại của đồ ăn/ đồ uống");
            System.out.println("2. Đổi tên");
            System.out.println("3. Đổi giá bán");
            System.out.println("4. Cập nhật tất cả thông tin");
            System.out.println("0. Thoát");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    updatedSnack.inputSnackType();
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
                    break;
                case 2:
                    updatedSnack.inputSnackName(snackList, false);
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
                    break;
                case 3:
                    updatedSnack.inputSnackPrice();
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
                    break;
                case 4:
                    System.out.println("Mời nhập thông tin mới cho đồ ăn/ đồ uống này");
                    updatedSnack.inputData(snackList, false);
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
            IOFile.writeToFile(IOFile.SNACK_PATH, snackList);
        }
    }

    @Override
    public void findItem()
    {
        int indexSearch = getIndexById();
        if (indexSearch == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy đồ ăn/ đồ uống này" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Thông tin của đồ ăn/ đồ uống đã tìm thấy:");
        snackList.get(indexSearch).displayData();
    }

    @Override
    public void displayAllItem(User currentUser)
    {
        if (snackList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Hiện không có đồ ăn/ đồ uống nào để hiển thị" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Danh sách các đồ ăn/ đồ uống hiện có:");
        AdminPagination.pagination(snackList);
    }

    @Override
    public int getIndexById()
    {
        if (snackList.isEmpty())
            return -1;
        System.out.println("Nhập mã đồ ăn/ đồ uống cần tìm:");
        String searchId = InputMethods.nextLine();
        for (int i = 0; i < snackList.size(); i++)
        {
            if (searchId.equals(snackList.get(i).getSnackId()))
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
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy đồ ăn/ đồ uống" + CONSOLECOLORS.RESET);
            return;
        }
        snackList.remove(indexDelete);
        System.out.println(CONSOLECOLORS.GREEN + "Xóa đồ ăn/ đồ uống thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.SNACK_PATH, snackList);
    }
}
