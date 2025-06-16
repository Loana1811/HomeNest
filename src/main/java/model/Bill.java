package model;

import java.sql.Date;

public class Bill {

    private int billID;
    private int contractID;
    private Date billDate;
    private double totalAmount;
    private String status;

    public Bill() {
    }

    public Bill(int billID, int contractID, Date billDate, double totalAmount, String status) {
        this.billID = billID;
        this.contractID = contractID;
        this.billDate = billDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    // Getter & Setter
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getContractID() {
        return contractID;
    }

    public void setContractID(int contractID) {
        this.contractID = contractID;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
