package dao;

import model.User;
import model.Role;
import utils.DBContext;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final DBContext dbContext;
    private final RoleDAO roleDAO;

    public UserDAO() {
        this.dbContext = new DBContext();
        this.roleDAO = new RoleDAO();
    }

   private User mapResultSetToUser (ResultSet rs) throws SQLException {
    User user = new User();
    user.setUserID(rs.getInt("UserID")); // Đảm bảo tên cột đúng
    user.setFullName(rs.getString("FullName"));
    user.setEmail(rs.getString("Email"));
    user.setPhoneNumber(rs.getString("PhoneNumber"));
    user.setPassword(rs.getString("Password"));
    user.setStatus(rs.getString("Status"));
    user.setCreatedAt(rs.getDate("CreatedAt"));

    // Lấy RoleID từ ResultSet
    int roleId = rs.getInt("RoleID");
    if (!rs.wasNull()) { // Kiểm tra xem RoleID có null không
        Role role = roleDAO.getRoleById(roleId); // Gọi RoleDAO để lấy Role
        user.setRole(role);
    } else {
        user.setRole(null); // Nếu RoleID là null, set Role là null
    }

    int blockId = rs.getInt("BlockID");
if (!rs.wasNull() && roleId == 2) { // chỉ set block nếu là Manager
    BlockDAO blockDAO = new BlockDAO(); // tạo DAO
    user.setBlock(blockDAO.getBlockById(blockId));
}

    return user;
}


    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users ORDER BY CreatedAt DESC";

        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser (rs));
            }
        } catch (SQLException e) {
            System.err.println("Error in getAllUsers: " + e.getMessage());
            throw e;
        }

        return users;
    }

   public User getUserById(int userId)throws SQLException{
   String query = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser (rs);
                }
            }
        }
        return null;
   }

  public boolean addUser(User user) throws SQLException {
    String query = "INSERT INTO Users (FullName, Email, PhoneNumber, Password, RoleID, Status, BlockID) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String hashedPassword = hashMD5(user.getPassword());
    int result = dbContext.execUpdateQuery(query,
            user.getFullName(),
            user.getEmail(),
            user.getPhoneNumber(),
            hashedPassword,
            user.getRoleID(),
            user.getStatus() != null ? user.getStatus() : "Active",
            user.getBlockID()  // null nếu không có
    );
    return result > 0;
}


  public boolean updateUser(User user) throws SQLException {
    StringBuilder queryBuilder = new StringBuilder("UPDATE Users SET FullName = ?, Email = ?, PhoneNumber = ?, RoleID = ?, Status = ?");
    
    // Nếu có BlockID và là Manager
    boolean hasBlock = (user.getRoleID() == 2);
    if (hasBlock) {
        queryBuilder.append(", BlockID = ?");
    }

    // Nếu có password mới
    boolean hasPassword = (user.getPassword() != null && !user.getPassword().isEmpty());
    if (hasPassword) {
        queryBuilder.append(", Password = ?");
    }

    queryBuilder.append(" WHERE UserID = ?");
    String query = queryBuilder.toString();

    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {

        int paramIndex = 1;
        ps.setString(paramIndex++, user.getFullName());
        ps.setString(paramIndex++, user.getEmail());
        ps.setString(paramIndex++, user.getPhoneNumber());
        ps.setInt(paramIndex++, user.getRoleID());
        ps.setString(paramIndex++, user.getStatus());

        if (hasBlock) {
            ps.setObject(paramIndex++, user.getBlockID()); // Có thể là null nếu chưa set
        }

        if (hasPassword) {
            ps.setString(paramIndex++, user.getPassword()); // Đã hash từ trước
        }

        ps.setInt(paramIndex, user.getUserID());

        return ps.executeUpdate() > 0;
    }
}


    public boolean deleteUser (int userId) throws SQLException {
        String query = "DELETE FROM Users WHERE UserID = ?";
        int result = dbContext.execUpdateQuery(query, userId);
        return result > 0;
    }

public boolean deactivateUser(int userId) throws SQLException {
    String query = "UPDATE Users SET Status = 'Inactive' WHERE UserID = ?";
    int result = 0;
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, userId);
        result = ps.executeUpdate();
    }
    return result > 0;
}


    public boolean activateUser (int userId) throws SQLException {
        String query = "UPDATE Users SET Status = 'Active' WHERE UserID = ?";
        int result = dbContext.execUpdateQuery(query, userId);
        return result > 0;
    }

    public User login(String phoneNumber, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE PhoneNumber = ? AND Status = 'Active'";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = mapResultSetToUser (rs);
                    if (user.getPassword().equals(hashMD5(password))) { // Kiểm tra mật khẩu
                        return user;
                    }
                }
            }
        }
        return null;
    }

     public String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

   
}
