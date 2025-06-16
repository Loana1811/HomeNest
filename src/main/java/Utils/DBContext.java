/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    private static final String JDBC_URL
            = "jdbc:sqlserver://localhost:1433;databaseName=RentalManagement;encrypt=false;trustServerCertificate=true";
    private static final String JDBC_USER = "sa";
    private static final String JDBC_PASSWORD = "778824";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("SQLServer JDBC Driver not found!");
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found", e);
        }

        try {
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database.");
            e.printStackTrace();
            throw e; // re-throw để servlet catch được
        }
    }
}
