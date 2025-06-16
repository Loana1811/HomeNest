/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kloane
 */
public class DBContext {
    // Public database connection object

    private Connection conn;

    // Update these constants based on your SQL Server config
    private final String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=RentalManagement;encrypt=false";
    private final String DB_USER = "sa";
    // private final String DB_PWD = "18112004";
    private final String DB_PWD = "123456";

    public DBContext() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
            System.out.println("✅ [DBContext] Connected to SQL Server successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ [DBContext] JDBC Driver not found.");
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            System.err.println("❌ [DBContext] Failed to connect to database: " + e.getMessage());
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            System.out.println("🔄 [DBContext] Connection was null or closed. Reconnecting...");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
        }
        return conn;
    }

    public int execQuery(String query, Object[] params) throws SQLException {
        try ( PreparedStatement pStatement = getConnection().prepareStatement(query)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pStatement.setObject(i + 1, params[i]);
                }
            }
            return pStatement.executeUpdate();
        }
    }

    public ResultSet execSelectQuery(String query, Object[] params) throws SQLException {
        PreparedStatement pStatement = getConnection().prepareStatement(query);
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pStatement.setObject(i + 1, params[i]);
            }
        }
        return pStatement.executeQuery();
    }

    public ResultSet execSelectQuery(String query) throws SQLException {
        return this.execSelectQuery(query, null);
    }

    public int execUpdateQuery(String query, Object... params) throws SQLException {
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }

            return ps.executeUpdate();
        }
    }
}
