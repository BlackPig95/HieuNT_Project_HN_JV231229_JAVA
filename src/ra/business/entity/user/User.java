package ra.business.entity.user;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.entity.enumclasses.USER_ROLE;

import java.io.InputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class User implements Serializable
{
    private String userId;
    private String email;
    private long phone;
    private String password;
    private String fullName;
    private boolean status = true;
    private USER_ROLE role = USER_ROLE.USER;
    private String avatar = null;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();
    private String historyId = null;

    public User()
    {
    }

    public User(String userId, String email, long phone, String password, String fullName, boolean status, USER_ROLE role, String avatar, LocalDate createdAt, LocalDate updatedAt, String historyId)
    {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.fullName = fullName;
        this.status = status;
        this.role = role;
        this.avatar = avatar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.historyId = historyId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public long getPhone()
    {
        return phone;
    }

    public void setPhone(long phone)
    {
        this.phone = phone;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public USER_ROLE getRole()
    {
        return role;
    }

    public void setRole(USER_ROLE role)
    {
        this.role = role;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public LocalDate getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String getHistoryId()
    {
        return historyId;
    }

    public void setHistoryId(String historyId)
    {
        this.historyId = historyId;
    }

    public void inputData(List<User> userList, boolean isAdding)
    {
        inputUserId(userList);
        inputUserName();
        inputUserEmail(userList, isAdding);
        inputUserPhone(userList, isAdding);
        confirmUserPassword();
        inputUserAvatar();
        //Các trường được set giá trị mặc định:
        //Avatar: Null
        //Status: True
        //Role: USER
        //HistoryID: Null
        //CreatedAt: LocalDate.now();
        //UpdatedAt: LocalDate.now();
    }

    public void displayData()
    {
        System.out.println(CONSOLECOLORS.YELLOW + "==================================================================================" + CONSOLECOLORS.RESET);
        System.out.printf("Mã người dùng: %s | Tên người dùng: %s \n", this.userId, this.fullName);
        System.out.printf("Số điện thoại: %s | Email: %s \n", String.format("0%d", this.phone), this.email);
        System.out.printf("Trạng thái: %s \n", this.status ? "Hoạt động" : "Bị khóa");
        System.out.println(CONSOLECOLORS.YELLOW + "==================================================================================" + CONSOLECOLORS.RESET);
    }

    //Dùng cho giao diện user
    public void displayBasicData()
    {
        System.out.printf("Họ tên: %s \n", this.fullName);
        System.out.printf("Số điện thoại: %s | Email: %s \n", String.format("0%d", this.phone), this.email);
    }

    private void inputUserName()
    {
        System.out.println("Nhập tên của bạn:");
        this.fullName = InputMethods.nextLine();
    }

    public void inputUserEmail(List<User> userList, boolean isAdding)
    {
        //Gồm các ký tự a-z hoặc A-Z hoặc 0-9,độ dài từ 2-64 ký tự, áp dụng chung cho cả phần đứng trước và
        //sau dấu@ cũng như sau dấu chấm. Sau dấu chấm là domain name nên sẽ không thể quá dài
        //=> Giới hạn độ dài từ 2-32 ký tự
        String emailRegex = "^[a-zA-Z0-9&&[\\S]]{2,64}@[a-zA-Z0-9&&[\\S]]{2,32}\\.[a-zA-Z0-9&&[\\S]]{2,32}$";
        while (true)
        {
            System.out.println("Nhập địa chỉ email:");
            String inputEmail = InputMethods.nextLine();
            if (inputEmail.matches(emailRegex))
            {
                if (userList.stream().anyMatch(u -> u.getEmail().equals(inputEmail)))
                {//Nếu là hành động update và email trùng với email cũ => Không cân update, return ngay
                    if (!isAdding && userList.stream().filter(u -> u.getEmail().equals(inputEmail)).findFirst().get() == this)
                    {
                        return;
                    }
                    //Nếu không phải thì là trùng email với user khác => không cho dùng
                    System.out.println(CONSOLECOLORS.RED + "Email này đã được sử dụng. " +
                            "Vui lòng nhập email khác" + CONSOLECOLORS.RESET);
                    continue;
                }
                this.email = inputEmail;
                break;
            } else
            {
                System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập đúng định dạng địa chỉ email" + CONSOLECOLORS.RESET);
            }
        }
    }

    public void inputUserPhone(List<User> userList, boolean isAdding)
    {
        String phoneRegex = "^(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7,8}$";
        while (true)
        {
            System.out.println("Nhập số điện thoại của bạn:");
            String inputPhone = InputMethods.nextLine();
            if (inputPhone.matches(phoneRegex))
            {
                //Lấy ra list các số điện thoại đã lưu
                List<Long> phoneList = userList.stream().map(u -> u.phone).toList();
                boolean phoneExisted = false;
                for (Long phone : phoneList)
                {//Nếu số điện thoại đã được dùng thì bắt nhập lại
                    if (phone == Long.parseLong(inputPhone))
                    {
                        //Nếu là hành động update và sđt trùng với số cũ => Không cần update, return ngay
                        if (!isAdding && phone == this.phone)
                        {
                            return;
                        }
                        System.out.println(CONSOLECOLORS.RED + "Số điện thoại đã được sử dụng. " + CONSTANT.INPUT_AGAIN + CONSOLECOLORS.RESET);
                        phoneExisted = true;
                        break;
                    }
                }
                //Nếu số điện thoại chưa được dùng thì mới cho set phone
                if (!phoneExisted)
                {
                    this.phone = Long.parseLong(inputPhone);
                    break;
                }
            } else
            {
                System.out.println(CONSOLECOLORS.RED + "Số điện thoại không hợp lệ. " + CONSTANT.INPUT_AGAIN + CONSOLECOLORS.RESET);
            }
        }
    }

    public void confirmUserPassword()
    {
        while (true)
        {   //Yêu cầu người dùng nhập mật khẩu trước
            String inputPassword = inputUserPassword();
            //Sau khi nhập thành công mật khẩu thì yêu cầu xác nhận lại
            System.out.println("Xác nhận lại mật khẩu:");
            String inputConfirmPassword = InputMethods.nextLine();
            if (inputConfirmPassword.equals(inputPassword))
            {//Xác nhận thành công thì mới set mật khâu
                this.password = BCrypt.hashpw(inputConfirmPassword, BCrypt.gensalt(5));
                break;
            } else
            {//Nếu xác thực sai, thì phải nhập lại mật khẩu từ đầu
                System.out.println(CONSOLECOLORS.RED + "Mật khẩu không khớp, vui lòng nhập lại mật khẩu từ đầu." + CONSOLECOLORS.RESET);
            }
        }
    }

    private String inputUserPassword()
    {
        String passwordRegex = "^.{6,}$";
        while (true)
        {
            System.out.println("Nhập mật khẩu, độ dài từ 6 ký tự trở lên:");
            String inputPassword = InputMethods.nextLine();
            if (inputPassword.matches(passwordRegex))
            {
                return inputPassword;
            } else
            {
                System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập mật khẩu có độ dài từ 6 ký tự trở lên." + CONSOLECOLORS.RESET);
            }
        }
    }

    private void inputUserAvatar()
    {
        System.out.println("Bạn có muốn đặt hình ảnh đại diện không?");
        System.out.println("1. Có");
        System.out.println("2. Chưa phải lúc này");
        while (true)
        {
            System.out.println("Mời nhập lựa chọn theo các số ở trên");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    System.out.println("Mời nhập đường dẫn cho ảnh đại diện:");
                    this.avatar = InputMethods.nextLine();
                    System.out.println("Đặt ảnh đại diện thành công");
                    return;
                case 2:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private int getIntPartOfUserId(List<User> userList)
    {
        if (userList.isEmpty())//Nếu userList null => Chưa có phần tử => Lấy userId là 0
            return 0;
        //Tách phần chữ số của userId ra, so sánh và tìm ra Id lớn nhất => Lấy ra user này
        User userMaxId = userList.stream().max(Comparator.comparingInt(u ->
                Integer.parseInt(u.userId.substring(1)))).orElse(null);
        //Trả về userId hiện tại + thêm 1
        return Integer.parseInt(userMaxId.userId.substring(1)) + 1;
    }

    private void inputUserId(List<User> userList)
    {//Tạo ra userId bắt đầu bằng chữ U, theo sau là 5 chữ số (đệm số 0 nếu số nhỏ hơn 5 chữ số)
        this.userId = "U" + String.format("%05d", getIntPartOfUserId(userList));
    }
}
