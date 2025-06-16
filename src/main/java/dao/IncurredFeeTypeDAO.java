package dao;

import model.IncurredFeeType;
import utils.DBContext;
import java.sql.*;
import java.util.*;

public class IncurredFeeTypeDAO {

    private final DBContext dbContext = new DBContext();

    public List<IncurredFeeType> getAllIncurredFeeTypes() throws SQLException {
        List<IncurredFeeType> list = new ArrayList<>();
        String sql = "SELECT * FROM IncurredFeeTypes";
        try ( Connection c = dbContext.getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public IncurredFeeType getIncurredFeeTypeById(int id) throws SQLException {
        String sql = "SELECT * FROM IncurredFeeTypes WHERE IncurredFeeTypeID=?";
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

    public boolean insertIncurredFeeType(IncurredFeeType b) throws SQLException {
        String sql = "INSERT INTO IncurredFeeTypes (FeeName, DefaultAmount) VALUES (?, ?)";
        return dbContext.execUpdateQuery(sql, b.getFeeName(), b.getDefaultAmount()) > 0;
    }

    public boolean updateIncurredFeeType(IncurredFeeType b) throws SQLException {
        String sql = "UPDATE IncurredFeeTypes SET FeeName=?, DefaultAmount=? WHERE IncurredFeeTypeID=?";
        return dbContext.execUpdateQuery(sql, b.getFeeName(), b.getDefaultAmount(), b.getIncurredFeeTypeID()) > 0;
    }

    public boolean deleteIncurredFeeType(int id) throws SQLException {
        String sql = "DELETE FROM IncurredFeeTypes WHERE IncurredFeeTypeID=?";
        return dbContext.execUpdateQuery(sql, id) > 0;
    }

    private IncurredFeeType map(ResultSet rs) throws SQLException {
        IncurredFeeType b = new IncurredFeeType();
        b.setIncurredFeeTypeID(rs.getInt("IncurredFeeTypeID"));
        b.setFeeName(rs.getString("FeeName"));
        b.setDefaultAmount(rs.getDouble("DefaultAmount"));
        return b;
    }
}
