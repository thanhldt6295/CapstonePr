package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.sql.Timestamp;

/**
 * Created by ThanhLDTSE61575 on 1/15/2017.
 */
public class OrderDetail {

    private int orderDetailID;
    private int orderID;
    private int serviceID;
    private String serviceName;
    private int categoryID;
    private String categoryName;
    private double unitPrice;
    private String description;
    private String image;
    private int quantity;
    private String comment;
    private String orderTime;
    private String deliveryTime;
    private String staffID;
    private String status;

    public OrderDetail(int orderDetailID, int orderID, int serviceID, String serviceName, int categoryID, String categoryName,
                       double unitPrice, String description, String image, int quantity, String comment, String orderTime,
                       String deliveryTime, String staffID,  String status) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.unitPrice = unitPrice;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.comment = comment;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.staffID = staffID;
        this.status = status;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
