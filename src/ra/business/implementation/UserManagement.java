package ra.business.implementation;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.config.CONSOLECOLORS;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.design.IUserDesign;
import ra.business.entity.enumclasses.USER_ROLE;
import ra.business.entity.user.User;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserManagement implements IUserDesign
{
    static List<User> userList;

    public UserManagement()
    {//Tránh trường hợp đọc file rỗng
        File file = new File(IOFile.USER_PATH);
        if (file.length() == 0)
        {
            userList = new ArrayList<>();
            //Nếu file rỗng thì tạo ra một list mới rồi ghi admin vào
            User admin = new User("U00000", "admin", 123456789,
                    BCrypt.hashpw("admin", BCrypt.gensalt(5)), "admin", true, USER_ROLE.ADMIN,
                    "avatar", LocalDate.now(), LocalDate.now(), "history");
            userList.add(admin);
            IOFile.writeToFile(file, userList);
        } else
        {
            userList = IOFile.readFromFile(IOFile.USER_PATH);
            //Nếu đã có file, kiểm tra xem có tồn tại admin chưa, nếu chưa thì add admin vào
            if (userList.stream().noneMatch(u -> u.getEmail().equals("admin")))
            {
                User admin = new User("U00000", "admin", 123456789,
                        BCrypt.hashpw("admin", BCrypt.gensalt(5)), "admin", true, USER_ROLE.ADMIN,
                        "avatar", LocalDate.now(), LocalDate.now(), "history");
                userList.add(admin);
                IOFile.writeToFile(file, userList);
            }
        }
    }

    @Override
    public void addItem()
    {
        User user = new User();
        user.inputData(userList, true);
        userList.add(user);
        IOFile.writeToFile(IOFile.USER_PATH, userList);
    }

    @Override
    public void updateItem()
    {
        int indexUpdate = getIndexById();
        if (indexUpdate == -1)
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy người dùng cần cập nhật" + CONSOLECOLORS.RESET);
            return;
        }
        //Khóa hoặc mở khóa người dùng
        User updateUser = userList.get(indexUpdate);
        if (updateUser.getRole() == USER_ROLE.ADMIN)
        {
            System.out.println(CONSOLECOLORS.RED + "Không thể thực hiện thay đổi với quản trị viên" + CONSOLECOLORS.RESET);
            return;
        }
        updateUser.setStatus(!updateUser.isStatus());
        System.out.printf("Trạng thái mới: %s \n", updateUser.isStatus() ? "Hoạt động" : "Bị khóa");
        IOFile.writeToFile(IOFile.USER_PATH, userList);
    }

    @Override
    public void findItem()
    {
        System.out.println("Nhập thông tin người dùng cần tìm (email hoặc tên người dùng)");
        String searchInput = InputMethods.nextLine();
        List<User> userFoundList = userList.stream().filter(u -> u.getFullName().
                contains(searchInput) || u.getEmail().contains(searchInput)).toList();
        if (userFoundList.isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Không tìm thấy người dùng" + CONSOLECOLORS.RESET);
            return;
        }
        System.out.println("Danh sách người dùng có thông tin giống với mô tả:");
        userFoundList.forEach(User::displayData);
    }

    //Overload method dùng cho giao diện user
    @Override
    public User findItem(User userToSearch)
    {
        return userList.stream().filter(u -> u == userToSearch).findFirst().orElse(null);
    }

    @Override
    public void displayAllItem()
    {
        System.out.println("Danh sách người dùng hiện tại");
        userList.forEach(u -> u.displayData());
    }

    @Override
    public int getIndexById()
    {
        if (userList.isEmpty())
            return -1;
        System.out.println("Nhập mã người dùng cần tìm:");
        String searchId = InputMethods.nextLine();
        for (int i = 0; i < userList.size(); i++)
        {
            if (searchId.equals(userList.get(i).getUserId()))
                return i;
        }
        return -1;
    }

    @Override
    public void changePassword(User currentUser)
    {
        while (true)
        {
            System.out.println("Nhập mật khẩu cũ");
            String oldPassword = InputMethods.nextLine();
            if (BCrypt.checkpw(oldPassword, currentUser.getPassword()))
            {
                System.out.println(CONSOLECOLORS.GREEN + "Xác nhận mật khẩu thành công. Hãy nhập mật khẩu mới" + CONSOLECOLORS.RESET);
                currentUser.confirmUserPassword();
                break;
            } else
            {
                System.out.println(CONSOLECOLORS.RED + "Mật khẩu cũ không đúng. Hãy kiểm tra lại" + CONSOLECOLORS.RESET);
            }
        }
        System.out.println(CONSOLECOLORS.GREEN + "Cập nhật mật khẩu thành công" + CONSOLECOLORS.RESET);
    }

    @Override
    public void updateUserEmail(User currentUser)
    {
        currentUser.inputUserEmail(userList, false);
    }

    @Override
    public void updateUserPhone(User currenUser)
    {
        currenUser.inputUserPhone(userList, false);
    }
}
