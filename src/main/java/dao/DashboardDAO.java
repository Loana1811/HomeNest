/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DBContext;

/**
 *
 * @author kloane
 */
public class DashboardDAO {

    public int countRooms() throws Exception {
        String sql = "SELECT COUNT(*) FROM Rooms";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int countTenants() throws Exception {
        String sql = "SELECT COUNT(*) FROM Tenants";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int countUnpaidBills() throws Exception {
        String sql = "SELECT COUNT(*) FROM Bills WHERE Status='Unpaid'";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    
    public int countReadingsLast30Days() throws Exception {
        String sql = "SELECT COUNT(*) FROM UtilityReadings WHERE ReadingDate >= DATEADD(day, -30, GETDATE())";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    
    public java.util.List<Object[]> getDailyElectricityUsageLastWeek() throws Exception {
        java.util.List<Object[]> list = new java.util.ArrayList<>();
        String sql
                = "SELECT CONVERT(varchar(10), ReadingDate, 120) as day, SUM(NewReading-OldReading) as used "
                + "FROM UtilityReadings ur JOIN UtilityTypes ut ON ur.UtilityTypeID=ut.UtilityTypeID "
                + "WHERE ut.UtilityName='Electricity' AND ReadingDate >= DATEADD(day,-6,CAST(GETDATE() as date)) "
                + "GROUP BY CONVERT(varchar(10), ReadingDate, 120) "
                + "ORDER BY day";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{rs.getString("day"), rs.getDouble("used")});
            }
        }
        return list;
    }

}
