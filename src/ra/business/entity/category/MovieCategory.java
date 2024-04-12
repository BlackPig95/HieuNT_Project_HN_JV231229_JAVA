package ra.business.entity.category;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.design.IAdminPaginable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class MovieCategory implements Serializable, IAdminPaginable
{
    private String categoryId;
    private String categoryName;
    private String description;

    public MovieCategory()
    {
    }

    public MovieCategory(String categoryId, String categoryName, String description)
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
    }

    public void inputData(List<MovieCategory> movieCategoryList, boolean isAdding)
    {
        if (isAdding)
        {
            inputCategoryId(movieCategoryList);
        }
        inputCategoryName(movieCategoryList, isAdding);
        inputCategoryDescription();
    }

    @Override
    public void displayData()
    {
        System.out.printf("Mã số: %s | Tên thể loại: %s | Mô tả: %s\n",
                this.categoryId, this.categoryName, this.description);
    }

    private void inputCategoryName(List<MovieCategory> movieCategoryList, boolean isAdding)
    {
        List<String> categoryName = movieCategoryList.stream().map(c -> c.categoryName).toList();
        while (true)
        {
            System.out.println("Nhập tên thể loại");
            String newGenre = InputMethods.nextLine();
            if (movieCategoryList.isEmpty())
            {   //Nếu list trống thì không cần kiểm tra trùng lặp
                this.categoryName = newGenre;
                return;
            }
            boolean genreExisted = false;
            for (String genre : categoryName)
            {
                if (genre.equals(newGenre))
                {   //Nếu là hành động thêm mới và bị trùng tên với chính bản thân => Không cần update, return ngay
                    if (!isAdding && this.categoryName.equals(genre))
                    {
                        return;
                    }
                    System.out.println(CONSOLECOLORS.RED + "Thể loại đã có sẵn. " + CONSTANT.INPUT_AGAIN + CONSOLECOLORS.RESET);
                    genreExisted = true;
                    break;
                }
            }
            if (!genreExisted)
            {
                this.categoryName = newGenre;
                return;
            }
        }
    }

    private void inputCategoryDescription()
    {
        System.out.println("Nhập mô tả về thể loại này");
        this.description = InputMethods.nextLine();
    }

    private void inputCategoryId(List<MovieCategory> movieCategoryList)
    {//Gán category bắt đầu bằng chữ C theo sau là 5 chữ số được lót đệm bằng số 0
        this.categoryId = "C" + String.format("%05d", getIntPartOfCategoryId(movieCategoryList));
    }

    private int getIntPartOfCategoryId(List<MovieCategory> movieCategoryList)
    {
        if (movieCategoryList.isEmpty())//Nếu movieCategoryList null => Chưa có phần tử => Lấy categoryId là 0
            return 0;
        //Tách phần chữ số của userId ra, so sánh và tìm ra Id lớn nhất => Lấy ra category này
        MovieCategory movieCategoryMaxId = movieCategoryList.stream().max(Comparator.comparingInt(c ->
                Integer.parseInt(c.categoryId.substring(1)))).orElse(null);
        //Trả về categoryId hiện tại + thêm 1
        return Integer.parseInt(movieCategoryMaxId.categoryId.substring(1)) + 1;
    }

    //Getter Setter
    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
