package model;

import java.sql.Date;

public class Tenant {

    private int tenantID;
    private int customerID;
    private Date joinDate;

    private Customer customer; 

    public Tenant() {
    }

    public Tenant(int tenantID, int customerID, Date joinDate) {
        this.tenantID = tenantID;
        this.customerID = customerID;
        this.joinDate = joinDate;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
