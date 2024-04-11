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
import ra.business.entity.purchase.Ticket;
import ra.business.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ra.business.implementation.MovieManagement.movieList;
import static ra.business.implementation.ShowTimeManagement.showTimeList;
import static ra.business.implementation.RoomManagement.roomList;

public class PurchaseManagement
{
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    List<IPurchasable> customerPurchaseList = new ArrayList<>();

    public void displayMovieOnShow(User currentuser)
    {
        //Chỉ hiện thị ra những phim đã có lịch chiếu
        System.out.println("Danh sách phim hiện có:");
        movieList.stream().filter(m -> !m.getShowTimeId().isEmpty()).forEach(m -> m.displayData(showTimeList));
        purchaseTicket(currentuser);
        System.out.println("Bạn có muốn mua đồ ăn và đồ uống kèm theo không?");
        System.out.println("1. Có");
        System.out.println("2. Không");
        byte choice = InputMethods.nextByte();
        switch (choice)
        {
            case 1:
                break;
            case 2:
                break;
            default:
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
                break;
        }
    }

    private void purchaseTicket(User currentUser)
    {
        Ticket newTicket = new Ticket();
        while (true)
        {
            System.out.println("Nhập chính xác tên của bộ phim mà bạn muốn mua vé. " +
                    "Nhập 'esc' nếu bạn muốn quay lại.");
            String moviePurchaseName = InputMethods.nextLine();
            if (moviePurchaseName.equals("esc"))
            {//Cho phép quay lại màn hình trước đó để xem lại danh sách phim
                return;
            }
            Movie moviePurchased = getMovieIndexByName(moviePurchaseName);
            if (moviePurchased == null)
            {
                System.out.println(CONSOLECOLORS.RED + "Tên phim không đúng. Vui lòng nhập lại" + CONSOLECOLORS.RESET);
                continue;
            }
            //Hiển thị danh sách lịch chiếu
            displayShowTimeList(moviePurchased);
            //Chọn lịch chiếu và ghế ngồi
            seatloop:
            while (true)
            {
                //Chọn lịch chiếu, sau đó lấy ra danh sách ghế ngồi của phòng chiếu tương ứng
                ShowTime chosenShowTime = chooseShowTime(moviePurchased);
                List<List<Seat>> seatList = chosenShowTime.getRoomFromId(roomList).getSeatList();
                //Dựa trên danh sách ghế ngồi, cho khách chọn các ghế muốn đặt
                List<Seat> seatsChosen = chooseSeats(seatList);
                if (seatsChosen.isEmpty())
                {
                    System.out.println(CONSOLECOLORS.RED + "Bạn chưa chọn ghế ngồi. Bạn có muốn chọn lại không?" + CONSOLECOLORS.RESET);
                    System.out.println("1. Chọn lại lịch chiếu và ghế");
                    System.out.println("2. Hủy mua vé phim này");
                    System.out.println("Nhập lựa chọn của bạn");
                    byte continueChoice = InputMethods.nextByte();
                    switch (continueChoice)
                    {
                        case 1:
                            continue;
                        case 2:
                            break seatloop;
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
            {
                //Set các ghế đã chọn thành Taken
                List<List<Seat>> seatList = newTicket.getShowTimeFromId(showTimeList).getRoomFromId(roomList).getSeatList();
                for (List<Seat> seatRow : seatList)
                {
                    seatRow.stream().filter(seat -> seat.getSeatStatus() == SEAT_STATUS.CHOSEN).
                            forEach(seatChosen -> seatChosen.setSeatStatus(SEAT_STATUS.TAKEN));
                }
                break;
            }
        }
        customerPurchaseList.add(newTicket);
        System.out.println(CONSOLECOLORS.GREEN + "Đặt vé thành công" + CONSOLECOLORS.RESET);
        IOFile.writeToFile(IOFile.ROOM_PATH, roomList);//Cập nhật thông tin ghế ngồi của phòng
    }

    private void purchaseSnack()
    {
        System.out.println("Danh sách các loại đồ ăn và đồ uống hiện có:");
    }

    private Movie getMovieIndexByName(String movieName)
    {
        return movieList.stream().filter(m -> m.getMovieName().equals(movieName)).findFirst().orElse(null);
    }

    private String getShowInfo(int showIndex)
    {
        ShowTime currentShow = showTimeList.get(showIndex);
        return "Phòng chiếu số: " + currentShow.getRoomId() + "\n" +
                "Giờ chiếu: " + currentShow.getOnAirTime().format(dateTimeFormatter);
    }

    private void displayShowTimeList(Movie moviePurchased)
    {
        System.out.printf("Đã chọn phim: %s. Dưới đây là danh sách lịch chiếu\n", moviePurchased.getMovieName());
        System.out.println(CONSOLECOLORS.YELLOW + "-----------------------------------------------------------------------------" + CONSOLECOLORS.RESET);
        for (int i = 1; i <= moviePurchased.getShowTimeId().size(); i++)
        {
            List<List<Seat>> seatList = showTimeList.get(i - 1).getRoomFromId(roomList).getSeatList();
            System.out.println(i + ". " + getShowInfo(i - 1));
            System.out.println("Danh sách ghế ngồi: (Màu đỏ là ghế đã có người đặt. " +
                    "Màu tím là ghế bạn đã chọn)");
            //Hiển thị danh sách ghế ngồi
            displaySeatList(seatList);
            System.out.println(CONSOLECOLORS.BLUE + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++" + CONSOLECOLORS.RESET);
        }
        System.out.println(CONSOLECOLORS.YELLOW + "-----------------------------------------------------------------------------" + CONSOLECOLORS.RESET);
    }

    private void displaySeatList(List<List<Seat>> seatList)
    {
        for (int j = 0; j < seatList.size(); j++)
        {
            System.out.print("                      ");
            for (int k = 0; k < seatList.get(j).size(); k++)
            {
                Seat currentSeat = seatList.get(j).get(k);
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
            System.out.println("Bạn đã chọn phòng chiếu số: " + showTimeChoice);
            return showTimeList.get(showTimeChoice - 1);
        }
    }

    private List<Seat> chooseSeats(List<List<Seat>> seatList)
    {
        List<Seat> seatsChosen = new ArrayList<>();
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
                        newSeatChosen.setSeatStatus(SEAT_STATUS.CHOSEN);
                        seatsChosen.add(newSeatChosen);
                        System.out.println(CONSOLECOLORS.GREEN + "Đã thêm ghế thành công" + CONSOLECOLORS.RESET);
                    } else if (newSeatChosen.getSeatStatus() == SEAT_STATUS.CHOSEN)
                    {
                        newSeatChosen.setSeatStatus(SEAT_STATUS.AVAILABLE);
                        seatsChosen.remove(newSeatChosen);
                        System.out.println(CONSOLECOLORS.GREEN_UNDERLINED + "Đã bỏ chọn ghế" + CONSOLECOLORS.RESET);
                    }
                    break;
                }
            }
            if (!seatExist)
            {
                System.out.println(CONSOLECOLORS.RED + "Mã ghế không đúng" + CONSOLECOLORS.RESET);
            }
        }
        return seatsChosen;
    }
}
