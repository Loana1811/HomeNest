/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Admin
 */
import model.Block;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlockDAO extends DBContext {

    public List<Block> getAllBlocks() {
        List<Block> list = new ArrayList<>();
        String sql = "SELECT * FROM Blocks";

        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Block block = new Block(
                        rs.getInt("BlockID"),
                        rs.getString("BlockName"),
                        rs.getInt("MaxRooms"),
                        rs.getInt("RoomCount"),
                        rs.getString("Status")
                );
                list.add(block);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insert(Block block) {
        String sql = "INSERT INTO Blocks (BlockName, MaxRooms, RoomCount, Status) VALUES (?, ?, 0, 'Available')";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            ps.setString(1, block.getBlockName());
            ps.setInt(2, block.getMaxRooms());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLatestBlockId() {
        String sql = "SELECT TOP 1 BlockID FROM Blocks ORDER BY BlockID DESC";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
//
//    public boolean deleteBlock(int blockID) {
//        String checkSql = "SELECT COUNT(*) FROM Rooms WHERE BlockID = ?";
//        String deleteSql = "DELETE FROM Blocks WHERE BlockID = ?";
//        try ( PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
//            checkPs.setInt(1, blockID);
//            ResultSet rs = checkPs.executeQuery();
//            if (rs.next() && rs.getInt(1) == 0) {
//                try ( PreparedStatement deletePs = conn.prepareStatement(deleteSql)) {
//                    deletePs.setInt(1, blockID);
//                    deletePs.executeUpdate();
//                    return true;
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    public void updateBlock(Block block) {
        String sql = "UPDATE Blocks SET blockName = ?, maxRooms = ? WHERE blockID = ?";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            ps.setString(1, block.getBlockName());
            ps.setInt(2, block.getMaxRooms());
            ps.setInt(3, block.getBlockID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Block getBlockById(int id) {
        String sql = "SELECT * FROM Blocks WHERE BlockID = ?";
        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Block(
                            rs.getInt("BlockID"),
                            rs.getString("BlockName"),
                            rs.getInt("MaxRooms"),
                            rs.getInt("RoomCount"),
                            rs.getString("Status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
