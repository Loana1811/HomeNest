package model;

import java.sql.Date;

public class UtilityReading {

    private int readingID;
    private int utilityTypeID;
    private int roomID;
    private Date readingDate;
    private double oldReading;
    private double newReading;
    private double priceUsed;
    private double oldPrice;
    private String changedBy;

    public UtilityReading() {
    }

    public UtilityReading(int readingID, int utilityTypeID, int roomID, Date readingDate, double oldReading, double newReading, double priceUsed, double oldPrice, String changedBy) {
        this.readingID = readingID;
        this.utilityTypeID = utilityTypeID;
        this.roomID = roomID;
        this.readingDate = readingDate;
        this.oldReading = oldReading;
        this.newReading = newReading;
        this.priceUsed = priceUsed;
        this.oldPrice = oldPrice;
        this.changedBy = changedBy;
    }

    // Getter & Setter
    public int getReadingID() {
        return readingID;
    }

    public void setReadingID(int readingID) {
        this.readingID = readingID;
    }

    public int getUtilityTypeID() {
        return utilityTypeID;
    }

    public void setUtilityTypeID(int utilityTypeID) {
        this.utilityTypeID = utilityTypeID;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }

    public double getOldReading() {
        return oldReading;
    }

    public void setOldReading(double oldReading) {
        this.oldReading = oldReading;
    }

    public double getNewReading() {
        return newReading;
    }

    public void setNewReading(double newReading) {
        this.newReading = newReading;
    }

    public double getPriceUsed() {
        return priceUsed;
    }

    public void setPriceUsed(double priceUsed) {
        this.priceUsed = priceUsed;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }
}
