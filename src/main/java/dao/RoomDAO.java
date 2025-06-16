/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.Room;
import utils.DBContext;

/**
 *
 * @author kloane
 */
public class RoomDAO {

    public List<Room> getAll() {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT * FROM Rooms";

        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Double area = rs.getBigDecimal("Area") != null ? rs.getBigDecimal("Area").doubleValue() : null;
                Room room = new Room(
                        rs.getInt("RoomID"),
                        rs.getString("RoomNumber"),
                        rs.getBigDecimal("RentPrice").doubleValue(),
                        area,
                        rs.getString("Status"),
                        (Integer) rs.getObject("BlockID"),
                        (Integer) rs.getObject("CategoryID"),
                        rs.getString("Highlights"),
                        rs.getString("ImagePath"),
                        rs.getString("Description"),
                        rs.getTimestamp("PostedDate")
                );
                list.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Room getById(int id) {
        String sql = "SELECT * FROM Rooms WHERE RoomID = ?";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Double area = rs.getBigDecimal("Area") != null ? rs.getBigDecimal("Area").doubleValue() : null;
                    return new Room(
                            rs.getInt("RoomID"),
                            rs.getString("RoomNumber"),
                            rs.getBigDecimal("RentPrice").doubleValue(),
                            area,
                            rs.getString("Status"),
                            (Integer) rs.getObject("BlockID"),
                            (Integer) rs.getObject("CategoryID"),
                            rs.getString("Highlights"),
                            rs.getString("ImagePath"),
                            rs.getString("Description"),
                            rs.getTimestamp("PostedDate")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insert(Room room) {
        String sql = "INSERT INTO Rooms (RoomNumber, RentPrice, Area, Status, BlockID, CategoryID, Highlights, ImagePath, Description, PostedDate) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setString(1, room.getRoomNumber());
            ps.setDouble(2, room.getRentPrice());
            ps.setObject(3, room.getArea());
            ps.setString(4, room.getStatus());
            ps.setObject(5, room.getBlockID());
            ps.setObject(6, room.getCategoryID());
            ps.setString(7, room.getHighlights());
            ps.setString(8, room.getImagePath());
            ps.setString(9, room.getDescription());
            ps.setTimestamp(10, room.getPostedDate());

            ps.executeUpdate();

            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    room.setRoomID(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Room room) {
        String sql = "UPDATE Rooms SET RoomNumber = ?, RentPrice = ?, Area = ?, Status = ?, BlockID = ?, CategoryID = ?, "
                + "Highlights = ?, ImagePath = ?, Description = ?, PostedDate = ? WHERE RoomID = ?";

        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            ps.setString(1, room.getRoomNumber());
            ps.setDouble(2, room.getRentPrice());
            ps.setObject(3, room.getArea());
            ps.setString(4, room.getStatus());
            ps.setObject(5, room.getBlockID());
            ps.setObject(6, room.getCategoryID());
            ps.setString(7, room.getHighlights());
            ps.setString(8, room.getImagePath());
            ps.setString(9, room.getDescription());
            ps.setTimestamp(10, room.getPostedDate());
            ps.setInt(11, room.getRoomID());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM Rooms WHERE RoomID = ?";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Room> filterRoomsByCategoryAndBlock(String categoryID, String blockID) {
        List<Room> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Rooms WHERE 1=1");

        if (categoryID != null && !categoryID.isEmpty()) {
            sql.append(" AND CategoryID = ?");
        }
        if (blockID != null && !blockID.isEmpty()) {
            sql.append(" AND BlockID = ?");
        }

        try (
                 Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (categoryID != null && !categoryID.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(categoryID));
            }

            if (blockID != null && !blockID.isEmpty()) {
                ps.setInt(paramIndex++, Integer.parseInt(blockID));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room(
                        rs.getInt("RoomID"),
                        rs.getString("RoomNumber"),
                        rs.getBigDecimal("RentPrice").doubleValue(),
                        rs.getBigDecimal("Area") != null ? rs.getBigDecimal("Area").doubleValue() : null,
                        rs.getString("Status"),
                        (Integer) rs.getObject("BlockID"),
                        (Integer) rs.getObject("CategoryID"),
                        rs.getString("Highlights"),
                        rs.getString("ImagePath"),
                        rs.getString("Description"),
                        rs.getTimestamp("PostedDate")
                );
                list.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Object[]> getRoomsAppliedToUtility(int utilityTypeId) throws SQLException {
        List<Object[]> rooms = new ArrayList<>();
        String sql
                = "SELECT r.RoomID, r.RoomNumber,\n"
                + "       CASE WHEN ur.UtilityTypeID IS NOT NULL THEN 1 ELSE 0 END AS IsChecked\n"
                + "FROM Rooms r\n"
                + "LEFT JOIN UtilityReadings ur\n"
                + "  ON r.RoomID = ur.RoomID AND ur.UtilityTypeID = ?\n"
                + "GROUP BY r.RoomID, r.RoomNumber, ur.UtilityTypeID";

        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, utilityTypeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rooms.add(new Object[]{
                    rs.getInt("RoomID"),
                    rs.getString("RoomNumber"),
                    rs.getInt("IsChecked") == 1
                });
            }
        }
        return rooms;

    }

    public List<Object[]> getAllRoomIdName() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT RoomID, RoomNumber FROM Rooms";
        try ( Connection conn = new DBContext().getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Object[]{rs.getInt("RoomID"), rs.getString("RoomNumber")});
            }
        }
        return list;
    }

}
