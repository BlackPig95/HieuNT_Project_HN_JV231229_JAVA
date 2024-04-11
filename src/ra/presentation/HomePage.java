package ra.presentation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.entity.enumclasses.USER_ROLE;
import ra.business.entity.user.User;
import ra.business.implementation.AuthenticationService;
import ra.business.implementation.UserManagement;

public class HomePage
{
    private static final AuthenticationService authentication = new AuthenticationService();
    private static final UserMenu userMenu = new UserMenu();
    private static final AdminMenu adminMenu = new AdminMenu();
    public static User user = null;

    public static void main(String[] args)
    {
        while (true)
        {
            System.out.println("""
                    ********************CHÀO MỪNG********************
                    1. Xem danh sách phim hiện có (Đăng nhập để mua vé)
                    2. Đăng ký
                    3. Đăng nhập
                    0. Thoát khỏi trang
                    """);
            System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    AdminMenu.movieManagement.displayAllItem();
                    break;
                case 2:
                    authentication.register();
                    System.out.println(CONSOLECOLORS.GREEN + "Đăng ký thành công" + CONSOLECOLORS.RESET);
                    break;
                case 3:
                    login();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private static void login()
    {
        System.out.println("Nhập email của bạn");
        String emailLogin = InputMethods.nextLine();
        System.out.println("Nhập mật khẩu của bạn");
        String passwordLogin = InputMethods.nextLine();
        User userLogin = authentication.login(emailLogin, passwordLogin);
        if (userLogin == null)
        {
            System.out.println(CONSOLECOLORS.RED + "Tên tài khoản hoặc mật khẩu không chínhh xác" + CONSOLECOLORS.RESET);
            System.out.println("1. Tiếp tục đăng nhập");
            System.out.println("2. Bạn chưa có tài khoản? Đăng ký ngay");
            System.out.println("3. Thoát");
            System.out.println(CONSTANT.INPUT_YOUR_CHOICE);
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    login();
                    break;
                case 2:
                    authentication.register();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        } else
        {
            if (userLogin.getRole() == USER_ROLE.USER)
            {
                if (userLogin.isStatus())
                {
                    user = userLogin;
                    userMenu.displayUserMenu(user);
                } else
                {
                    System.out.println(CONSOLECOLORS.RED + "Tài khoản đã bị khóa, vui lòng liên hệ bộ phận chăm sóc khách hàng" + CONSOLECOLORS.RESET);
                }
            } else if (userLogin.getRole() == USER_ROLE.ADMIN)
            {
                user = userLogin;
                adminMenu.displayAdminMenu();
            }
        }
    }
}
