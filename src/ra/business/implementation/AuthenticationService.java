package ra.business.implementation;

import org.mindrot.jbcrypt.BCrypt;
import ra.business.config.IOFile;
import ra.business.design.IAuthentication;
import ra.business.entity.user.User;

import static ra.business.implementation.UserManagement.userList;

public class AuthenticationService implements IAuthentication
{
    @Override
    public User login(String userEmail, String password)
    {
        User userLogin = getUserFromEmail(userEmail);
        if (userLogin == null)
        {
            return null;
        }
        boolean checkLogin = BCrypt.checkpw(password, userLogin.getPassword());
        if (checkLogin)
        {
            return userLogin;
        }
        return null;
    }

    @Override
    public void register()
    {
        User user = new User();
        user.inputData(userList, true);
        userList.add(user);
        IOFile.writeToFile(IOFile.USER_PATH, userList);
    }

    private User getUserFromEmail(String userEmail)
    {
        return userList.stream().filter(u -> u.getEmail().equals(userEmail)).findFirst().orElse(null);
    }
}
