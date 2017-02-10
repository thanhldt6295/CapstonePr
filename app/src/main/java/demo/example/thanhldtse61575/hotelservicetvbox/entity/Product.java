package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/10/2017.
 */

public class Product {
    private int image;
    private String serviceName;
    private String unitPrice;
    private String description;

    public Product(int image, String serviceName, String unitPrice, String description) {
        this.image = image;
        this.serviceName = serviceName;
        this.unitPrice = unitPrice;
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
