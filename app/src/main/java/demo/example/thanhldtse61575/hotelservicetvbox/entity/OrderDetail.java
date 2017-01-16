package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by VULHSE61532 on 1/15/2017.
 */
public class OrderDetail {

    private String name;
    private String category;
    private float unitPrice;
    private int quantity;

    public OrderDetail(String name, String category, float unitPrice, int quantity) {
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
