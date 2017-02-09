package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class Order {
    private int orderID;
    private int roomID;
    private String custName;
    private String custIDNum;
    private Timestamp startTime;
    private Timestamp endTime;
    private double subTotal;
    private double discount;
    private double total;

    public Order(int orderID, int roomID, String custName, String custIDNum, Timestamp startTime, Timestamp endTime, double subTotal, double discount, double total){
        this.orderID = orderID;
        this.roomID = roomID;
        this.custName = custName;
        this.custIDNum = custIDNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subTotal = subTotal;
        this.discount = discount;
        this.total = total;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustIDNum() {
        return custIDNum;
    }

    public void setCustIDNum(String custIDNum) {
        this.custIDNum = custIDNum;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
