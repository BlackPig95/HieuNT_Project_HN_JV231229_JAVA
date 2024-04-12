package ra.business.design;

import ra.business.entity.user.User;

public interface CRUF
{
    void addItem();

    void updateItem();

    void findItem();

    void displayAllItem(User currentUser);

    int getIndexById();
}
