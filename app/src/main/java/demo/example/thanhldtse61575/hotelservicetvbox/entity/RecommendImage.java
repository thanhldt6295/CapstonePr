package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ASUS on 3/21/2017.
 */

public class RecommendImage {
    public int ID;
    public String Link;
    public int CategoryID;
    public int SecondID;

    public RecommendImage(int ID, String link, int categoryID, int secondID) {
        this.ID = ID;
        Link = link;
        CategoryID = categoryID;
        SecondID = secondID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getSecondID() {
        return SecondID;
    }

    public void setSecondID(int secondID) {
        SecondID = secondID;
    }
}
