package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class Order implements Serializable{
    private int OrderID;
    private int RoomID;
    private String CustName;
    private String CustIDNum;
    private long StartTime;
    private long EndTime;
    private double SubTotal;
    private double Discount;
    private double  Total;
    private double UnitPrice;

    public Order(int orderID, int roomID, String custName, String custIDNum, long startTime, long endTime, double subTotal, double discount, double total, double roomUPrice){
        this.OrderID = orderID;
        this.RoomID = roomID;
        this.CustName = custName;
        this.CustIDNum = custIDNum;
        this.StartTime = startTime;
        this.EndTime = endTime;
        this.SubTotal = subTotal;
        this.Discount = discount;
        this.Total = total;
        this.UnitPrice = roomUPrice;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getCustIDNum() {
        return CustIDNum;
    }

    public void setCustIDNum(String custIDNum) {
        CustIDNum = custIDNum;
    }

    public long getStartTime() {
        return StartTime;
    }

    public void setStartTime(long startTime) {
        StartTime = startTime;
    }

    public long getEndTime() {
        return EndTime;
    }

    public void setEndTime(long endTime) {
        EndTime = endTime;
    }

    public double getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }
}
