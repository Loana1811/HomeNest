/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Contract {

    private int contractId;
    private int tenantId;
    private String tenantName;
    private int roomId;
    private Date startDate;
    private Date endDate;
    private String status;
    private Date createdAt;
    private double amount;

    public Contract() {
    }

//    public Contract(int contractId, int tenantId, String tenantName, int roomId, Date startDate, Date endDate, String status, Date createdAt, double amount) {
//        this.contractId = contractId;
//        this.tenantId = tenantId;
//        this.tenantName = tenantName;
//        this.roomId = roomId;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.status = status;
//        this.createdAt = createdAt;
//        this.amount = amount;
//    }

    public Contract(int contractId, int roomId, Date startDate, Date endDate, String status, Date createdAt) {
        this.contractId = contractId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
