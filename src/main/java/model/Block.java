/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Block {

    private int blockID;
    private String blockName;
    private int maxRooms;
    private int roomCount;
    private String status;

    public Block() {
    }

    public Block(int blockID, String blockName) {
        this.blockID = blockID;
        this.blockName = blockName;
    }

    public Block(int blockID, String blockName, int maxRooms, int roomCount, String status) {
        this.blockID = blockID;
        this.blockName = blockName;
        this.maxRooms = maxRooms;
        this.roomCount = roomCount;
        this.status = status;
    }

    public Block(String blockName, int maxRooms) {
        this.blockName = blockName;
        this.maxRooms = maxRooms;
    }

    public int getBlockID() {
        return blockID;
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
