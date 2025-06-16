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
public class UtilityReading {
    private int readingId;
    private int roomId;
    private int utilityTypeId;
    private double oldIndex;
    private double newIndex;
    private double priceUsed;
    private String changedBy;
    private Date createdAt;

    public UtilityReading(int readingId, int roomId, int utilityTypeId, double oldIndex, double newIndex, double priceUsed, String changedBy, Date createdAt) {
        this.readingId = readingId;
        this.roomId = roomId;
        this.utilityTypeId = utilityTypeId;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.priceUsed = priceUsed;
        this.changedBy = changedBy;
        this.createdAt = createdAt;
    }

    public int getReadingId() {
        return readingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getUtilityTypeId() {
        return utilityTypeId;
    }

    public double getOldIndex() {
        return oldIndex;
    }

    public double getNewIndex() {
        return newIndex;
    }

    public double getPriceUsed() {
        return priceUsed;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setReadingId(int readingId) {
        this.readingId = readingId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public void setUtilityTypeId(int utilityTypeId) {
        this.utilityTypeId = utilityTypeId;
    }

    public void setOldIndex(double oldIndex) {
        this.oldIndex = oldIndex;
    }

    public void setNewIndex(double newIndex) {
        this.newIndex = newIndex;
    }

    public void setPriceUsed(double priceUsed) {
        this.priceUsed = priceUsed;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    
}
