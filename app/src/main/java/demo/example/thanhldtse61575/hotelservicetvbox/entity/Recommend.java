package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/24/2017.
 */

public class Recommend {
    private int id;
    private int hobbyID;
    private int image;
    private String address;
    private String description;
    private int priceID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHobbyID() {
        return hobbyID;
    }

    public void setHobbyID(int hobbyID) {
        this.hobbyID = hobbyID;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriceID() {
        return priceID;
    }

    public void setPriceID(int priceID) {
        this.priceID = priceID;
    }
}
