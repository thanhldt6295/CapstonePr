package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;

/**
 * Created by ThanhLDTSE61575 on 2/18/2017.
 */

public class PendingItem implements Serializable {

    private String serviceName;
    private String categoryName;
    private double unitPrice;
    private String description;
    private String image;
    private int quantity;
    private String comment;
    private String orderTime;
    private String deliveryTime;

    public PendingItem(String serviceName, String categoryName, double unitPrice, String description,
                       String image, int quantity, String comment, String orderTime, String deliveryTime) {
        this.serviceName = serviceName;
        this.categoryName = categoryName;
        this.unitPrice = unitPrice;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.comment = comment;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
