package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;

/**
 * Created by ThanhLDTSE61575 on 2/7/2017.
 */

public class Service implements Serializable{
    private int ServiceID;
    private String ServiceName;
    private int CategoryID;
    private double UnitPrice;
    private String Description;
    private String Image;

    public Service(int ServiceID, String ServiceName, int CategoryID, double UnitPrice, String Description, String Image){
        this.ServiceID = ServiceID;
        this.ServiceName = ServiceName;
        this.CategoryID = CategoryID;
        this.UnitPrice = UnitPrice;
        this.Description = Description;
        this.Image = Image;
    }

    public int getServiceID() {
        return ServiceID;
    }

    public void setServiceID(int ServiceID) {
        ServiceID = ServiceID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String ServiceName) {
        ServiceName = ServiceName;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int CategoryID) {
        CategoryID = CategoryID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        Description = Description;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double UnitPrice) {
        UnitPrice = UnitPrice;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        Image = Image;
    }
}
