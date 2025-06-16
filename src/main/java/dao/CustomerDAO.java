package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.Customer;
import utils.DBContext;
import java.sql.*;
import java.util.*;

public class CustomerDAO {

    private final DBContext dbContext;

    public CustomerDAO() {
        this.dbContext = new DBContext();
    }

    // Map một dòng ResultSet thành Customer object
    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setCustomerID(rs.getInt("CustomerID"));
        customer.setFullName(rs.getString("FullName"));
        customer.setPhoneNumber(rs.getString("PhoneNumber"));
        customer.setCccd(rs.getString("CCCD"));
        customer.setGender(rs.getString("Gender"));
        customer.setBirthDate(rs.getDate("BirthDate"));
        customer.setAddress(rs.getString("Address"));
        customer.setEmail(rs.getString("Email"));
        customer.setStatus(rs.getString("Status"));
        customer.setCustomerPassword(rs.getString("CustomerPassword"));
        return customer;
    }

    // Lấy toàn bộ customers
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM Customers";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                customers.add(mapResultSetToCustomer(rs));
            }
        }
        return customers;
    }

    // Thêm customer mới
    public boolean createCustomer(Customer customer) throws SQLException {
        String query = "INSERT INTO Customers (FullName, PhoneNumber, CCCD, Gender, BirthDate, Address, Email, Status, CustomerPassword) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int result = dbContext.execUpdateQuery(query,
                customer.getFullName(),
                customer.getPhoneNumber(),
                customer.getCccd(),
                customer.getGender(),
                customer.getBirthDate(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getStatus() != null ? customer.getStatus() : "Potential",
                customer.getCustomerPassword()
        );
        return result > 0;
    }

    // Lấy customer theo ID
    public Customer getCustomerById(int customerId) throws SQLException {
        String query = "SELECT * FROM Customers WHERE CustomerID = ? ";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customerId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCustomer(rs);
                }
            }
        }
        return null;
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE Customers SET FullName = ?, PhoneNumber = ?, CCCD = ?, Gender = ?, BirthDate = ?, Address = ?, Email = ?, Status = ? WHERE CustomerID = ?";

        System.out.println("DEBUG - Updating customer ID: " + customer.getCustomerID());
        System.out.println("DEBUG - New status: " + customer.getStatus());

        try {
            int result = dbContext.execUpdateQuery(query,
                    customer.getFullName(),
                    customer.getPhoneNumber(),
                    customer.getCccd(),
                    customer.getGender(),
                    customer.getBirthDate(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getStatus(),
                    customer.getCustomerID()
            );

            System.out.println("DEBUG - Customer update result: " + result);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("DEBUG - SQL Exception in updateCustomer: " + e.getMessage());
            throw e;
        }
    }

// Thêm method updateCustomerStatus riêng cho việc disable
    public boolean updateCustomerStatus(int customerID, String status) throws SQLException {
        String query = "UPDATE Customers SET Status = ? WHERE CustomerID = ?";

        System.out.println("DEBUG - Updating customer status - ID: " + customerID + ", Status: " + status);

        try {
            int result = dbContext.execUpdateQuery(query, status, customerID);
            System.out.println("DEBUG - Status update result: " + result);
            return result > 0;
        } catch (SQLException e) {
            System.out.println("DEBUG - SQL Exception in updateCustomerStatus: " + e.getMessage());
            throw e;
        }
    }

    // Xoá customer
    public boolean deleteCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM Customers WHERE CustomerID = ?";
        int result = dbContext.execUpdateQuery(query, customerId);
        return result > 0;
    }

    // Kiểm tra số điện thoại đã tồn tại chưa
    public boolean isPhoneNumberExists(String phoneNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers WHERE PhoneNumber = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, phoneNumber);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // Đếm tổng số customer
    public int getTotalCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Hash password với MD5
    public String hashMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Kiểm tra customer có hợp đồng hoặc là tenant không
    public boolean hasContractOrTenant(int customerId) throws SQLException {
        // Kiểm tra bảng Tenants
        String sql1 = "SELECT 1 FROM Tenants WHERE CustomerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql1)) {
            ps.setInt(1, customerId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        // Kiểm tra bảng Contracts
        String sql2 = "SELECT 1 FROM Contracts c JOIN Tenants t ON c.TenantID = t.TenantID WHERE t.CustomerID = ?";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql2)) {
            ps.setInt(1, customerId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        }
        return false;
    }

    // Lấy toàn bộ thông tin chi tiết customer (dùng cho viewDetails)
    public List<Map<String, Object>> getFullCustomerDetails(int customerID) throws SQLException {
        String sql
                = "SELECT c.CustomerID, c.FullName AS CustomerName, c.PhoneNumber, c.Email, c.Status AS CustomerStatus, "
                + "t.TenantID, t.JoinDate, "
                + "ct.ContractID, ct.RoomID, ct.StartDate, ct.EndDate, ct.Status AS ContractStatus, ct.CreatedAt AS ContractCreatedAt, "
                + "r.RoomNumber, r.RoomType, r.Location, r.Status AS RoomStatus, "
                + "rq.RequestID, rq.RequestDate, rq.Status AS RequestStatus, "
                + "b.BillID, b.BillDate, b.TotalAmount, b.Status AS BillStatus, "
                + "bd.BillDetailID, bd.RoomRent, bd.ElectricityCost, bd.WaterCost, bd.WifiCost, "
                + "ir.IncurredFeeID, ift.FeeName, ir.Amount, "
                + "ur.ReadingID, ut.UtilityName, ur.ReadingDate, ur.OldReading, ur.NewReading, ur.PriceUsed "
                + "FROM Customers c "
                + "LEFT JOIN Tenants t ON c.CustomerID = t.CustomerID "
                + "LEFT JOIN Contracts ct ON t.TenantID = ct.TenantID "
                + "LEFT JOIN Rooms r ON ct.RoomID = r.RoomID "
                + "LEFT JOIN Bills b ON ct.ContractID = b.ContractID "
                + "LEFT JOIN BillDetails bd ON b.BillID = bd.BillID "
                + "LEFT JOIN IncurredFees ir ON b.BillID = ir.BillID "
                + "LEFT JOIN IncurredFeeTypes ift ON ir.IncurredFeeTypeID = ift.IncurredFeeTypeID "
                + "LEFT JOIN RentalRequests rq ON c.CustomerID = rq.CustomerID "
                + "LEFT JOIN UtilityReadings ur ON r.RoomID = ur.RoomID "
                + "LEFT JOIN UtilityTypes ut ON ur.UtilityTypeID = ut.UtilityTypeID "
                + "WHERE c.CustomerID = ? "
                + "ORDER BY ct.ContractID DESC, b.BillDate DESC";

        List<Map<String, Object>> list = new ArrayList<>();
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try ( ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData meta = rs.getMetaData();
                int colCount = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= colCount; i++) {
                        row.put(meta.getColumnLabel(i), rs.getObject(i));
                    }
                    list.add(row);
                }
            }
        }
        return list;
    }

// Số customer status = Converted
    public int getConvertedCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers WHERE Status = 'Converted'";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

// Số customer status = Potential
    public int getPotentialCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers WHERE Status = 'Potential'";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

// Số customer status = Inactive
    public int getInactiveCustomerCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Customers WHERE Status = 'Inactive'";
        try ( Connection conn = dbContext.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

}
