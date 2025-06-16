package dao;

import model.RentalRequest;
import utils.DBContext;
import java.sql.*;
import java.util.*;

public class RentalRequestDAO {

    private final DBContext dbContext = new DBContext();

    public List<RentalRequest> getAllRentalRequests() throws SQLException {
        List<RentalRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM RentalRequests";
        try ( Connection c = dbContext.getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public RentalRequest getRentalRequestById(int id) throws SQLException {
        String sql = "SELECT * FROM RentalRequests WHERE RequestID=?";
        try ( Connection c = dbContext.getConnection();  PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public boolean insertRentalRequest(RentalRequest b) throws SQLException {
        String sql = "INSERT INTO RentalRequests (CustomerID, RoomID, RequestDate, Status, ApprovedBy, ApprovedDate) VALUES (?, ?, ?, ?, ?, ?)";
        return dbContext.execUpdateQuery(sql, b.getCustomerID(), b.getRoomID(), b.getRequestDate(), b.getStatus(), b.getApprovedBy(), b.getApprovedDate()) > 0;
    }

    public boolean updateRentalRequest(RentalRequest b) throws SQLException {
        String sql = "UPDATE RentalRequests SET CustomerID=?, RoomID=?, RequestDate=?, Status=?, ApprovedBy=?, ApprovedDate=? WHERE RequestID=?";
        return dbContext.execUpdateQuery(sql, b.getCustomerID(), b.getRoomID(), b.getRequestDate(), b.getStatus(), b.getApprovedBy(), b.getApprovedDate(), b.getRequestID()) > 0;
    }

    public boolean deleteRentalRequest(int id) throws SQLException {
        String sql = "DELETE FROM RentalRequests WHERE RequestID=?";
        return dbContext.execUpdateQuery(sql, id) > 0;
    }

    private RentalRequest map(ResultSet rs) throws SQLException {
        RentalRequest b = new RentalRequest();
        b.setRequestID(rs.getInt("RequestID"));
        b.setCustomerID(rs.getInt("CustomerID"));
        b.setRoomID(rs.getInt("RoomID"));
        b.setRequestDate(rs.getDate("RequestDate"));
        b.setStatus(rs.getString("Status"));
        b.setApprovedBy(rs.getObject("ApprovedBy") != null ? rs.getInt("ApprovedBy") : null);
        b.setApprovedDate(rs.getDate("ApprovedDate"));
        return b;
    }
}
