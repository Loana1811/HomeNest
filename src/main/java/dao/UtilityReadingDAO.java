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
import model.UtilityReading;
import model.UtilityUsageView;
import utils.DBContext;

/**
 *
 * @author kloane
 */
public class UtilityReadingDAO {

    public void insert(UtilityReading ur) throws SQLException {
        String sql
                = "INSERT INTO UtilityReadings "
                + "(RoomID, UtilityTypeID, ReadingDate, OldReading, NewReading, PriceUsed, ChangedBy, CreatedAt) "
                + "VALUES (?, ?, GETDATE(), ?, ?, ?, ?, GETDATE())";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ur.getRoomId());
            ps.setInt(2, ur.getUtilityTypeId());
            ps.setDouble(3, ur.getOldIndex());  
            ps.setDouble(4, ur.getNewIndex()); 
            ps.setDouble(5, ur.getPriceUsed());
            ps.setString(6, ur.getChangedBy());
            ps.executeUpdate();
        }
    }

    public double getLatestIndex(int roomId, int utilityTypeId) throws SQLException {
        String sql
                = "SELECT TOP 1 NewReading "
                + "FROM UtilityReadings "
                + "WHERE RoomID = ? AND UtilityTypeID = ? "
                + "ORDER BY CreatedAt DESC";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setInt(2, utilityTypeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0;
    }

    public List<UtilityUsageView> getAllUsages() throws SQLException {
        List<UtilityUsageView> list = new ArrayList<>();
        String sql
                = "SELECT ur.ReadingID, r.RoomNumber, ut.UtilityName, "
                + "       ur.OldReading   AS OldIndex, "
                + "       ur.NewReading   AS NewIndex, "
                + "       ut.UnitPrice    AS UnitPrice, "
                + "       (ur.NewReading - ur.OldReading) * ut.UnitPrice AS PriceUsed, "
                + "       ur.ChangedBy, "
                + "       ur.ReadingDate  AS ReadingDate "
                + "FROM UtilityReadings ur "
                + "  JOIN Rooms        r  ON ur.RoomID        = r.RoomID "
                + "  JOIN UtilityTypes ut ON ur.UtilityTypeID = ut.UtilityTypeID "
                + "ORDER BY ur.ReadingDate DESC";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new UtilityUsageView(
                        rs.getInt("ReadingID"),
                        rs.getString("RoomNumber"),
                        rs.getString("UtilityName"),
                        rs.getDouble("OldIndex"),
                        rs.getDouble("NewIndex"),
                        rs.getDouble("PriceUsed"),
                        rs.getString("ChangedBy"),
                        rs.getDate("ReadingDate")
                ));
            }
        }
        return list;
    }

    public void assignUtilityToRoom(int roomId, int utilityTypeId) throws SQLException {
        String sql = "INSERT INTO UtilityReadings (RoomID, UtilityTypeID, ReadingDate, OldReading, NewReading) VALUES (?, ?, GETDATE(), 0, 0)";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roomId);
            ps.setInt(2, utilityTypeId);
            ps.executeUpdate();
        }
    }

    public void deleteZeroReadingsByUtilityType(int utilityTypeId) throws SQLException {
        String sql = "DELETE FROM UtilityReadings WHERE UtilityTypeID = ? AND OldReading = 0 AND NewReading = 0";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utilityTypeId);
            ps.executeUpdate();
        }
    }

}
