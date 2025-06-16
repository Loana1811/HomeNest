package model;

public class IncurredFee {
    private int incurredFeeID;
    private int billID;
    private int incurredFeeTypeID;
    private double amount;

    public IncurredFee() {}

    public IncurredFee(int incurredFeeID, int billID, int incurredFeeTypeID, double amount) {
        this.incurredFeeID = incurredFeeID;
        this.billID = billID;
        this.incurredFeeTypeID = incurredFeeTypeID;
        this.amount = amount;
    }

    // Getter & Setter
    public int getIncurredFeeID() { return incurredFeeID; }
    public void setIncurredFeeID(int incurredFeeID) { this.incurredFeeID = incurredFeeID; }
    public int getBillID() { return billID; }
    public void setBillID(int billID) { this.billID = billID; }
    public int getIncurredFeeTypeID() { return incurredFeeTypeID; }
    public void setIncurredFeeTypeID(int incurredFeeTypeID) { this.incurredFeeTypeID = incurredFeeTypeID; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
