package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/7/2017.
 */

public class Service {
    private int serviceID;
    private String serviceName;
    private int categoryID;
    private double unitPrice;
    private String description;
    private String image;

    public Service(int serviceID, String serviceName, int categoryID, double unitPrice, String description, String image){
        this.serviceID = serviceID;
        this.serviceName = serviceName;
        this.categoryID = categoryID;
        this.unitPrice = unitPrice;
        this.description = description;
        this.image = image;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        serviceID = serviceID;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        serviceName = serviceName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        categoryID = categoryID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        unitPrice = unitPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        image = image;
    }
}
