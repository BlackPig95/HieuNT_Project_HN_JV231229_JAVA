package ra.business.design;

import ra.business.entity.user.User;

public interface IUserDesign extends CRUF
{
    void changePassword(User currentUser);

    void updateUserEmail(User currentUser);

    void updateUserPhone(User currenUser);

    User findItem(User userToSearch);
}
