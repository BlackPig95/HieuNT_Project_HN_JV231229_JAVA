package ra.business.implementation;

import ra.business.config.IOFile;
import ra.business.design.IDeletable;
import ra.business.entity.purchase.Snack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SnackManagement implements IDeletable
{
    static List<Snack> snackList;

    public SnackManagement()
    {
        File file = new File(IOFile.SNACK_PATH);
        if (file.length() == 0)
        {
            snackList = new ArrayList<>();
            IOFile.writeToFile(file, snackList);
        } else
        {
            snackList = IOFile.readFromFile(IOFile.SNACK_PATH);
        }
    }

    @Override
    public void addItem()
    {

    }

    @Override
    public void updateItem()
    {

    }

    @Override
    public void findItem()
    {

    }

    @Override
    public void displayAllItem()
    {

    }

    @Override
    public int getIndexById()
    {
        return 0;
    }

    @Override
    public void deleteItem()
    {

    }
}
