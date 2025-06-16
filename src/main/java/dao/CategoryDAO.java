/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Admin
 */
import model.Category;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends DBContext {

    public List<Category> getAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM Categories";

        try ( Connection c = new DBContext().getConnection();  PreparedStatement ps = c.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName")
                );
                list.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
