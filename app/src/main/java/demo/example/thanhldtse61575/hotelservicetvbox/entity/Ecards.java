package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;

/**
 * Created by ThanhLDTSE61575 on 3/13/2017.
 */

public class Ecards implements Serializable {
    private int ID;
    private String Message;
    private String Image;

    public Ecards(String image){
        this.Image = image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
