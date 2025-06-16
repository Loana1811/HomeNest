/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author kloane
 */
public class UtilityHistoryView {
private int utilityTypeId;
    private String utilityName;
    private double oldPrice;
    private double newPrice;
    private String changedBy;
    private Date createdAt;

    public UtilityHistoryView(int utilityTypeId, String utilityName, double oldPrice, double newPrice, String changedBy, Date createdAt) {
    this.utilityTypeId = utilityTypeId;
    this.utilityName = utilityName;
    this.oldPrice = oldPrice;
    this.newPrice = newPrice;
    this.changedBy = changedBy;
    this.createdAt = createdAt; // 
}


    public int getUtilityTypeId() {
        return utilityTypeId;
    }

    public String getUtilityName() {
        return utilityName;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

   

    public void setUtilityTypeId(int utilityTypeId) {
        this.utilityTypeId = utilityTypeId;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

   
    
}

