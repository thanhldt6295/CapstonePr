package demo.example.thanhldtse61575.hotelservicetvbox.entity;

/**
 * Created by ThanhLDTSE61575 on 1/15/2017.
 */
public class OrderDetail {

    private int OrderDetailID;
    private int OrderID;
    private int ServiceID;
    private String ServiceName;
    private int categoryID;
    private String categoryName;
    private double unitPrice;
    private String Description;
    private String Image;
    private int Quantity;
    private String Note;
    private long OrderTime;
    private long DeliverTime;
    private String StaffID;
    private String Status;

    public OrderDetail(int orderDetailID, int orderID, int serviceID, String serviceName, int categoryID, String categoryName,
                       double unitPrice, String description, String image, int quantity, String comment, long orderTime,
                       long deliveryTime, String staffID,  String status) {
        this.OrderDetailID = orderDetailID;
        this.OrderID = orderID;
        this.ServiceID = serviceID;
        this.ServiceName = serviceName;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.unitPrice = unitPrice;
        this.Description = description;
        this.Image = image;
        this.Quantity = quantity;
        this.Note = comment;
        this.OrderTime = orderTime;
        this.DeliverTime = deliveryTime;
        this.StaffID = staffID;
        this.Status = status;
    }

    public int getOrderDetailID() {
        return OrderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        OrderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getServiceID() {
        return ServiceID;
    }

    public void setServiceID(int serviceID) {
        ServiceID = serviceID;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public long getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(long orderTime) {
        OrderTime = orderTime;
    }

    public long getDeliverTime() {
        return DeliverTime;
    }

    public void setDeliverTime(long deliverTime) {
        DeliverTime = deliverTime;
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
        StaffID = staffID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
