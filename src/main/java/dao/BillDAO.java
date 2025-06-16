package dao;

import model.Bill;
import utils.DBContext;
import java.sql.*;
import java.util.*;

public class BillDAO {
    private final DBContext dbContext = new DBContext();

    public List<Bill> getAllBills() throws SQLException {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM Bills";
        try(Connection c = dbContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while(rs.next()) list.add(map(rs));
        }
        return list;
    }

    public Bill getBillById(int id) throws SQLException {
        String sql = "SELECT * FROM Bills WHERE BillID=?";
        try(Connection c = dbContext.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return map(rs);
            }
        }
        return null;
    }

    public boolean insertBill(Bill b) throws SQLException {
        String sql = "INSERT INTO Bills (ContractID, BillDate, TotalAmount, Status) VALUES (?, ?, ?, ?)";
        return dbContext.execUpdateQuery(sql, b.getContractID(), b.getBillDate(), b.getTotalAmount(), b.getStatus()) > 0;
    }

    public boolean updateBill(Bill b) throws SQLException {
        String sql = "UPDATE Bills SET ContractID=?, BillDate=?, TotalAmount=?, Status=? WHERE BillID=?";
        return dbContext.execUpdateQuery(sql, b.getContractID(), b.getBillDate(), b.getTotalAmount(), b.getStatus(), b.getBillID()) > 0;
    }

    public boolean deleteBill(int id) throws SQLException {
        String sql = "DELETE FROM Bills WHERE BillID=?";
        return dbContext.execUpdateQuery(sql, id) > 0;
    }

    private Bill map(ResultSet rs) throws SQLException {
        Bill b = new Bill();
        b.setBillID(rs.getInt("BillID"));
        b.setContractID(rs.getInt("ContractID"));
        b.setBillDate(rs.getDate("BillDate"));
        b.setTotalAmount(rs.getDouble("TotalAmount"));
        b.setStatus(rs.getString("Status"));
        return b;
    }
}
