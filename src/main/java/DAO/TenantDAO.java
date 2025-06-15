/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DB.DBContext;
import Model.Tenant;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class TenantDAO extends DBContext {

    public ArrayList<Tenant> getAllTenants() {
        ArrayList<Tenant> tenants = new ArrayList<>();
        String squery = "SELECT * FROM Tenants WHERE TenantID NOT IN (SELECT TenantID FROM Contracts )";

        try ( ResultSet rs = this.execSelectQuery(squery)) {
            while (rs.next()) {
                Tenant tenant = new Tenant();
                tenant.setTenantID(rs.getInt("TenantID"));
                tenant.setCustomerID(rs.getInt("CustomerID"));
                tenant.setJoinDate(rs.getDate("JoinDate"));
                tenants.add(tenant);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tenants;
    }
}
