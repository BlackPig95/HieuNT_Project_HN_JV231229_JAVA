package ra.business.config;

import java.text.NumberFormat;
import java.util.Locale;

public class CONSTANT
{
    public static final Locale VN_LOCALE = new Locale("vi", "VN");
    public static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(VN_LOCALE);
    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final String ID_NOT_EXIST = "Id không tồn tại";
    public static final String CHOICE_NOT_AVAI = "Lựa chọn không khả dụng";
    public static final String INPUT_AGAIN = "Vui lòng nhập lại";
    public static final String NO_ELEMENT_IN_LIST = " hiện không có phần tử nào";
    public static final String LIST_STH = "Danh sách ";
    public static final String ELEMENT_NOT_FOUND = "Không tìm thấy kết quả nào";
    public static final String LIST_SEARCH_RESULT = "Danh sách kết quả tìm kiếm:";
    public static final String INPUT_YOUR_CHOICE = "Nhập thao tác muốn thực hiện theo các lựa chọn ở trên";
    public static final String DELETED_SUCCESS = "Đã xóa thành công";
    public static final String ADDED_SUCCESS = "Đã thêm thành công";
    public static final String UPDATE_SUCCESS = "Cập nhật thành công";
}
