package ra.presentation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.design.ICategoryDesign;
import ra.business.design.IDeletable;
import ra.business.design.IUserDesign;
import ra.business.entity.user.User;
import ra.business.implementation.*;

public class AdminMenu
{
    private static final ICategoryDesign movieCategoryManagement = new MovieCategoryManagement();
    static final IDeletable movieManagement = new MovieManagement();
    private static final IDeletable showTimeManagement = new ShowTimeManagement();
    static final IUserDesign userManagement = new UserManagement();
    private static final IDeletable roomManagement = new RoomManagement();
    private static final IDeletable snackManagement = new SnackManagement();

    public void displayAdminMenu(User currentUser)
    {
        while (true)
        {

            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.println(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  ==================  CHÀO MỪNG ADMIN  ===================  ┃\s
                    ┃  1. Quản lý người dùng                                     ┃\s
                    ┃  2. Quản lý thể loại phim                                  ┃\s
                    ┃  3. Quản lý phòng chiếu                                    ┃\s
                    ┃  4. Quản lý lịch chiếu                                     ┃\s
                    ┃  5. Quản lý danh sách phim                                 ┃\s
                    ┃  6. Quản lý danh sách đồ ăn/ đồ uống tại rạp               ┃\s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Đăng xuất                                              ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.print(CONSOLECOLORS.RESET);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    displayUserManageMenu(currentUser);
                    break;
                case 2:
                    displayCategoryManageMenu(currentUser);
                    break;
                case 3:
                    displayRoomManageMenu(currentUser);
                    break;
                case 4:
                    displayShowTimeManageMenu(currentUser);
                    break;
                case 5:
                    displayMovieManageMenu(currentUser);
                    break;
                case 6:
                    displaySnackManageMenu(currentUser);
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    IOFile.writeObject(IOFile.USER_LOGIN, null);
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayUserManageMenu(User currentUser)
    {
        while (true)
        {
            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.println(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  ================  QUẢN LÝ NGƯỜI DÙNG  ==================  ┃\s
                    ┃  1. Thêm người dùng mới                                    ┃\s
                    ┃  2. Cập nhật trạng thái người                              ┃\s
                    ┃  3. Tìm kiếm người dùng                                    ┃\s
                    ┃  4. Hiển thị danh sách người dùng hiện                     ┃\s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Quay lại                                               ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.print(CONSOLECOLORS.RESET);
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
                    userManagement.displayAllItem(currentUser);
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayCategoryManageMenu(User currentUser)
    {
        while (true)
        {
            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.println(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  ===============  QUẢN LÝ THỂ LOẠI PHIM  ================  ┃\s
                    ┃  1. Thêm thể loại phim mới                                 ┃\s
                    ┃  2. Cập nhật thông tin thể loại phim                       ┃\s
                    ┃  3. Tìm kiếm thể loại phim                                 ┃\s
                    ┃  4. Hiển thị danh sách thể loại phim hiện có               ┃\s
                    ┃  5. Xóa thể loại                                           ┃\s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Quay lại                                               ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.print(CONSOLECOLORS.RESET);
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
                    movieCategoryManagement.displayAllItem(currentUser);
                    break;
                case 5:
                    movieCategoryManagement.deleteItem();
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayMovieManageMenu(User currentUser)
    {
        while (true)
        {
            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.println(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  ===================  QUẢN LÝ PHIM  =====================  ┃\s
                    ┃  1. Thêm phim mới                                          ┃\s
                    ┃  2. Cập nhật thông tin phim                                ┃\s
                    ┃  3. Tìm kiếm phim                                          ┃\s
                    ┃  4. Hiển thị danh sách phim hiện có                        ┃\s
                    ┃  5. Xóa phim                                               ┃\s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Quay lại                                               ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.print(CONSOLECOLORS.RESET);
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
                    movieManagement.displayAllItem(currentUser);
                    break;
                case 5:
                    movieManagement.deleteItem();
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayShowTimeManageMenu(User currentUser)
    {
        while (true)
        {
            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.print(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  =================  QUẢN LÝ LỊCH CHIẾU  =================  ┃  \s
                    ┃  1. Thêm lịch chiếu mới                                    ┃           \s
                    ┃  2. Cập nhật thông tin lịch chiếu                          ┃      \s
                    ┃  3. Tìm kiếm lịch chiếu                                    ┃
                    ┃  4. Hiển thị danh sách lịch chiếu hiện có                  ┃  \s
                    ┃  5. Xóa lịch chiếu                                         ┃  \s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Quay lại                                               ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.println(CONSOLECOLORS.RESET);
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
                    showTimeManagement.displayAllItem(currentUser);
                    break;
                case 5:
                    showTimeManagement.deleteItem();
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayRoomManageMenu(User currentUser)
    {
        while (true)
        {
            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.print(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  =================  QUẢN LÝ PHÒNG CHIẾU  ================= ┃  \s
                    ┃  1. Thêm phòng chiếu mới                                   ┃           \s
                    ┃  2. Cập nhật thông tin phòng chiếu                         ┃      \s
                    ┃  3. Tìm kiếm phòng chiếu                                   ┃
                    ┃  4. Hiển thị danh sách phòng chiếu hiện có                 ┃  \s
                    ┃  5. Xóa phòng chiếu                                        ┃  \s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Quay lại                                               ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.println(CONSOLECOLORS.RESET);
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
                    roomManagement.displayAllItem(currentUser);
                    break;
                case 5:
                    roomManagement.deleteItem();
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displaySnackManageMenu(User currentUser)
    {
        while (true)
        {
            System.out.print(CONSOLECOLORS.BLACK_BACKGROUND);
            System.out.print(CONSOLECOLORS.BLUE_BOLD_BRIGHT);
            System.out.print("""
                    ┏────────────────────────────────────────────────────────────┓\s
                    ┃  ==========  QUẢN LÝ DANH SÁCH ĐỒ ĂN/ ĐỒ UỐNG  =========== ┃  \s
                    ┃  1. Thêm đồ ăn/ đồ uống mới                                ┃           \s
                    ┃  2. Cập nhật thông tin đồ ăn/ đồ uống                      ┃      \s
                    ┃  3. Tìm kiếm đồ ăn/ đồ uống                                ┃
                    ┃  4. Hiển thị danh sách đồ ăn/ đồ uống hiện có              ┃  \s
                    ┃  5. Xóa đồ ăn/ đồ uống                                     ┃  \s
                    ┃  9. Thoát khỏi trang                                       ┃
                    ┃  0. Quay lại                                               ┃  \s
                    ┗────────────────────────────────────────────────────────────┛ 	                  \s
                      """);
            System.out.println(CONSOLECOLORS.RESET);
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
                    snackManagement.displayAllItem(currentUser);
                    break;
                case 5:
                    snackManagement.deleteItem();
                    break;
                case 9:
                    System.exit(0);
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }
}
