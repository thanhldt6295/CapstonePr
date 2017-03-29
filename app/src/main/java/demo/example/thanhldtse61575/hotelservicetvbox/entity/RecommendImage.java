package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ASUS on 3/21/2017.
 */

public class RecommendImage {
    public int ID;
    public String Link;
    public int RecommendID;

    public RecommendImage(int ID, String link, int recommendID) {
        this.ID = ID;
        Link = link;
        RecommendID = recommendID;
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

    public int getRecommendID() {
        return RecommendID;
    }

    public void setRecommendID(int recommendID) {
        RecommendID = recommendID;
    }
}
