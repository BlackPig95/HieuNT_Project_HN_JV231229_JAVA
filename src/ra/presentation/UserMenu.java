package ra.presentation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.entity.user.User;
import ra.business.implementation.PurchaseManagement;

public class UserMenu
{
    private static final PurchaseManagement purchaseManagement = new PurchaseManagement();

    public void displayUserMenu(User currentUser)
    {
        while (true)
        {
            String menuText = """
                    ********************CHÀO MỪNG%s********************
                    1. Mua vé
                    2. Tìm kiếm phim
                    3. Hiển thị danh sách phim
                    4. Cài đặt thông tin cá nhân
                    0. Đăng xuất""";
            System.out.println(String.format(menuText, currentUser == null ? "" : " " + currentUser.getFullName().toUpperCase()));
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    purchaseManagement.displayMovieOnShow(currentUser);
                    break;
                case 2:
                    AdminMenu.movieManagement.findItem();
                    break;
                case 3:
                    AdminMenu.movieManagement.displayAllItem(currentUser);
                    break;
                case 4:
                    displayPersonalInfoMenu(currentUser);
                    break;
                case 0:
                    //JAVA chỉ có pass by sharing => set null ở đây sẽ không ảnh hưởng đến user được truyền vào
                    currentUser = null;
                    //Viết lại file để lần sau đọc lại
                    IOFile.writeObject(IOFile.USER_LOGIN, currentUser);
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void displayPersonalInfoMenu(User currentUser)
    {
        while (true)
        {
            System.out.println("""
                    ********************CÀI ĐẶT********************
                    1. Xem thông tin cá nhân
                    2. Cập nhật thông tin cá nhân
                    3. Đổi mật khẩu
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    System.out.println("Thông tin của bạn:");
                    AdminMenu.userManagement.findItem(currentUser).displayBasicData();
                    System.out.println(CONSOLECOLORS.YELLOW + "--------------------------------------------------------------" + CONSOLECOLORS.RESET);
                    break;
                case 2:
                    updateUserInfoMenu(currentUser);
                    break;
                case 3:
                    AdminMenu.userManagement.changePassword(currentUser);
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private void updateUserInfoMenu(User currentUser)
    {
        while (true)
        {
            System.out.println("""
                    ********************CHỈNH SỬA THÔNG TIN********************
                    1. Cập nhật tên của bạn
                    2. Cập nhật email
                    3. Cập nhật số điện thoại
                    0. Quay lại
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    System.out.println("Nhập tên mới mà bạn muốn sử dụng:");
                    AdminMenu.userManagement.findItem(currentUser).setFullName(InputMethods.nextLine());
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
                    break;
                case 2:
                    AdminMenu.userManagement.updateUserEmail(currentUser);
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
                    break;
                case 3:
                    AdminMenu.userManagement.updateUserPhone(currentUser);
                    System.out.println(CONSOLECOLORS.GREEN + "Cập nhật thành công" + CONSOLECOLORS.RESET);
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
