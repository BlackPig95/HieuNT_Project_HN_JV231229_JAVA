package ra.business.entity.purchase;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;
import ra.business.design.IPurchasable;
import ra.business.entity.enumclasses.SNACK_TYPE;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;


public class Snack implements IPurchasable
{
    private String snackId;
    private int price;
    private SNACK_TYPE snackType;
    private String snackName;
    private byte amountPurchased;

    public void inputData(List<Snack> snackList, boolean isAdding)
    {
        inputSnackType();
        inputSnackName(snackList, isAdding);
        inputSnackPrice();
        if (isAdding)
        {
            inputSnackId(snackList);
        }
    }

    public void displayData()
    {
        System.out.printf("Mã đồ ăn/ đồ uống: %s | Tên: %s | Giá bán: %s | Phân loại: %s\n",
                this.snackId, this.snackName, CONSTANT.currencyFormat.format(this.price), this.snackType.getName());
    }

    //UI
    @Override
    public String showBasicData()
    {
        return "Tên đồ ăn/ đồ uống: " + this.snackName + " | Giá bán: " + CONSTANT.currencyFormat.format(this.price)
                + " | Số lượng: " + this.amountPurchased;
    }

    private void inputSnackId(List<Snack> snackList)
    {
        //Tự tăng dựa theo snack type
        List<Snack> sameTypeList = snackList.stream().filter(s -> s.snackType == this.snackType).toList();
        int maxId = getMaxId(sameTypeList);
        switch (this.snackType)
        {
            case POPCORN:
                this.snackId = "P" + String.format("%02d", maxId);
                break;
            case SOFT_DRINK:
                this.snackId = "S" + String.format("%02d", maxId);
                break;
            case OTHER_FOOD:
                this.snackId = "F" + String.format("%02d", maxId);
                break;
            case OTHER_DRINK:
                this.snackId = "W" + String.format("%02d", maxId);
                break;
            default:
                break;
        }
    }

    private void inputSnackName(List<Snack> snackList, boolean isAdding)
    {
        while (true)
        {
            System.out.println("Nhập tên của đồ ăn/ đồ uống");
            String inputName = InputMethods.nextLine();
            if (snackList.isEmpty())//List rỗng thì không cần check trùng lặp
            {
                this.snackName = inputName;
                return;
            }
            if (snackList.stream().anyMatch(snack -> snack.snackName.equals(inputName)))
            {   //Nếu là hành động update và trùng tên cũ => Không cần set mới, return ngay
                if (!isAdding && this.snackName.equals(inputName))
                {
                    return;
                }
                System.out.println(CONSOLECOLORS.RED + "Tên này đã được sử dụng" + CONSOLECOLORS.RESET);
            } else
            {
                this.snackName = inputName;
                break;
            }
        }
    }

    private void inputSnackType()
    {
        while (true)
        {
            System.out.println("Các phân loại đồ ăn/ đồ uống hiện có");
            for (SNACK_TYPE type : SNACK_TYPE.values())
            {//In ra tên của các phân loại snack
                System.out.println(type.getName());
            }
            System.out.println("Chọn phân loại đồ ăn/ đồ uống bằng cách nhập chữ số tương ứng:");
            byte choice = InputMethods.nextByte();
            //Kiểm tra xem số nhập vào có ngoài vùng cho phép không
            if (choice <= 0 || choice > SNACK_TYPE.values().length)
            {
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSTANT.INPUT_AGAIN + CONSOLECOLORS.RESET);
                continue;
            }
            //Dựa trên số nhập vào, lọc ra loại snack tương ứng và set type
            //SNACK_TYPE là enum nên có thể kiểm soát các chữ số đứng trước sao cho không bị trùng nhau
            this.snackType = Stream.of(SNACK_TYPE.values()).
                    filter(type -> type.getName().contains(String.valueOf(choice))).findFirst().orElse(null);
            break;
        }
    }

    private void inputSnackPrice()
    {
        while (true)
        {
            System.out.println("Nhập giá bán cho sản phẩm này:");
            int newPrice = InputMethods.nextInt();
            if (newPrice <= 0)
            {
                System.out.println(CONSOLECOLORS.RED + "Giá sản phẩm phải lớn hơn 0" + CONSOLECOLORS.RESET);
            } else
            {
                this.price = newPrice;
                break;
            }
        }
    }

    private int getMaxId(List<Snack> sameTypeList)
    {
        if (sameTypeList.isEmpty())
        {
            return 0;
        }
        Snack maxIdSnack = sameTypeList.stream().max(Comparator.comparing(s -> s.snackId)).orElse(null);
        return Integer.parseInt(maxIdSnack.snackId.substring(1)) + 1;
    }

    public String getSnackId()
    {
        return snackId;
    }

    public void setSnackId(String snackId)
    {
        this.snackId = snackId;
    }

    public String getSnackName()
    {
        return snackName;
    }

    public void setSnackName(String snackName)
    {
        this.snackName = snackName;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public SNACK_TYPE getSnackType()
    {
        return snackType;
    }

    public void setSnackType(SNACK_TYPE snackType)
    {
        this.snackType = snackType;
    }

    public byte getAmountPurchased()
    {
        return amountPurchased;
    }

    public void setAmountPurchased(byte amountPurchased)
    {
        this.amountPurchased = amountPurchased;
    }
}
