package model;

public class IncurredFeeType {

    private int incurredFeeTypeID;
    private String feeName;
    private double defaultAmount;

    public IncurredFeeType() {
    }

    public IncurredFeeType(int incurredFeeTypeID, String feeName, double defaultAmount) {
        this.incurredFeeTypeID = incurredFeeTypeID;
        this.feeName = feeName;
        this.defaultAmount = defaultAmount;
    }

    // Getter & Setter
    public int getIncurredFeeTypeID() {
        return incurredFeeTypeID;
    }

    public void setIncurredFeeTypeID(int incurredFeeTypeID) {
        this.incurredFeeTypeID = incurredFeeTypeID;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public double getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }
}
