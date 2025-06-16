package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import model.Role;
import utils.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    private final DBContext dbContext;

    public RoleDAO() {
        this.dbContext = new DBContext();
    }

    private Role mapResultSetToRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setRoleID(rs.getInt("RoleID"));
        role.setRoleName(rs.getString("RoleName"));
        return role;
    }

   public Role getRoleById(int roleId) throws SQLException {
    String query = "SELECT * FROM Roles WHERE RoleID = ?";
    try (Connection conn = dbContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, roleId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                Role role = new Role();
                role.setRoleID(rs.getInt("RoleID"));
                role.setRoleName(rs.getString("RoleName"));
                return role;
            }
        }
    }
    return null; // Trả về null nếu không tìm thấy Role
}


    public Role getRoleByName(String roleName) throws SQLException {
        String query = "SELECT * FROM Roles WHERE RoleName = ?";
        try (ResultSet rs = dbContext.execSelectQuery(query, new Object[]{roleName})) {
            if (rs.next()) {
                return mapResultSetToRole(rs);
            }
        }
        return null;
    }

    public List<Role> getAllRoles() throws SQLException {
        List<Role> roles = new ArrayList<>();
        String query = "SELECT * FROM Roles ORDER BY RoleName";

        try (ResultSet rs = dbContext.execSelectQuery(query)) {
            while (rs.next()) {
                roles.add(mapResultSetToRole(rs));
            }
        }
        return roles;
    }
}
