package dao;

import model.IncurredFee;
import utils.DBContext;
import java.sql.*;
import java.util.*;

public class IncurredFeeDAO {
    private final DBContext dbContext = new DBContext();

    public List<IncurredFee> getAllIncurredFees() throws SQLException {
        List<IncurredFee> list = new ArrayList<>();
        String sql = "SELECT * FROM IncurredFees";
        try(Connection c = dbContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    public IncurredFee getIncurredFeeById(int id) throws SQLException {
        String sql = "SELECT * FROM IncurredFees WHERE IncurredFeeID=?";
        try(Connection c = dbContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return map(rs);
            }
        }
        return null;
    }

    public boolean insertIncurredFee(IncurredFee b) throws SQLException {
        String sql = "INSERT INTO IncurredFees (BillID, IncurredFeeTypeID, Amount) VALUES (?, ?, ?)";
        return dbContext.execUpdateQuery(sql, b.getBillID(), b.getIncurredFeeTypeID(), b.getAmount()) > 0;
    }

    public boolean updateIncurredFee(IncurredFee b) throws SQLException {
        String sql = "UPDATE IncurredFees SET BillID=?, IncurredFeeTypeID=?, Amount=? WHERE IncurredFeeID=?";
        return dbContext.execUpdateQuery(sql, b.getBillID(), b.getIncurredFeeTypeID(), b.getAmount(), b.getIncurredFeeID()) > 0;
    }

    public boolean deleteIncurredFee(int id) throws SQLException {
        String sql = "DELETE FROM IncurredFees WHERE IncurredFeeID=?";
        return dbContext.execUpdateQuery(sql, id) > 0;
    }

    private IncurredFee map(ResultSet rs) throws SQLException {
        IncurredFee b = new IncurredFee();
        b.setIncurredFeeID(rs.getInt("IncurredFeeID"));
        b.setBillID(rs.getInt("BillID"));
        b.setIncurredFeeTypeID(rs.getInt("IncurredFeeTypeID"));
        b.setAmount(rs.getDouble("Amount"));
        return b;
    }
}
