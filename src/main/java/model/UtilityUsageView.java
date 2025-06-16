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
public class UtilityUsageView {
   private int readingId;
    private String roomNumber;
    private String utilityName;
    private double oldIndex;
    private double newIndex;
    private double priceUsed;
    private String changedBy;
    private java.sql.Date readingDate;

    public UtilityUsageView(int readingId, String roomNumber, String utilityName, double oldIndex, double newIndex, double priceUsed, String changedBy, Date readingDate) {
        this.readingId = readingId;
        this.roomNumber = roomNumber;
        this.utilityName = utilityName;
        this.oldIndex = oldIndex;
        this.newIndex = newIndex;
        this.priceUsed = priceUsed;
        this.changedBy = changedBy;
        this.readingDate = readingDate;
    }

    public int getReadingId() {
        return readingId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getUtilityName() {
        return utilityName;
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

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingId(int readingId) {
        this.readingId = readingId;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
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

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }

}

  