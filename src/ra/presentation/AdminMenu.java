package ra.presentation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.design.ICategoryDesign;
import ra.business.design.IDeletable;
import ra.business.design.IUserDesign;
import ra.business.implementation.*;

public class AdminMenu
{
    private static final ICategoryDesign movieCategoryManagement = new MovieCategoryManagement();
    static final IDeletable movieManagement = new MovieManagement();
    private static final IDeletable showTimeManagement = new ShowTimeManagement();
    static final IUserDesign userManagement = new UserManagement();
    private static final IDeletable roomManagement = new RoomManagement();
    private static final IDeletable snackManagement = new SnackManagement();

    public void displayAdminMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Quản lý người dùng
                    2. Quản lý thể loại phim
                    3. Quản lý phòng chiếu
                    4. Quản lý lịch chiếu
                    5. Quản lý danh sách phim
                    6. Quản lý danh sách đồ ăn/ đồ uống tại rạp
                    0. Đăng xuất
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    displayUserManageMenu();
                    break;
                case 2:
                    displayCategoryManageMenu();
                    break;
                case 3:
                    displayRoomManageMenu();
                    break;
                case 4:
                    displayShowTimeManageMenu();
                    break;
                case 5:
                    displayMovieManageMenu();
                    break;
                case 6:
                    displaySnackManageMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayUserManageMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Thêm người dùng mới
                    2. Cập nhật trạng thái người dùng
                    3. Tìm kiếm người dùng
                    4. Hiển thị danh sách người dùng hiện có
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    userManagement.addItem();
                    break;
                case 2:
                    userManagement.updateItem();
                    break;
                case 3:
                    userManagement.findItem();
                    break;
                case 4:
                    userManagement.displayAllItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayCategoryManageMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Thêm thể loại phim mới
                    2. Cập nhật thông tin thể loại phim
                    3. Tìm kiếm thể loại phim
                    4. Hiển thị danh sách thể loại phim hiện có
                    5. Xóa thể loại
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    movieCategoryManagement.addItem();
                    break;
                case 2:
                    movieCategoryManagement.updateItem();
                    break;
                case 3:
                    movieCategoryManagement.findItem();
                    break;
                case 4:
                    movieCategoryManagement.displayAllItem();
                    break;
                case 5:
                    movieCategoryManagement.deleteItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayMovieManageMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Thêm phim mới
                    2. Cập nhật thông tin phim
                    3. Tìm kiếm phim
                    4. Hiển thị danh sách phim hiện có
                    5. Xóa phim
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    movieManagement.addItem();
                    break;
                case 2:
                    movieManagement.updateItem();
                    break;
                case 3:
                    movieManagement.findItem();
                    break;
                case 4:
                    movieManagement.displayAllItem();
                    break;
                case 5:
                    movieManagement.deleteItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayShowTimeManageMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Thêm lịch chiếu mới
                    2. Cập nhật thông tin lịch chiếu
                    3. Tìm kiếm lịch chiếu
                    4. Hiển thị danh sách lịch chiếu hiện có
                    5. Xóa lịch chiếu
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    showTimeManagement.addItem();
                    break;
                case 2:
                    showTimeManagement.updateItem();
                    break;
                case 3:
                    showTimeManagement.findItem();
                    break;
                case 4:
                    showTimeManagement.displayAllItem();
                    break;
                case 5:
                    showTimeManagement.deleteItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayRoomManageMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Thêm phòng chiếu mới
                    2. Cập nhật thông tin phòng chiếu
                    3. Tìm kiếm phòng chiếu
                    4. Hiển thị danh sách phòng chiếu hiện có
                    5. Xóa phòng chiếu
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    roomManagement.addItem();
                    break;
                case 2:
                    roomManagement.updateItem();
                    break;
                case 3:
                    roomManagement.findItem();
                    break;
                case 4:
                    roomManagement.displayAllItem();
                    break;
                case 5:
                    roomManagement.deleteItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displaySnackManageMenu()
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG ADMIN********************
                    1. Thêm đồ ăn/ đồ uống mới
                    2. Cập nhật thông tin đồ ăn/ đồ uống
                    3. Tìm kiếm đồ ăn/ đồ uống
                    4. Hiển thị danh sách đồ ăn/ đồ uống hiện có
                    5. Xóa đồ ăn/ đồ uống
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    snackManagement.addItem();
                    break;
                case 2:
                    snackManagement.updateItem();
                    break;
                case 3:
                    snackManagement.findItem();
                    break;
                case 4:
                    snackManagement.displayAllItem();
                    break;
                case 5:
                    snackManagement.deleteItem();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }
}
