package model;
import java.sql.Date;

public class Customer {
    private int customerID;
    private String fullName;
    private String phoneNumber;
    private String cccd;
    private String gender;
    private Date birthDate;
    private String address;
    private String email;
    private String status;
    private String customerPassword; // Đổi thành camelCase theo convention
    
    // Constructors
    public Customer() {}
    
    public Customer(int customerID, String fullName, String phoneNumber, String cccd, 
                   String gender, Date birthDate, String address, String email, 
                   String status, String customerPassword) {
        this.customerID = customerID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
        this.status = status;
        this.customerPassword = customerPassword;
    }
    
    // Getters and Setters
    public int getCustomerID() { 
        return customerID; 
    }
    
    public void setCustomerID(int customerID) { 
        this.customerID = customerID; 
    }
    
    public String getFullName() { 
        return fullName; 
    }
    
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }
    
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }
    
    public String getCccd() { 
        return cccd; 
    }
    
    public void setCccd(String cccd) { 
        this.cccd = cccd; 
    }
    
    public String getGender() { 
        return gender; 
    }
    
    public void setGender(String gender) { 
        this.gender = gender; 
    }
    
    public Date getBirthDate() { 
        return birthDate; 
    }
    
    public void setBirthDate(Date birthDate) { 
        this.birthDate = birthDate; 
    }
    
    public String getAddress() { 
        return address; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }
    
    public String getCustomerPassword() {
        return customerPassword;
    }
    
    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }
}