package dao;

import model.UtilityReading;
import utils.DBContext;
import java.sql.*;
import java.util.*;

public class UtilityReadingDAO {
    private final DBContext dbContext = new DBContext();

    public List<UtilityReading> getAllUtilityReadings() throws SQLException {
        List<UtilityReading> list = new ArrayList<>();
        String sql = "SELECT * FROM UtilityReadings";
        try(Connection c = dbContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    public UtilityReading getUtilityReadingById(int id) throws SQLException {
        String sql = "SELECT * FROM UtilityReadings WHERE ReadingID=?";
        try(Connection c = dbContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return map(rs);
            }
        }
        return null;
    }

    public boolean insertUtilityReading(UtilityReading b) throws SQLException {
        String sql = "INSERT INTO UtilityReadings (UtilityTypeID, RoomID, ReadingDate, OldReading, NewReading, PriceUsed, OldPrice, ChangedBy) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return dbContext.execUpdateQuery(sql, b.getUtilityTypeID(), b.getRoomID(), b.getReadingDate(), b.getOldReading(), b.getNewReading(), b.getPriceUsed(), b.getOldPrice(), b.getChangedBy()) > 0;
    }

    public boolean updateUtilityReading(UtilityReading b) throws SQLException {
        String sql = "UPDATE UtilityReadings SET UtilityTypeID=?, RoomID=?, ReadingDate=?, OldReading=?, NewReading=?, PriceUsed=?, OldPrice=?, ChangedBy=? WHERE ReadingID=?";
        return dbContext.execUpdateQuery(sql, b.getUtilityTypeID(), b.getRoomID(), b.getReadingDate(), b.getOldReading(), b.getNewReading(), b.getPriceUsed(), b.getOldPrice(), b.getChangedBy(), b.getReadingID()) > 0;
    }

    public boolean deleteUtilityReading(int id) throws SQLException {
        String sql = "DELETE FROM UtilityReadings WHERE ReadingID=?";
        return dbContext.execUpdateQuery(sql, id) > 0;
    }

    private UtilityReading map(ResultSet rs) throws SQLException {
        UtilityReading b = new UtilityReading();
        b.setReadingID(rs.getInt("ReadingID"));
        b.setUtilityTypeID(rs.getInt("UtilityTypeID"));
        b.setRoomID(rs.getInt("RoomID"));
        b.setReadingDate(rs.getDate("ReadingDate"));
        b.setOldReading(rs.getDouble("OldReading"));
        b.setNewReading(rs.getDouble("NewReading"));
        b.setPriceUsed(rs.getDouble("PriceUsed"));
        b.setOldPrice(rs.getDouble("OldPrice"));
        b.setChangedBy(rs.getString("ChangedBy"));
        return b;
    }
}
