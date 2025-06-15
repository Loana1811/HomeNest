/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
    public class Tenant {
   private int tenantID;
    private int customerID;
    private Date joinDate;
    private String fullName;
    private String phone;
    private String email;

    // Constructors
    public Tenant() {
    }

    public Tenant(int tenantID, String fullName, String phone, String email) {
        this.tenantID = tenantID;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }

    public Tenant(int tenantID, int customerID, Date joinDate) {
        this.tenantID = tenantID;
        this.customerID = customerID;
        this.joinDate = joinDate;
    }
    

    public Tenant(int tenantID, int customerID, Date joinDate, String fullName, String phone, String email) {
        this.tenantID = tenantID;
        this.customerID = customerID;
        this.joinDate = joinDate;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
    }
    

    // Getter & Setter
    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    
}

