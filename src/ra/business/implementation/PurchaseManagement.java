package ra.business.implementation;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.IOFile;
import ra.business.config.InputMethods;
import ra.business.design.IPurchasable;
import ra.business.entity.enumclasses.SEAT_STATUS;
import ra.business.entity.movie.Movie;
import ra.business.entity.movie.Seat;
import ra.business.entity.movie.ShowTime;
import ra.business.entity.purchase.Snack;
import ra.business.entity.purchase.Ticket;
import ra.business.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static ra.business.implementation.MovieManagement.movieList;
import static ra.business.implementation.ShowTimeManagement.showTimeList;
import static ra.business.implementation.RoomManagement.roomList;
import static ra.business.implementation.SnackManagement.snackList;

public class PurchaseManagement
{
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    List<IPurchasable> customerPurchaseList = new ArrayList<>();

    public void displayMovieOnShow(User currentuser)
    {
        //Chỉ hiện thị ra những phim đã có lịch chiếu
        System.out.println("Danh sách phim hiện có:");
        movieList.stream().filter(m -> !m.getShowTimeId().isEmpty()).forEach(m -> m.displayData(showTimeList));
        Ticket newTicket = purchaseTicket(currentuser);
        //Nếu khách không mua vé thì ticket sẽ không có ID => Quay lại màn hình chính
        if (newTicket.getTicketId().isEmpty())
        {
            System.out.println(CONSOLECOLORS.RED + "Bạn chưa chọn mua vé xem phim nên không thể tiếp tục" + CONSOLECOLORS.RESET);
            return;
        }
        displaySnackChoiceMenu();
    }

    private void displaySnackChoiceMenu()
    {
        while (true)
        {
            System.out.println("Bạn có muốn mua đồ ăn và đồ uống kèm theo không?");
            System.out.println("1. Có");
            System.out.println("2. Không");
            byte choice = InputMethods.nextByte();
            switch (choice)
            {
                case 1:
                    chooseSnack();
                    return;
                case 2:
                    return;
                default:
                    System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                    break;
            }
        }
    }

    private Ticket purchaseTicket(User currentUser)
    {
        Ticket newTicket = new Ticket();
        while (true)
        {
            System.out.println("Nhập chính xác tên của bộ phim mà bạn muốn mua vé. " +
                    "Nhập 'esc' nếu bạn muốn quay lại.");
            String moviePurchaseName = InputMethods.nextLine();
            if (moviePurchaseName.equals("esc"))
            {//Cho phép quay lại màn hình trước đó để xem lại danh sách phim
                return newTicket;
            }
            Movie moviePurchased = getMovieIndexByName(moviePurchaseName);
            if (moviePurchased == null)
            {
                System.out.println(CONSOLECOLORS.RED + "Tên phim không đúng. Vui lòng nhập lại" + CONSOLECOLORS.RESET);
                continue;
            }
            //Hiển thị danh sách lịch chiếu và ghế ngồi
            displayShowTimeList(moviePurchased);
            //Chọn lịch chiếu và ghế ngồi
            while (true)
            {
                //Chọn lịch chiếu, sau đó lấy ra danh sách ghế ngồi của phòng chiếu tương ứng
                ShowTime chosenShowTime = chooseShowTime(moviePurchased);
                //seatList dựa trên chosenShowTime
                List<List<Seat>> seatList = chosenShowTime.getRoom().getSeatList();
                //Dựa trên danh sách ghế ngồi, cho khách chọn các ghế muốn đặt
                //Hiển thị lại danh sách ghế ngồi của phòng chiếu
                displaySeatList(seatList, chosenShowTime);
                Map<String, SEAT_STATUS> seatsChosen = chooseSeats(seatList, chosenShowTime);
                //Nếu không có cái nào là CHOSEN => Khách chưa chọn ghế
                if (!seatsChosen.containsValue(SEAT_STATUS.CHOSEN))
                {
                    System.out.println(CONSOLECOLORS.RED + "Bạn chưa chọn ghế ngồi. Bạn có muốn chọn lại không?" + CONSOLECOLORS.RESET);
                    System.out.println("1. Chọn lại lịch chiếu và ghế");
                    System.out.println("2. Hủy mua vé phim");
                    System.out.println("Nhập lựa chọn của bạn");
                    byte continueChoice = InputMethods.nextByte();
                    switch (continueChoice)
                    {
                        case 1:
                            continue;
                        case 2:
                            return newTicket;
                        default:
                            System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                            System.out.println(CONSOLECOLORS.RED + "Vui lòng thực hiện lại thao tác chọn lịch chiếu" + CONSOLECOLORS.RESET);
                            break;
                    }
                } else
                {
                    //Nếu đã chọn thành công lịch chiếu và ghế ngồi thì dừng vòng lặp để tiếp tục việc mua vé
                    Random random = new Random();
                    newTicket.setMovieId(moviePurchased.getMovieId());
                    newTicket.setShowTimeId(chosenShowTime.getShowTimeId());
                    //Đặt ticketId dựa trên email của user và một số ngẫu nhiên
                    newTicket.setTicketId(currentUser.getEmail() + "_" + String.format("%04d", random.nextInt(1000)));
                    break;
                }
            }
            //Nếu việc đặt vé thành công=> vé sẽ có Id => ngừng vòng lặp
            if (!newTicket.getTicketId().isBlank())
            {   //Vào thời điểm hoàn tất đặt vé
                //Set các ghế đã chọn thành Taken (Các ghế đang ở status CHOSEN trong map)
                List<List<Seat>> seatList = newTicket.getShowTimeFromId(showTimeList).getRoom().getSeatList();
                Map<String, SEAT_STATUS> seatMap = newTicket.getShowTimeFromId(showTimeList).getChosenSeatMap();
                for (List<Seat> seatRow : seatList)
                {
                    for (Seat seat : seatRow)
                    {//So sánh seatName và status để tìm ra ghế nào cần được set thành TAKEN
                        if (seatMap.containsKey(seat.getSeatName()) && seatMap.get(seat.getSeatName()) == SEAT_STATUS.CHOSEN)
                        {
                            seat.setSeatStatus(SEAT_STATUS.TAKEN);
                            seatMap.put(seat.getSeatName(), SEAT_STATUS.TAKEN);
                        }
                    }
                }
//                for (List<Seat> seatRow : seatList)
//                {
//                    seatRow.stream().filter(seat -> seat.getSeatStatus() == SEAT_STATUS.CHOSEN).
//                            forEach(seatChosen -> seatChosen.setSeatStatus(SEAT_STATUS.TAKEN));
//                }
                break;
            }
        }
        customerPurchaseList.add(newTicket);
        System.out.println(CONSOLECOLORS.GREEN + "Đặt vé thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.SHOW_TIME_PATH, showTimeList);//Cập nhật thông tin ghế ngồi của phòng
        return newTicket;
    }

    private Movie getMovieIndexByName(String movieName)
    {
        return movieList.stream().filter(m -> m.getMovieName().equals(movieName)).findFirst().orElse(null);
    }

    private String getShowInfo(int showIndex, Movie moviePurchased)
    {   //Từ phim đã chọn, lấy ra thông tin lịch chiếu tương ứng
        ShowTime currentShow = moviePurchased.getShowTimeFromId(showTimeList, moviePurchased.getShowTimeId().get(showIndex));
        return "Phòng chiếu số: " + currentShow.getRoom().getRoomId() + "\n" +
                "Giờ chiếu: " + currentShow.getOnAirTime().format(dateTimeFormatter);
    }

    private void displayShowTimeList(Movie moviePurchased)
    {
        System.out.printf("Đã chọn phim: %s. Dưới đây là danh sách lịch chiếu\n", moviePurchased.getMovieName());
        System.out.println(CONSOLECOLORS.YELLOW + "-----------------------------------------------------------------------------" + CONSOLECOLORS.RESET);
        for (int i = 1; i <= moviePurchased.getShowTimeId().size(); i++)
        {   //Từ thông tin phim đã chọn, lấy ra danh sách lịch chiếu tương ứng
            //Hiển thị từng lịch chiếu tương ứng-truyền i-1 làm index để truy xuất các showTime trong
            //list showTime của object moviePurchased
            System.out.println("Lịch số: " + i + ". " + getShowInfo(i - 1, moviePurchased));
            System.out.println("Danh sách ghế ngồi: (Màu đỏ là ghế đã có người đặt. " +
                    "Màu tím là ghế bạn đã chọn)");
            //Hiển thị danh sách ghế ngồi tương ứng
            List<List<Seat>> seatList = moviePurchased.
                    getShowTimeFromId(showTimeList, moviePurchased.getShowTimeId().get(i - 1)).
                    getRoom().getSeatList();
            displaySeatList(seatList, moviePurchased.getShowTimeFromId(showTimeList, moviePurchased.getShowTimeId().get(i - 1)));
            System.out.println(CONSOLECOLORS.BLUE_BACKGROUND_BRIGHT + CONSOLECOLORS.BLACK_BOLD + "                        MÀN HÌNH                        " + CONSOLECOLORS.RESET);
        }
        System.out.println(CONSOLECOLORS.YELLOW + "-----------------------------------------------------------------------------" + CONSOLECOLORS.RESET);
    }

    private void displaySeatList(List<List<Seat>> seatList, ShowTime correspondShowTime)
    {
        //Vào thời điểm hiển thị ra danh sách ghế, dựa vào các key trong map để đổi trạng thái của ghế và
        //chọn màu sắc phù hợp
        Map<String, SEAT_STATUS> mapChosenSeat = correspondShowTime.getChosenSeatMap();
        for (int j = 0; j < seatList.size(); j++)
        {
            System.out.print("                      ");
            for (int k = 0; k < seatList.get(j).size(); k++)
            {
                Seat currentSeat = seatList.get(j).get(k);
                if (mapChosenSeat.containsKey(currentSeat.getSeatName()))
                {//Nếu có key = seatName tồn tại => Set Status theo value của key này
                    currentSeat.setSeatStatus(mapChosenSeat.get(currentSeat.getSeatName()));
                } else
                {//Nếu không có key trong map => Seat này AVAI
                    currentSeat.setSeatStatus(SEAT_STATUS.AVAILABLE);
                }
                if (currentSeat.getSeatStatus() == SEAT_STATUS.TAKEN)
                {
                    System.out.print(CONSOLECOLORS.RED_BACKGROUND +
                            CONSOLECOLORS.BLACK_BOLD + currentSeat.getSeatName() + CONSOLECOLORS.RESET);
                    System.out.print(" ");
                } else if (currentSeat.getSeatStatus() == SEAT_STATUS.CHOSEN)
                {
                    System.out.print(CONSOLECOLORS.PURPLE_BACKGROUND_BRIGHT + CONSOLECOLORS.BLACK_BOLD +
                            currentSeat.getSeatName() + CONSOLECOLORS.RESET);
                    System.out.print(" ");
                } else
                {
                    System.out.print(CONSOLECOLORS.GREEN_BACKGROUND + CONSOLECOLORS.BLACK_BOLD +
                            currentSeat.getSeatName() + CONSOLECOLORS.RESET);
                    System.out.print(" ");
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    private ShowTime chooseShowTime(Movie moviePurchased)
    {
        while (true)
        {
            System.out.println("Hãy chọn lịch chiếu bằng mã số tương ứng:");
            byte showTimeChoice = InputMethods.nextByte();
            if (showTimeChoice <= 0 || showTimeChoice > moviePurchased.getShowTimeId().size())
            {
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + ". " + CONSTANT.INPUT_AGAIN + CONSOLECOLORS.RESET);
                continue;
            }
            System.out.println("Bạn đã chọn lịch chiếu số: " + showTimeChoice);
            System.out.println("Danh sách ghế ngồi:");
            //Lấy ra showTime thuộc list showTime của moviePurchased
            return moviePurchased.getShowTimeFromId
                    (showTimeList, moviePurchased.getShowTimeId().get(showTimeChoice - 1));
        }
    }

    private Map<String, SEAT_STATUS> chooseSeats(List<List<Seat>> seatList, ShowTime chosenShowTime)
    {
//        List<Seat> seatsChosen = new ArrayList<>();
//        List<String> seatsChosen = new ArrayList<>();
        while (true)
        {
            System.out.println("Hãy chọn ghế ngồi bằng cách nhập chính xác tên ghế. " +
                    "Nhập lại tên ghế lần nữa nếu muốn bỏ chọn. " +
                    "Nhập chính xác 'esc' để tiếp tục sau khi đã chọn xong");
            String seatChoice = InputMethods.nextLine();
            if (seatChoice.equals("esc"))
            {
                break;
            }
            boolean seatExist = false;
            //Duyệt qua danh sách ghế ngồi
            for (List<Seat> subList : seatList)
            {//Mỗi phần tử là một list 1 chiều => Tìm ra seat có tên khớp với tên đã nhận
                Seat newSeatChosen = subList.stream().filter(seat -> seat.getSeatName().equals(seatChoice)).findFirst().orElse(null);
                if (newSeatChosen != null)
                {
                    seatExist = true;
                    //Nếu ghế đã được chọn từ trước thì không cho tác động
                    if (newSeatChosen.getSeatStatus() == SEAT_STATUS.TAKEN)
                    {
                        System.out.println(CONSOLECOLORS.RED + "Ghế này đã có người chọn" + CONSOLECOLORS.RESET);
                        continue;
                    }
                    //Đánh dấu là ghế đã được chọn hoặc bỏ chọn
                    if (newSeatChosen.getSeatStatus() == SEAT_STATUS.AVAILABLE)
                    {
                        //key-value pair của map: seatName là key, Status là value
                        //=>Những ghế nào không có trong map thì là AVAI, ghế nào được đưa vào map thì
                        //là CHOSEN
                        chosenShowTime.getChosenSeatMap().put(newSeatChosen.getSeatName(), SEAT_STATUS.CHOSEN);
                        newSeatChosen.setSeatStatus(SEAT_STATUS.CHOSEN);
                        //Cập nhật danh sách ghế ngồi
                        displaySeatList(seatList, chosenShowTime);
//                        seatsChosen.add(newSeatChosen);
                        System.out.println(CONSOLECOLORS.GREEN + "Đã thêm ghế thành công" + CONSOLECOLORS.RESET);
                    } else if (newSeatChosen.getSeatStatus() == SEAT_STATUS.CHOSEN)
                    {
                        //Nếu ghế được chọn lại thì xóa key ra khỏi map => Không có trong map thì là AVAI
                        chosenShowTime.getChosenSeatMap().remove(newSeatChosen.getSeatName());
//                        chosenShowTime.getChosenSeatMap().put(newSeatChosen.getSeatName(), SEAT_STATUS.AVAILABLE);
                        newSeatChosen.setSeatStatus(SEAT_STATUS.AVAILABLE);
//                        seatsChosen.remove(newSeatChosen);
                        //Cập nhật danh sách ghế ngồi
                        displaySeatList(seatList, chosenShowTime);
                        System.out.println(CONSOLECOLORS.GREEN_UNDERLINED + "Đã bỏ chọn ghế" + CONSOLECOLORS.RESET);
                    }
                    break;
                }
            }
//            for (List<Seat> subList : seatList)
//            {//Mỗi phần tử là một list 1 chiều => Tìm ra seat có tên khớp với tên đã nhận
//                Seat newSeatChosen = subList.stream().filter(seat -> seat.getSeatName().equals(seatChoice)).findFirst().orElse(null);
//                if (newSeatChosen != null)
//                {
//                    seatExist = true;
//                    //Nếu ghế đã được chọn từ trước thì không cho tác động
//                    if (newSeatChosen.getSeatStatus() == SEAT_STATUS.TAKEN)
//                    {
//                        System.out.println(CONSOLECOLORS.RED + "Ghế này đã có người chọn" + CONSOLECOLORS.RESET);
//                        continue;
//                    }
//                    //Đánh dấu là ghế đã được chọn hoặc bỏ chọn
//                    if (newSeatChosen.getSeatStatus() == SEAT_STATUS.AVAILABLE)
//                    {
//                        newSeatChosen.setSeatStatus(SEAT_STATUS.CHOSEN);
//                        seatsChosen.add(newSeatChosen);
//                        System.out.println(CONSOLECOLORS.GREEN + "Đã thêm ghế thành công" + CONSOLECOLORS.RESET);
//                    } else if (newSeatChosen.getSeatStatus() == SEAT_STATUS.CHOSEN)
//                    {
//                        newSeatChosen.setSeatStatus(SEAT_STATUS.AVAILABLE);
//                        seatsChosen.remove(newSeatChosen);
//                        System.out.println(CONSOLECOLORS.GREEN_UNDERLINED + "Đã bỏ chọn ghế" + CONSOLECOLORS.RESET);
//                    }
//                    break;
//                }
//            }
            if (!seatExist)
            {
                System.out.println(CONSOLECOLORS.RED + "Mã ghế không đúng" + CONSOLECOLORS.RESET);
            }
        }
        return chosenShowTime.getChosenSeatMap();
    }

    private void chooseSnack()
    {
        while (true)
        {
            System.out.println("Danh sách các đồ ăn/ đồ uống tại rạp:");
            System.out.println(CONSOLECOLORS.YELLOW + "-------------------------------------------------------" + CONSOLECOLORS.RESET);
            for (int i = 0; i < snackList.size(); i++)
            {
                System.out.println((i + 1) + ". " + snackList.get(i).showBasicData());
            }
            System.out.println(CONSOLECOLORS.YELLOW + "-------------------------------------------------------" + CONSOLECOLORS.RESET);
            System.out.println("Mời nhập lựa chọn của bạn bằng chỉ số của mỗi loại đồ ăn/ đồ uống");
            System.out.println(CONSOLECOLORS.BLUE + "Nhập '0' khi bạn muốn ngừng mua" + CONSOLECOLORS.RESET);
            byte choice = InputMethods.nextByte();
            if (choice == 0)
            {
                return;
            }
            if (choice < 0 || choice > snackList.size())
            {
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
            } else
            {
                Snack snackChosen = snackList.get(choice - 1);
                System.out.println("Bạn đã chọn " + snackChosen.getSnackName() + ". Mời nhập số lượng muốn mua");
                System.out.println(CONSOLECOLORS.BLUE + "Bạn có thể nhập số âm để giảm bớt số lượng muốn mua" + CONSOLECOLORS.RESET);
                byte amount = InputMethods.nextByte();
                byte totalAmount = (byte) (snackChosen.getAmountPurchased() + amount);
                //byte max = 128 => 50 là con số an toàn để không bị overflow
                if (totalAmount < 0 || totalAmount > 50)
                {
                    System.out.println(CONSOLECOLORS.RED + "Số lượng không được nhỏ hơn 0 hay lớn hơn 50" + CONSOLECOLORS.RESET);
                    continue;
                }
                snackChosen.setAmountPurchased(totalAmount);
                System.out.println("Hiện tại bạn đã mua tổng cộng " + totalAmount + " " + snackChosen.getSnackName());
            }
        }
    }
}
