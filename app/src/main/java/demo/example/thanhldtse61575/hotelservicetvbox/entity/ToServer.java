package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.util.List;

/**
 * Created by ASUS on 2/28/2017.
 */

public class ToServer {
    private int roomid;
    private double deliveryTime;
    private List<CartItem> list;

    public ToServer(double deliveryTime, List<CartItem> list, int roomid) {
        this.deliveryTime = deliveryTime;
        this.list = list;
        this.roomid = roomid;
    }

    public int getRoomid() {
        return roomid;

    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public List<CartItem> getList() {
        return list;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setList(List<CartItem> list) {
        this.list = list;
    }
}
