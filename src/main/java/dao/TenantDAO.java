/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBContext;

/**
 *
 * @author ThanhTruc
 */
public class TenantDAO {
    private final DBContext dbContext = new DBContext();

    public boolean isTenant(int customerID) throws SQLException {
        String query = "SELECT 1 FROM Tenants WHERE CustomerID = ?";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true nếu có dòng kết quả
            }
        }
    }
}
