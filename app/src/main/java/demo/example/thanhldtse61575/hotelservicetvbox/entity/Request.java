package demo.example.thanhldtse61575.hotelservicetvbox.entity;

import java.sql.Timestamp;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class Request {
    private int id;
    private String name;
    private int roomID;
    private String custName;
    private String custIDNum;
    private Timestamp startTime;
    private Timestamp endTime;
    private String staffID;

    public Request(int id, String name, int roomID, String custName, String custIDNum, Timestamp startTime, Timestamp endTime, String staffID){
        this.id = id;
        this.name = name;
        this.roomID = roomID;
        this.custName = custName;
        this.custIDNum = custIDNum;
        this.startTime = startTime;
        this.endTime = endTime;
        this.staffID = staffID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getCustIDNum() {
        return custIDNum;
    }

    public void setCustIDNum(String custIDNum) {
        this.custIDNum = custIDNum;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }
}
