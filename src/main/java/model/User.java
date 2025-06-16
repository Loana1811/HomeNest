package model;

import java.sql.Date;

public class User {
    private int userID;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String status;
    private Date createdAt;
    private Role role;
    private Block block; // Chỉ dùng Block object, không cần blockID riêng

    // Constructor mặc định
    public User() {}

    // Constructor có tham số
    public User(int userID, String fullName, String email, String phoneNumber,
                String password, String status, Date createdAt, Role role, Block block) {
        this.userID = userID;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.status = status;
        this.createdAt = createdAt;
        this.role = role;
        this.block = block;
    }

    // Getters và Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setRoleID(int roleID) {
        if (this.role == null) {
            this.role = new Role();
        }
        this.role.setRoleID(roleID);
    }

    public int getRoleID() {
        return role != null ? role.getRoleID() : 0;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Integer getBlockID() {
        return (block != null) ? block.getBlockID() : null;
    }

    public void setBlockID(Integer blockID) {
        if (blockID == null) {
            this.block = null;
        } else {
            if (this.block == null) this.block = new Block();
            this.block.setBlockID(blockID);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", role=" + role +
                ", block=" + (block != null ? block.getBlockName() : "None") +
                '}';
    }
}
