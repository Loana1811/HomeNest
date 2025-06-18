package Model;

import java.util.Date;

public class Contract {

    private int contractId;
    private int tenantId;
    private String tenantName;
    private int roomId;
    private String roomNumber;
    private Date startDate;
    private Date endDate;
    private String contractstatus;
    private Date contractcreatedAt;
    private double amount;

    public Contract() {
    }

    public Contract(int contractId, int tenantId, int roomId, Date startDate, Date endDate, String contractstatus, Date contractcreatedAt) {
        this.contractId = contractId;
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractstatus = contractstatus;
        this.contractcreatedAt = contractcreatedAt;
    }
    
    
    

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContractstatus() {
        return contractstatus;
    }

    public void setContractstatus(String contractstatus) {
        this.contractstatus = contractstatus;
    }

    public Date getContractcreatedAt() {
        return contractcreatedAt;
    }

    public void setContractcreatedAt(Date contractcreatedAt) {
        this.contractcreatedAt = contractcreatedAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

   
}
