package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 2/9/2017.
 */

public class Staff {
    private String staffIDNum;
    private String staffName;
    private String role;
    private String address;
    private String phoneNumber;

    public Staff (String staffIDNum, String staffName, String role, String address, String phoneNumber){
        this.staffIDNum = staffIDNum;
        this.staffName = staffName;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getStaffIDNum() {
        return staffIDNum;
    }

    public void setStaffIDNum(String staffIDNum) {
        staffIDNum = staffIDNum;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        staffName = staffName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }
}
