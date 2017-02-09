package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class RoomType {
    private int roomTypeID;
    private String rTypName;
    private int capacity;
    private String description;
    private double unitPrice;

    public RoomType(int roomTypeID, String rTypName, int capacity, String description, double unitPrice){
        this.roomTypeID = roomTypeID;
        this.rTypName = rTypName;
        this.capacity = capacity;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public int getRoomTypeID() {
        return roomTypeID;
    }

    public void setRoomTypeID(int roomTypeID) {
        this.roomTypeID = roomTypeID;
    }

    public String getrTypName() {
        return rTypName;
    }

    public void setrTypName(String rTypName) {
        this.rTypName = rTypName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
