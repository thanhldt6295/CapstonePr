package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.sql.Timestamp;

/**
 * Created by ThanhLDTSE61575 on 1/15/2017.
 */
public class OrderDetail {

    private int orderDetailID;
    private int orderID;
    private int serviceID;
    private int quantity;
    private Timestamp orderTime;
    private String status;
    private String note;
    private String staffID;
    private Timestamp deliverTime;

    public OrderDetail(int orderDetailID, int orderID, int serviceID, int quantity, Timestamp orderTime, String status, String note, String staffID,  Timestamp deliverTime) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.serviceID = serviceID;
        this.quantity = quantity;
        this.orderTime = orderTime;
        this.status = status;
        this.note = note;
        this.staffID = staffID;
        this.deliverTime = deliverTime;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public Timestamp getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Timestamp deliverTime) {
        this.deliverTime = deliverTime;
    }
}
