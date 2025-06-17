/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBContext;
import Model.Room;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class RoomDAO extends DBContext {

    public ArrayList<Room> getAvailableRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        String squery = "SELECT * FROM Rooms WHERE RoomID NOT IN (SELECT RoomID FROM Contracts)";
        try ( ResultSet rs = this.execSelectQuery(squery)) {
            while (rs.next()) {
                Room room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setRoomType(rs.getString("RoomType"));
                room.setRentPrice(rs.getDouble("RentPrice"));
                room.setArea(rs.getDouble("Area"));
                room.setLocation(rs.getString("Location"));
                room.setStatus(rs.getString("Status"));
                room.setBlockID(rs.getInt("BlockID"));
                room.setCategoryID(rs.getInt("CategoryID"));
                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public ArrayList<Room> getAvailableRoomsIncludingCurrent(int currentRoomId) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM Rooms WHERE RoomID NOT IN (SELECT RoomID FROM Contracts) OR RoomID = ?";

        try ( PreparedStatement ps = getConnection().prepareStatement(query)) {
            ps.setInt(1, currentRoomId);
//            ps.setInt(2, currentRoomId);  // Gán cả hai tham số
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
                room.setBlockID(rs.getInt("BlockID"));
                room.setCategoryID(rs.getInt("CategoryID"));
                System.out.println("ROOM: " + room.getRoomID() + " - " + room.getRoomNumber());

                rooms.add(room);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Room getRoomById(int roomId) {
        Room room = null;
        String squery = "SELECT * FROM Rooms WHERE RoomID = ?";

        try ( PreparedStatement ps = conn.prepareStatement(squery)) {
            ps.setInt(1, roomId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                room = new Room();
                room.setRoomID(rs.getInt("RoomID"));
                room.setRoomNumber(rs.getString("RoomNumber"));
                room.setRoomType(rs.getString("RoomType"));
                room.setRentPrice(rs.getDouble("RentPrice"));
                room.setArea(rs.getDouble("Area"));
                room.setLocation(rs.getString("Location"));
                room.setStatus(rs.getString("Status"));
                room.setBlockID(rs.getInt("BlockID"));
                room.setCategoryID(rs.getInt("CategoryID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return room;
    }

}
