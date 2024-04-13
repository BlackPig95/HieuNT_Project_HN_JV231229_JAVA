package ra.business.design;

import java.io.Serializable;
import java.util.List;

public interface IPurchasable extends Serializable
{
    String showBasicData();//Ép các lớp con phải triển khai phương thức để hiển thị thông tin mặt hàng

    int getTotalPrice();//Tính tổng giá tiền mỗi mặt hàng đã mua
}
