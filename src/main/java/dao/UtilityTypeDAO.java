/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.UtilityType;
import utils.DBContext;

/**
 *
 * @author kloane
 */
public class UtilityTypeDAO {

    public List<UtilityType> getAll() throws SQLException {
        List<UtilityType> list = new ArrayList<>();
        String sql = "SELECT UtilityTypeID, UtilityName, UnitPrice, Unit, IsSystem FROM UtilityTypes";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new UtilityType(
                        rs.getInt("UtilityTypeID"),
                        rs.getString("UtilityName"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("Unit"),
                        rs.getBoolean("IsSystem")
                ));

            }
        }
        return list;
    }

    public UtilityType getById(int id) throws SQLException {
        String sql = "SELECT * FROM UtilityTypes WHERE UtilityTypeID = ?";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new UtilityType(
                        id,
                        rs.getString("UtilityName"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("Unit"),
                        rs.getBoolean("IsSystem")
                );

            }
        }
        return null;
    }

    public void insert(UtilityType u) throws SQLException {
        String sql = "INSERT INTO UtilityTypes (UtilityName, UnitPrice, Unit) VALUES (?, ?, ?)";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setDouble(2, u.getPrice());
            ps.setString(3, u.getUnit());
            ps.executeUpdate();
        }
    }

    public void update(UtilityType u) throws SQLException {
        String sql = "UPDATE UtilityTypes SET UtilityName = ?, UnitPrice = ?, Unit = ? WHERE UtilityTypeID = ?";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getName());
            ps.setDouble(2, u.getPrice());
            ps.setString(3, u.getUnit());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM UtilityTypes WHERE UtilityTypeID = ?";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Object[]> getAllTypeIdName() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT UtilityTypeID, UtilityName FROM UtilityTypes";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{rs.getInt("UtilityTypeID"), rs.getString("UtilityName")});
            }
        }
        return list;
    }

    public int getLastInsertedId() throws SQLException {
        String sql = "SELECT TOP 1 UtilityTypeID FROM UtilityTypes ORDER BY UtilityTypeID DESC";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return -1;
    }

    public boolean isUtilityTypeInUse(int utilityTypeId) throws SQLException {
        String sql
                = "SELECT COUNT(*) FROM ("
                + " SELECT UtilityTypeID FROM UtilityReadings WHERE UtilityTypeID = ? AND (OldReading > 0 OR NewReading > 0) "
                + " UNION "
                + " SELECT UtilityTypeID FROM BillDetails WHERE UtilityTypeID = ? "
                + ") AS Combined";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utilityTypeId);
            ps.setInt(2, utilityTypeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean isUtilityNameExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM UtilityTypes WHERE UtilityName = ?";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

}
