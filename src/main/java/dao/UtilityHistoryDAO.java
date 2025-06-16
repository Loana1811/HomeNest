/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.UtilityHistoryView;
import utils.DBContext;

/**
 *
 * @author kloane
 */
public class UtilityHistoryDAO {

    public List<UtilityHistoryView> getHistory() throws SQLException {
        List<UtilityHistoryView> list = new ArrayList<>();
        String sql
                = "SELECT ur.UtilityTypeID, "
                + "  ISNULL(ut.UtilityName, "
                + "    CASE WHEN CHARINDEX('|', ur.ChangedBy) > 0 "
                + "         THEN SUBSTRING(ur.ChangedBy, CHARINDEX('|', ur.ChangedBy) + 1, LEN(ur.ChangedBy)) "
                + "         ELSE ur.ChangedBy END"
                + "  ) AS UtilityName, "
                + "  ur.OldPrice, ur.PriceUsed AS NewPrice, ur.CreatedAt AS ChangeAt, "
                + "  CASE WHEN CHARINDEX('|', ur.ChangedBy) > 0 "
                + "         THEN LEFT(ur.ChangedBy, CHARINDEX('|', ur.ChangedBy) - 1) "
                + "         ELSE ur.ChangedBy END AS ChangedBy "
                + "FROM UtilityReadings ur "
                + "LEFT JOIN UtilityTypes ut ON ur.UtilityTypeID = ut.UtilityTypeID "
                + "WHERE ur.OldPrice IS NOT NULL "
                + "ORDER BY ur.CreatedAt DESC";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new UtilityHistoryView(
                        rs.getInt("UtilityTypeID"),
                        rs.getString("UtilityName"),
                        rs.getDouble("OldPrice"),
                        rs.getDouble("NewPrice"),
                        rs.getString("ChangedBy"),
                        rs.getDate("ChangeAt")
                ));
            }
        }
        return list;
    }

    public void insertHistory(
            int utilityTypeId, String utilityName,
            double oldPrice, double newPrice,
            String changedBy, Date date
    ) throws SQLException {
        String sql = "INSERT INTO UtilityReadings "
                + "(UtilityTypeID, RoomID, ReadingDate, OldReading, NewReading, OldPrice, PriceUsed, ChangedBy, CreatedAt) "
                + "VALUES (?, ?, ?, 0, 0, ?, ?, ?, ?)";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utilityTypeId);
            ps.setInt(2, 1); 
            ps.setDate(3, date);
            ps.setDouble(4, oldPrice);
            ps.setDouble(5, newPrice);
            ps.setString(6, changedBy + "|" + utilityName);
            ps.setDate(7, date);
            ps.executeUpdate();
        }
    }

}
