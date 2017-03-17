package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/24/2017.
 */

public class Recommend {
    private int ID;
    private int HobbyID;
    private int PriceID;
    private String LocationName;
    private String HobbyName;
    private String About;
    private String Address;
    private double X;
    private double Y;
    private String Description;
    private int Image;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getHobbyID() {
        return HobbyID;
    }

    public void setHobbyID(int hobbyID) {
        HobbyID = hobbyID;
    }

    public int getPriceID() {
        return PriceID;
    }

    public void setPriceID(int priceID) {
        PriceID = priceID;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public String getHobbyName() {
        return HobbyName;
    }

    public void setHobbyName(String hobbyName) {
        HobbyName = hobbyName;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
