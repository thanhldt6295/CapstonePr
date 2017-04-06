package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.io.Serializable;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class FoodyCategory implements Serializable{
    private int CategoryID;
    private String CatgName;

    public FoodyCategory(int categoryID, String categoryName){
        this.CategoryID = categoryID;
        this.CatgName = categoryName;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        this.CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CatgName;
    }

    public void setCategoryName(String categoryName) {
        this.CatgName = categoryName;
    }
}
