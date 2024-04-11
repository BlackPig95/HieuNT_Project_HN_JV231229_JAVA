package ra.business.entity.movie;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.CONSTANT;
import ra.business.config.InputMethods;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ShowTime implements Serializable
{
    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private String showTimeId;//String vì có thể có nhiều ngày chiếu khác nhau
    private LocalDateTime onAirTime;
    private byte roomId;

    public ShowTime()
    {
    }

    public ShowTime(String showTimeId, LocalDateTime onAirTime, byte roomId)
    {
        this.showTimeId = showTimeId;
        this.onAirTime = onAirTime;
        this.roomId = roomId;
    }

    public String getShowTimeId()
    {
        return showTimeId;
    }

    public void setShowTimeId(String showTimeId)
    {
        this.showTimeId = showTimeId;
    }

    public LocalDateTime getOnAirTime()
    {
        return onAirTime;
    }

    public void setOnAirTime(LocalDateTime onAirTime)
    {
        this.onAirTime = onAirTime;
    }

    public byte getRoomId()
    {
        return roomId;
    }

    public void setRoomId(byte roomId)
    {
        this.roomId = roomId;
    }

    public void inputData(List<Room> roomList, List<ShowTime> showTimeList, boolean isAdding)
    {

        inputShowTimeId(showTimeList, isAdding);
        inputOnAirTime();
        inputRoomId(roomList);
    }

    public void displayData()
    {
        System.out.printf("Mã lịch chiếu: %s | Thời gian chiếu: %s | Phòng chiếu: %d \n",
                this.showTimeId, this.onAirTime.format(dateTimeFormatter), this.roomId);
    }

    public Room getRoomFromId(List<Room> roomList)
    {
        return roomList.stream().filter(r -> r.getRoomId() == this.roomId).findFirst().orElse(null);
    }

    private void inputShowTimeId(List<ShowTime> showTimeList, boolean isAdding)
    {
        while (true)
        {
            System.out.println("Nhập mã của lịch chiếu này:");
            String newId = InputMethods.nextLine();
            if (showTimeList.stream().anyMatch(s -> s.showTimeId.equals(newId)))
            {
                if (!isAdding)//Nếu là hành động update
                {//Và id trùng với chính bản thân nó => Không cần set lại, return ngay
                    if (this == showTimeList.stream().
                            filter(s -> s.showTimeId.equals(newId)).findFirst().get())
                    {
                        return;
                    }
                }
                //Nếu không phải => trùng với phòng khác => không cho set
                System.out.println(CONSOLECOLORS.RED + "Mã lịch chiếu đã tồn tại. Vui lòng nhập mã khác" + CONSOLECOLORS.RESET);
                continue;
            }
            this.showTimeId = newId;
            break;
        }
    }

    private void inputOnAirTime()
    {
        while (true)
        {
            System.out.println("Nhập khung giờ chiếu: (Định dạng dd-MM-yyyy HH:mm)");
            String onAir = InputMethods.nextLine();
            try
            {
                this.onAirTime = LocalDateTime.parse(onAir, dateTimeFormatter);
                return;
            } catch (DateTimeParseException e)
            {
                System.out.println(CONSOLECOLORS.RED + "Vui lòng nhập đúng định dạng." + CONSOLECOLORS.RESET);
            }
        }
    }

    private void inputRoomId(List<Room> roomList)
    {
        System.out.println("Danh sách các phòng chiếu hiện có:");
        roomList.forEach(r -> r.displayData());
        while (true)
        {
            System.out.println("Nhập mã số phòng chiếu muốn sử dụng:");
            byte choice = InputMethods.nextByte();
            Room roomChosen = roomList.stream().filter(r -> r.getRoomId() == choice).findFirst().orElse(null);
            if (roomChosen == null)
            {
                System.out.println(CONSOLECOLORS.RED + CONSTANT.CHOICE_NOT_AVAI + CONSOLECOLORS.RESET);
            } else
            {
                roomId = roomChosen.getRoomId();
                return;
            }
        }
    }
}
