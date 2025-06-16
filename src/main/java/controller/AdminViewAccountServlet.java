/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Customer;
import model.User;

/**
 *
 * @author ThanhTruc
 */
@WebServlet(name = "AdminViewAccountServlet", urlPatterns = {"/viewListAccount"})
public class AdminViewAccountServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve users and customers from database
            UserDAO userDAO = new UserDAO();
            CustomerDAO customerDAO = new CustomerDAO();

            List<User> userList = userDAO.getAllUsers();
            List<Customer> customerList = customerDAO.getAllCustomers();

            // ----------- LẤY SỐ LIỆU CUSTOMER -----------
            int totalCustomers = customerDAO.getTotalCustomerCount();
            int convertedCustomers = customerDAO.getConvertedCustomerCount();
            int potentialCustomers = customerDAO.getPotentialCustomerCount();
            int inactiveCustomers = customerDAO.getInactiveCustomerCount();

            // Set attributes for JSP
            request.setAttribute("userList", userList);
            request.setAttribute("customerList", customerList);

            // Set các số liệu customer
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("convertedCustomers", convertedCustomers);
            request.setAttribute("potentialCustomers", potentialCustomers);
            request.setAttribute("inactiveCustomers", inactiveCustomers);

            // Forward to view
            request.getRequestDispatcher("admin/viewListAccount.jsp").forward(request, response);

        } catch (Exception ex) {
            // Handle errors
            ex.printStackTrace();
            request.setAttribute("error", "Error retrieving account data: " + ex.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
