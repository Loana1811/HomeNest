/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBContext;
import Model.Room;
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
}
