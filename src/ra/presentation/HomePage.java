package ra.presentation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.entity.enumclasses.USER_ROLE;
import ra.business.entity.user.User;
import ra.business.implementation.AuthenticationService;

public class HomePage
{
    private static final AuthenticationService authentication = new AuthenticationService();
    private static final UserMenu userMenu = new UserMenu();
    private static final AdminMenu adminMenu = new AdminMenu();
    public static User user;

    public static void main(String[] args)
    {
        while (true)
        {
            //JAVA không tồn tại pass by reference mà chỉ có pass by sharing
            //=> Khi user logout thì phải tự set lại user hiện thành null
            //Việc set param của hàm displayUserMenu thành null sẽ không ảnh hưởng
            //tới user hiện tại => Phải đọc lại file mỗi lần quay lại menu để đảm bảo
            //user được cập nhật theo file mới nhất
            try
            {
                user = IOFile.readObject(IOFile.USER_LOGIN);
            } catch (Exception e)
            {
                user = null;
            }
            if (user == null)
            {
                System.out.println(CONSOLECOLORS.YELLOW_BOLD_BRIGHT);
                System.out.print("""
                        ┏────────────────────────────────────────────────────────────┓
                        ┃  =====================  CHÀO MỪNG  ======================  ┃
                        ┃  1. Xem danh sách phim hiện có (Đăng nhập để mua vé)       ┃
                        ┃  2. Đăng ký                                                ┃
                        ┃  3. Đăng nhập                                              ┃
                        ┃  0. Thoát khỏi trang                                       ┃
                        ┗────────────────────────────────────────────────────────────┛ 	               
                          """);
                System.out.print(CONSOLECOLORS.RESET);
                System.out.println("Hãy nhập lựa chọn theo danh sách ở trên");
                byte choice = InputMethods.nextByte();
                switch (choice)
                {
                    case 1:
                        AdminMenu.movieManagement.displayAllItem(user);
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
            } else
            {
                login();
            }
        }
    }

    private static void login()
    {
        if (user != null)
        {   //Không cần check case status = false, vì nếu bị khóa thì ngay từ đầu user
            //đã không thể đăng nhập
            if (user.getRole() == USER_ROLE.USER && user.isStatus())
            {
                userMenu.displayUserMenu(user);
            } else
            {
                adminMenu.displayAdminMenu(user);
            }
            //JAVA không tồn tại pass by reference mà chỉ có pass by sharing
            //=> Khi user logout thì phải tự set lại user hiện thành null
            //Việc set param của hàm displayUserMenu thành null sẽ không ảnh hưởng
            //tới user hiện tại
            return;
        }
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
                    //Viết vào file trước khi gọi displayMenu để khi hàm displayMenu trả về sẽ không bị ghi đè
                    //file 1 lần nữa gây lỗi vì cơ chế pass by sharing
                    IOFile.writeObject(IOFile.USER_LOGIN, user);
                    userMenu.displayUserMenu(user);
                } else
                {
                    System.out.println(CONSOLECOLORS.RED + "Tài khoản đã bị khóa, vui lòng liên hệ bộ phận chăm sóc khách hàng" + CONSOLECOLORS.RESET);
                }
            } else if (userLogin.getRole() == USER_ROLE.ADMIN)
            {
                user = userLogin;
                //Viết vào file trước khi gọi displayMenu để khi hàm displayMenu trả về sẽ không bị ghi đè
                //file 1 lần nữa gây lỗi vì cơ chế pass by sharing
                IOFile.writeObject(IOFile.USER_LOGIN, user);
                adminMenu.displayAdminMenu(user);
            }
        }
    }
}
