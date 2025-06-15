/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amin
 */
public class DBContext {

     // Public database connection object
    public Connection conn;

    // Constant for database connection URL (localhost, port 1433, TourManagement_GR3 database, no encryption)
    private final String DB_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=RentalManagement;encrypt=false";
    // Constant for database username
    private final String DB_USER = "sa";
    // Constant for database password
    private final String DB_PWD = "123";

    // Constructor for the DBContext class
    public DBContext() {
        try {
            // Register the SQL Server JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Initialize the connection using the specified URL, username, and password
            this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
        } catch (ClassNotFoundException | SQLException ex) {
            // Log any exceptions that occur during connection setup
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Method to retrieve the database connection
    public Connection getConnection() throws SQLException {
        return conn; // Returns the current connection instance
    }

    // Method to execute an update/insert/delete SQL query with parameters
    public int execQuery(String query, Object[] params) throws SQLException {
        // Create a prepared statement with the provided query
        PreparedStatement pStatement = conn.prepareStatement(query);
        // If parameters are provided, set them in the prepared statement
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pStatement.setObject(i + 1, params[i]); // Set each parameter (1-based index)
            }
        }
        // Execute the query and return the number of rows affected
        return pStatement.executeUpdate();
    }

    // Method to execute a select query with parameters
    public ResultSet execSelectQuery(String query, Object[] params) throws SQLException {
        // Create a prepared statement with the provided query
        PreparedStatement pStatement = conn.prepareStatement(query);
        // If parameters are provided, set them in the prepared statement
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pStatement.setObject(i + 1, params[i]); // Set each parameter (1-based index)
            }
        }
        // Execute the query and return the result set
        return pStatement.executeQuery();
    }

    // Overloaded method to execute a select query without parameters
    public ResultSet execSelectQuery(String query) throws SQLException {
        // Call the parameterized version of execSelectQuery with null parameters
        return this.execSelectQuery(query, null);
    }

}
