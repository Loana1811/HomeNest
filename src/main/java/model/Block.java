package model;

public class Block {
    private int blockID;
    private String blockName;
    private int roomCount;
    private int maxRooms;
    private String status;

    // Constructors
    public Block() {}

    public Block(int blockID, String blockName) {
        this.blockID = blockID;
        this.blockName = blockName;
    }

    // Getters & Setters
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

    public int getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getMaxRooms() {
        return maxRooms;
    }

    public void setMaxRooms(int maxRooms) {
        this.maxRooms = maxRooms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
