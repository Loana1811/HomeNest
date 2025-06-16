/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author kloane
 */
public class Room {

    private int roomID;
    private String roomNumber;
    private double rentPrice;
    private Double area;
    private String status;
    private Integer blockID;
    private Integer categoryID;
    private String highlights;
    private String imagePath;
    private String description;
    private Timestamp postedDate;

    public Room() {
    }

    // Constructor đầy đủ (SELECT từ DB)
    public Room(int roomID, String roomNumber, double rentPrice, Double area,
                String status, Integer blockID, Integer categoryID,
                String highlights, String imagePath, String description, Timestamp postedDate) {
        this.roomID = roomID;
        this.roomNumber = roomNumber;
        this.rentPrice = rentPrice;
        this.area = area;
        this.status = status;
        this.blockID = blockID;
        this.categoryID = categoryID;
        this.highlights = highlights;
        this.imagePath = imagePath;
        this.description = description;
        this.postedDate = postedDate;
    }

    // Constructor dùng để INSERT (không có roomID)
    public Room(String roomNumber, double rentPrice, Double area,
                String status, Integer blockID, Integer categoryID,
                String highlights, String imagePath, String description, Timestamp postedDate) {
        this.roomNumber = roomNumber;
        this.rentPrice = rentPrice;
        this.area = area;
        this.status = status;
        this.blockID = blockID;
        this.categoryID = categoryID;
        this.highlights = highlights;
        this.imagePath = imagePath;
        this.description = description;
        this.postedDate = postedDate;
    }

    // Getters and Setters

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBlockID() {
        return blockID;
    }

    public void setBlockID(Integer blockID) {
        this.blockID = blockID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(Timestamp postedDate) {
        this.postedDate = postedDate;
    }
}
