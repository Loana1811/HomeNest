/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBContext;
import Model.Contract;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ContractDAO extends DBContext {

    public ArrayList<Contract> getAllContracts() {
        // Initialize an empty ArrayList to store the contract records
        ArrayList<Contract> contracts = new ArrayList<>();
        // SQL query to select all contracts with tenant names from the Contracts and Tenants tables
        String query = "SELECT * from Contracts";
                //

        // Use try-with-resources to automatically close database resources
        try ( Connection conn = getConnection(); // Establish database connection
                  PreparedStatement ps = conn.prepareStatement(query); // Prepare the SQL query
                  ResultSet rs = ps.executeQuery()) { // Execute the query and get the result set

            // Check if the connection is null (though this check is redundant due to try-with-resources)
            if (conn == null) {
                System.out.println("❌ Unable to connect to the database");
                return null;
            }

            // Loop through each row in the result set
            while (rs.next()) {
                // Create a new Contract object with the retrieved data
                Contract contract = new Contract(
                        rs.getInt("ContractID"), // Contract ID          
                        rs.getInt("RoomID"), // Room ID
                        rs.getDate("StartDate"), // Start date
                        rs.getDate("EndDate"), // End date
                        rs.getString("Status"), // Status
                        rs.getDate("CreatedAt") // Created at
                );
                // Add the contract object to the list
                contracts.add(contract);
                // Debug statement to print the name of the retrieved contract (using tenant name)
                System.out.println("📌 Retrieved contract for: " + contract.getTenantName());
            }

            // Debug statement to print the total number of contracts retrieved
            System.out.println("✅ Total contracts retrieved: " + contracts.size());

        } catch (SQLException ex) {
            // Print an error message if a SQL query error occurs
            System.out.println("❌ SQL Query Error: " + ex.getMessage());
        }
        // Return the list of contracts
        return contracts;
    }
}

