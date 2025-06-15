package dao;

import model.Room;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

public class RoomDAO {

    public List<Room> getAllRoomsWithCategory() {
        List<Room> list = new ArrayList<>();

        String sql = "SELECT r.RoomID, r.RoomNumber, r.RoomType, r.Area, r.Location, r.Status, r.RentPrice, "
                + "r.ImageName, r.PostedDate, r.BlockID, r.Description, c.CategoryName "
                + "FROM Rooms r LEFT JOIN Categories c ON r.CategoryID = c.CategoryID";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Room room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setRoomType(rs.getString("RoomType"));
                room.setRentPrice(rs.getDouble("RentPrice"));
                room.setArea(rs.getDouble("Area"));
                room.setLocation(rs.getString("Location"));
                room.setStatus(rs.getString("Status"));
                room.setCategoryName(rs.getString("CategoryName"));
                room.setImageName(rs.getString("ImageName"));
                room.setPostedDate(new Date(rs.getTimestamp("PostedDate").getTime()));
                room.setBlockID(rs.getInt("BlockID"));
                room.setBlockName(mapBlockIDToName(rs.getInt("BlockID")));
                room.setDescription(rs.getString("Description")); // 👈 THÊM DÒNG NÀY

                list.add(room);
            }

            System.out.println("Total rooms retrieved: " + list.size());

        } catch (Exception e) {
            System.err.println("Error while retrieving room list:");
            e.printStackTrace();
        }

        return list;
    }

    public List<Room> filterRooms(String status, String type, Double minPrice, Double maxPrice, Double minArea, Double maxArea, Integer blockID) {
        List<Room> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT r.RoomID, r.RoomNumber, r.RoomType, r.RentPrice, r.Area, r.Location, r.Status, "
                + "r.BlockID, r.Description, c.CategoryName, r.ImageName, r.PostedDate "
                + "FROM Rooms r LEFT JOIN Categories c ON r.CategoryID = c.CategoryID WHERE 1=1"
        );

        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND r.Status = ?");
        }
        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND r.RoomType = ?");
        }
        if (minPrice != null) {
            sql.append(" AND r.RentPrice >= ?");
        }
        if (maxPrice != null) {
            sql.append(" AND r.RentPrice <= ?");
        }
        if (minArea != null) {
            sql.append(" AND r.Area >= ?");
        }
        if (maxArea != null) {
            sql.append(" AND r.Area <= ?");
        }
        if (blockID != null) {
            sql.append(" AND r.BlockID = ?");
        }

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (status != null && !status.trim().isEmpty()) {
                ps.setString(index++, status);
            }
            if (type != null && !type.trim().isEmpty()) {
                ps.setString(index++, type);
            }
            if (minPrice != null) {
                ps.setDouble(index++, minPrice);
            }
            if (maxPrice != null) {
                ps.setDouble(index++, maxPrice);
            }
            if (minArea != null) {
                ps.setDouble(index++, minArea);
            }
            if (maxArea != null) {
                ps.setDouble(index++, maxArea);
            }
            if (blockID != null) {
                ps.setInt(index++, blockID);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setRoomType(rs.getString("RoomType"));
                room.setRentPrice(rs.getDouble("RentPrice"));
                room.setArea(rs.getDouble("Area"));
                room.setLocation(rs.getString("Location"));
                room.setStatus(rs.getString("Status"));
                room.setCategoryName(rs.getString("CategoryName"));
                room.setImageName(rs.getString("ImageName"));
                room.setPostedDate(new Date(rs.getTimestamp("PostedDate").getTime()));
                room.setBlockID(rs.getInt("BlockID"));
                room.setBlockName(mapBlockIDToName(rs.getInt("BlockID")));
                room.setDescription(rs.getString("Description")); // 👈 THÊM DÒNG NÀY

                list.add(room);
            }

            System.out.println("Total rooms filtered: " + list.size());

        } catch (Exception e) {
            System.err.println("Error while filtering room list:");
            e.printStackTrace();
        }

        return list;
    }

    private Connection conn;

    public RoomDAO(Connection conn) {
        this.conn = conn;
    }

    public Room getRoomById(int id) {
        String sql = "SELECT r.RoomID, r.RoomNumber, r.RoomType, r.Area, r.Location, r.Status, r.RentPrice, "
                + "r.ImageName, r.PostedDate, r.BlockID, r.Description, c.CategoryName "
                + "FROM Rooms r LEFT JOIN Categories c ON r.CategoryID = c.CategoryID WHERE r.RoomID = ?";
        try ( PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setRoomID(rs.getInt("RoomID"));
                    room.setRoomNumber(rs.getString("RoomNumber"));
                    room.setRoomType(rs.getString("RoomType"));
                    room.setArea(rs.getDouble("Area"));
                    room.setLocation(rs.getString("Location"));
                    room.setStatus(rs.getString("Status"));
                    room.setRentPrice(rs.getDouble("RentPrice"));
                    room.setImageName(rs.getString("ImageName"));
                    room.setCategoryName(rs.getString("CategoryName"));
                    room.setPostedDate(new Date(rs.getTimestamp("PostedDate").getTime()));
                    room.setBlockID(rs.getInt("BlockID"));
                    room.setBlockName(mapBlockIDToName(rs.getInt("BlockID")));
                    room.setDescription(rs.getString("Description")); // 👈 THÊM DÒNG NÀY
                    return room;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Room> getFeaturedRooms(int limit) {
        List<Room> list = new ArrayList<>();
        String sql = "SELECT TOP(?) RoomID, RoomNumber, RoomType, RentPrice, PostedDate, ImageName FROM Rooms ORDER BY PostedDate DESC";

        try ( Connection conn = DBUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setRoomType(rs.getString("RoomType"));
                room.setRentPrice(rs.getDouble("RentPrice"));
                room.setPostedDate(rs.getDate("PostedDate"));
                room.setImageName(rs.getString("ImageName"));
                list.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    // Lấy danh sách phòng có phân trang

   public List<Room> roomsPaginated(String status, String type,
                                 Double minPrice, Double maxPrice,
                                 Double minArea, Double maxArea,
                                 Integer blockID, int offset, int pageSize) {

    List<Room> list = new ArrayList<>();
    StringBuilder sql = new StringBuilder(
        "SELECT r.RoomID, r.RoomNumber, r.RoomType, r.RentPrice, r.Area, r.Location, r.Status, " +
        "r.BlockID, r.Description, c.CategoryName, r.ImageName, r.PostedDate " +
        "FROM Rooms r LEFT JOIN Categories c ON r.CategoryID = c.CategoryID WHERE 1=1"
    );

    if (status != null && !status.trim().isEmpty()) {
        sql.append(" AND r.Status = ?");
    }
    if (type != null && !type.trim().isEmpty()) {
        sql.append(" AND r.RoomType = ?");
    }
    if (minPrice != null) {
        sql.append(" AND r.RentPrice >= ?");
    }
    if (maxPrice != null) {
        sql.append(" AND r.RentPrice <= ?");
    }
    if (minArea != null) {
        sql.append(" AND r.Area >= ?");
    }
    if (maxArea != null) {
        sql.append(" AND r.Area <= ?");
    }
    if (blockID != null) {
        sql.append(" AND r.BlockID = ?");
    }

    sql.append(" ORDER BY r.RoomID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int index = 1;
        if (status != null && !status.trim().isEmpty()) ps.setString(index++, status);
        if (type != null && !type.trim().isEmpty()) ps.setString(index++, type);
        if (minPrice != null) ps.setDouble(index++, minPrice);
        if (maxPrice != null) ps.setDouble(index++, maxPrice);
        if (minArea != null) ps.setDouble(index++, minArea);
        if (maxArea != null) ps.setDouble(index++, maxArea);
        if (blockID != null) ps.setInt(index++, blockID);

        ps.setInt(index++, offset);
        ps.setInt(index++, pageSize);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Room room = new Room();
            room.setRoomID(rs.getInt("RoomID"));
            room.setRoomNumber(rs.getString("RoomNumber"));
            room.setRoomType(rs.getString("RoomType"));
            room.setRentPrice(rs.getDouble("RentPrice"));
            room.setArea(rs.getDouble("Area"));
            room.setLocation(rs.getString("Location"));
            room.setStatus(rs.getString("Status"));
            room.setCategoryName(rs.getString("CategoryName"));
            room.setImageName(rs.getString("ImageName"));
            room.setPostedDate(new Date(rs.getTimestamp("PostedDate").getTime()));
            room.setBlockID(rs.getInt("BlockID"));
            room.setBlockName(mapBlockIDToName(rs.getInt("BlockID")));
            room.setDescription(rs.getString("Description"));

            list.add(room);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}


// Lấy tổng số phòng (để tính tổng số trang)
 public int totalRoomCount(String status, String type,
                          Double minPrice, Double maxPrice,
                          Double minArea, Double maxArea,
                          Integer blockID) {

    StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Rooms WHERE 1=1");

    if (status != null && !status.trim().isEmpty()) {
        sql.append(" AND Status = ?");
    }
    if (type != null && !type.trim().isEmpty()) {
        sql.append(" AND RoomType = ?");
    }
    if (minPrice != null) {
        sql.append(" AND RentPrice >= ?");
    }
    if (maxPrice != null) {
        sql.append(" AND RentPrice <= ?");
    }
    if (minArea != null) {
        sql.append(" AND Area >= ?");
    }
    if (maxArea != null) {
        sql.append(" AND Area <= ?");
    }
    if (blockID != null) {
        sql.append(" AND BlockID = ?");
    }

    try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
        int index = 1;
        if (status != null && !status.trim().isEmpty()) ps.setString(index++, status);
        if (type != null && !type.trim().isEmpty()) ps.setString(index++, type);
        if (minPrice != null) ps.setDouble(index++, minPrice);
        if (maxPrice != null) ps.setDouble(index++, maxPrice);
        if (minArea != null) ps.setDouble(index++, minArea);
        if (maxArea != null) ps.setDouble(index++, maxArea);
        if (blockID != null) ps.setInt(index++, blockID);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return 0;
}


    private String mapBlockIDToName(int id) {
        switch (id) {
            case 1:
                return "Khu A";
            case 2:
                return "Khu B";
            case 3:
                return "Khu C";
            case 4:
                return "Khu D";
            default:
                return "Không rõ";
        }
    }

}
