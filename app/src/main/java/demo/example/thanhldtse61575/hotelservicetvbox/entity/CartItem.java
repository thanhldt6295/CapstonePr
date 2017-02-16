package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;

/**
 * Created by VULHSE61532 on 2/14/2017.
 */

public class CartItem implements Serializable {

    private int serviceID;
    private String serviceName;
    private int categoryID;
    private double unitPrice;
    private String description;
    private String image;
    private int quantity;
    private String comment;

    public CartItem(int serviceID, String serviceName, int categoryID, double unitPrice, String description, String image, int quantity, String comment) {
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.categoryID = categoryID;
        this.unitPrice = unitPrice;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
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
}
