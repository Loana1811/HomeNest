package model;

public class BillDetail {

    private int billDetailID;
    private int billID;
    private double roomRent;
    private double electricityCost;
    private double waterCost;
    private double wifiCost;

    public BillDetail() {
    }

    public BillDetail(int billDetailID, int billID, double roomRent, double electricityCost, double waterCost, double wifiCost) {
        this.billDetailID = billDetailID;
        this.billID = billID;
        this.roomRent = roomRent;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.wifiCost = wifiCost;
    }

    // Getter & Setter
    public int getBillDetailID() {
        return billDetailID;
    }

    public void setBillDetailID(int billDetailID) {
        this.billDetailID = billDetailID;
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public double getRoomRent() {
        return roomRent;
    }

    public void setRoomRent(double roomRent) {
        this.roomRent = roomRent;
    }

    public double getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(double electricityCost) {
        this.electricityCost = electricityCost;
    }

    public double getWaterCost() {
        return waterCost;
    }

    public void setWaterCost(double waterCost) {
        this.waterCost = waterCost;
    }

    public double getWifiCost() {
        return wifiCost;
    }

    public void setWifiCost(double wifiCost) {
        this.wifiCost = wifiCost;
    }
}
