package ra.business.design;

import ra.business.entity.user.User;

public interface IAuthentication
{
    User login(String userEmail, String password);

    void register();
}
