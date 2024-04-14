package ra.business.config;

import ra.business.design.IAdminPaginable;

import java.util.List;

public class AdminPagination
{
    public static <T extends IAdminPaginable> void pagination(List<T> list)
    {
        int itemsPerPage = 3;
        int numberOfPages = (int) Math.ceil((double) list.size() / itemsPerPage);
        displayPagination(0, numberOfPages, itemsPerPage, list);
        while (true)
        {
            System.out.println(CONSOLECOLORS.BLUE + "Nhập số trang bạn muốn xem. Nhập 0 để thoát" + CONSOLECOLORS.RESET);
            int choice = InputMethods.nextInt();
            if (choice == 0)
            {
                return;
            }
            if (choice >= 1 && choice <= numberOfPages)
            {
                displayPagination(choice - 1, numberOfPages, itemsPerPage, list);
            } else
            {
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
            }
        }
    }

    private static <T extends IAdminPaginable> void displayPagination(int currentPage, int numberOfPages, int itemsPerPage, List<T> list)
    {
        System.out.println(CONSOLECOLORS.YELLOW + "==================================================================================" + CONSOLECOLORS.RESET);
        for (int i = currentPage * itemsPerPage; i < currentPage * itemsPerPage + itemsPerPage; i++)
        {
            if (i == list.size())
            {
                break;
            }
            list.get(i).displayData();
        }
        System.out.println(CONSOLECOLORS.YELLOW + "==================================================================================" + CONSOLECOLORS.RESET);
        System.out.print(CONSOLECOLORS.BLUE);
        System.out.printf("Trang hiện tại: %d/%d", currentPage + 1, numberOfPages);
        System.out.println(CONSOLECOLORS.RESET);
    }
}
