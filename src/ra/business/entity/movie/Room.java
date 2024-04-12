package ra.business.entity.movie;

import ra.business.config.CONSOLECOLORS;
import ra.business.config.InputMethods;
import ra.business.design.IAdminPaginable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable, IAdminPaginable
{
    private byte roomId;
    private List<List<Seat>> seatList;

    public Room()
    {
        seatList = new ArrayList<>();
    }

    public Room(byte roomId, List<List<Seat>> seatList)
    {
        this.seatList = seatList;
        this.roomId = roomId;
    }

    public byte getRoomId()
    {
        return roomId;
    }

    public void setRoomId(byte roomId)
    {
        this.roomId = roomId;
    }

    public List<List<Seat>> getSeatList()
    {
        return seatList;
    }

    public void setSeatList(List<List<Seat>> seatList)
    {
        this.seatList = seatList;
    }

    public void inputData(List<Room> roomList, boolean isAdding)
    {
        inputRoomId(roomList, isAdding);
        inputRoomSeat();
    }

    @Override
    public void displayData()
    {
        System.out.printf("Phòng chiếu số: %d | Số lượng ghế ngồi: %d \n", this.roomId, countSeat());
        System.out.println("────────────────────────────────────────────────────────────────────────────────────");
    }

    public void inputRoomId(List<Room> roomList, boolean isAdding)
    {
        while (true)
        {
            System.out.println("Nhập mã số phòng: ");
            byte newRoomId = InputMethods.nextByte();
            boolean roomExisted = false;
            for (Room existingRoom : roomList)
            {
                if (existingRoom.roomId == newRoomId)
                {//Nếu là hành động update và id trùng với chính bản thân nó thì không cần set mới => return ngay
                    if (!isAdding && this == existingRoom)
                    {
                        return;
                    }
                    //Nếu là hành động thêm mới, hoặc trùng id với phòng khác => Không cho set
                    System.out.println(CONSOLECOLORS.RED + "Mã phòng này đã tồn tại" + CONSOLECOLORS.RESET);
                    roomExisted = true;
                    break;
                }
            }
            if (!roomExisted)
            {
                this.roomId = newRoomId;
                break;
            }
        }
    }

    public void inputRoomSeat()
    {
        while (true)
        {
            System.out.println("Phòng chiếu này có bao nhiêu hàng ghế?");
            byte row = InputMethods.nextByte();
            if (row <= 2)
            {
                System.out.println(CONSOLECOLORS.RED + "Số lượng hàng ghế quá ít" + CONSOLECOLORS.RESET);
                continue;
            }
            while (true)
            {
                System.out.println("Mỗi hàng gồm bao nhiêu ghế?");
                byte seatPerRow = InputMethods.nextByte();
                if (seatPerRow <= 3)
                {
                    System.out.println("Số lượng ghế quá ít");
                    continue;
                }
                //Nếu nhập hàng ghế và số lượng ghế thỏa mãn thì set tên các ghế vào danh sách
                //sau đó return để phá vòng lặp
                for (int i = 0; i < row; i++)
                {
                    //Ép kiểu về ASCII code để có thể thực hiện phép cộng
                    byte rowName = (byte) ('A' + i);
                    List<Seat> rowList = new ArrayList<>();
                    for (int j = 1; j <= seatPerRow; j++)
                    {//Nối chuỗi A,B,C,... + j
                        Seat newSeat = new Seat();
                        //Ép lại về kiểu char
                        String newSeatName = String.valueOf((char) rowName) + j;
                        //Tạo ra seat mới và đặt tên
                        newSeat.setSeatName(newSeatName);
                        //Thêm vào danh sách các hàng ghế
                        rowList.add(newSeat);
                    }
                    //Thêm hàng ghế vào danh sách tất cả các chỗ ngồi
                    this.seatList.add(rowList);
                }
                return;
            }
        }
    }

    private int countSeat()
    {
        int count = 0;
        for (int i = 0; i < seatList.size(); i++)
        {
            for (int j = 0; j < seatList.get(i).size(); j++)
            {
                count++;
            }
        }
        return count;
    }
}
