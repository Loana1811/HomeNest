package model;

import java.sql.Date;

public class RentalRequest {

    private int requestID;
    private int customerID;
    private int roomID;
    private Date requestDate;
    private String status;
    private Integer approvedBy;
    private Date approvedDate;

    public RentalRequest() {
    }

    public RentalRequest(int requestID, int customerID, int roomID, Date requestDate, String status, Integer approvedBy, Date approvedDate) {
        this.requestID = requestID;
        this.customerID = customerID;
        this.roomID = roomID;
        this.requestDate = requestDate;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approvedDate = approvedDate;
    }

    // Getter & Setter
    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Integer approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }
}
