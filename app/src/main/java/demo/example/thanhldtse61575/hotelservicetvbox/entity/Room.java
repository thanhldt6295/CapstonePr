package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class Room {
    private int roomID;
    private int typeID;

    public Room(int roomID, int typeID){
        this.roomID = roomID;
        this.typeID = typeID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }
}
