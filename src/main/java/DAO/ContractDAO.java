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

    public void addContract(Contract contract) throws SQLException {
        String query = "INSERT INTO Contracts (TenantID, RoomID, StartDate, EndDate, Status, CreatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, contract.getTenantId());
            ps.setInt(2, contract.getRoomId());
            ps.setDate(3, new java.sql.Date(contract.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(contract.getEndDate().getTime()));
            ps.setString(5, contract.getStatus());
            ps.setDate(6, new java.sql.Date(contract.getCreatedAt().getTime()));
            ps.setDouble(7, contract.getAmount());
            ps.executeUpdate();
            System.out.println("✅ Contract added for TenantID: " + contract.getTenantId());
        } catch (SQLException ex) {
            System.out.println("❌ SQL Query Error: " + ex.getMessage());
            throw ex;
        }
    }

    public void updateContract(Contract contract) throws SQLException {
        String query = "UPDATE Contracts SET TenantID = ?, RoomID = ?, StartDate = ?, EndDate = ?, Status = ? WHERE ContractID = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, contract.getTenantId());
            ps.setInt(2, contract.getRoomId());
            ps.setDate(3, new java.sql.Date(contract.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(contract.getEndDate().getTime()));
            ps.setString(5, contract.getStatus());
            ps.setInt(6, contract.getContractId()); // Added missing parameter
            ps.executeUpdate();
            System.out.println("✅ Contract updated for ContractID: " + contract.getContractId());
        } catch (SQLException ex) {
            System.out.println("❌ SQL Query Error: " + ex.getMessage());
            throw ex;
        }
    }

    public Contract getContractById(int id) throws SQLException {
        String query = "SELECT c.[ContractID], c.[TenantID], c.[RoomID], c.[StartDate], c.[EndDate], c.[Status], c.[CreatedAt] "
                + "FROM [RentalManagement].[dbo].[Contracts] c "
                + "JOIN [RentalManagement].[dbo].[Tenants] t ON c.[TenantID] = t.[TenantID] "
                + "WHERE c.[ContractID] = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Contract contract = new Contract(
                            rs.getInt("ContractID"),
                            rs.getInt("TenantID"),
                            rs.getInt("RoomID"),
                            rs.getDate("StartDate"),
                            rs.getDate("EndDate"),
                            rs.getString("Status"),
                            rs.getDate("CreatedAt")
                    );
                    System.out.println("📌 Retrieved contract: " + contract.getContractId());
                    return contract;
                }
            }
        } catch (SQLException ex) {
            System.out.println("❌ SQL Query Error: " + ex.getMessage());
            throw ex;
        }
        return null;
    }
}
