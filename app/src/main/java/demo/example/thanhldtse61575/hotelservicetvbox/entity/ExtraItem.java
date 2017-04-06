package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;

/**
 * Created by ThanhLDTSE61575 on 4/6/2017.
 */

public class ExtraItem implements Serializable {
    private int ExtraID;
    private String ExtraName;
    private String Description;
    private String Image;

    public int getExtraID() {
        return ExtraID;
    }

    public void setExtraID(int extraID) {
        ExtraID = extraID;
    }

    public String getExtraName() {
        return ExtraName;
    }

    public void setExtraName(String extraName) {
        ExtraName = extraName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
