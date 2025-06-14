/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ContractDAO;
import Model.Contract;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ContractServlet", urlPatterns = {"/contracts"})
public class ContractServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ContractServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ContractServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action) || action == null) {
            ContractDAO contractDAO = new ContractDAO();
            List<Contract> contracts = contractDAO.getAllContracts();
            // Pagination logic (simple implementation)

            request.setAttribute("contracts", contracts);

            request.getRequestDispatcher("/listContracts.jsp").forward(request, response);
        } else if ("create".equals(action)) {
            request.getRequestDispatcher("/createContract.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null && idParam.matches("\\d+")) {
                int id = Integer.parseInt(idParam);
                try {
                    ContractDAO contractDAO = new ContractDAO();
                    Contract contract = contractDAO.getContractById(id);
                    if (contract != null) {
                        request.setAttribute("contract", contract);
                        request.getRequestDispatcher("/editContract.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Contract not found");
                    }
                } catch (SQLException e) {
                    throw new ServletException("Error fetching contract", e);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contract ID");
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equals(action)) {
            String tenantIdStr = request.getParameter("tenantId");
            String roomIdStr = request.getParameter("roomId");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");

            if (tenantIdStr != null && roomIdStr != null && startDateStr != null && endDateStr != null) {
                try {
                    int tenantId = Integer.parseInt(tenantIdStr);
                    int roomId = Integer.parseInt(roomIdStr);
                    java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                    java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

                    Contract newContract = new Contract(0, tenantId, roomId, startDate, endDate, "Active", new java.sql.Date(System.currentTimeMillis()));
                    ContractDAO contractDAO = new ContractDAO();
                    contractDAO.addContract(newContract);
                    response.sendRedirect(request.getContextPath() + "/contracts");
                } catch (SQLException e) {
                    throw new ServletException("Error adding contract", e);
                }
            } else {
                request.setAttribute("error", "Missing required fields");
                request.getRequestDispatcher("/createContract.jsp").forward(request, response);
            }
        } else if ("edit".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && idStr.matches("\\d+")) {
                int id = Integer.parseInt(idStr);
                String tenantIdStr = request.getParameter("tenantId");
                String roomIdStr = request.getParameter("roomId");
                String startDateStr = request.getParameter("startDate");
                String endDateStr = request.getParameter("endDate");
                String statusStr = request.getParameter("status");

                if (tenantIdStr != null && roomIdStr != null && startDateStr != null && endDateStr != null && statusStr != null) {
                    try {
                        int tenantId = Integer.parseInt(tenantIdStr);
                        int roomId = Integer.parseInt(roomIdStr);
                        java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                        java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

                        Contract updatedContract = new Contract(tenantId, roomId, startDate, endDate, statusStr);
                        updatedContract.setContractId(id); // Added to set ContractID
                        ContractDAO contractDAO = new ContractDAO();
                        contractDAO.updateContract(updatedContract);
                        response.sendRedirect(request.getContextPath() + "/contracts?action=show&id=" + id);
                    } catch (SQLException e) {
                        throw new ServletException("Error updating contract", e);
                    }
                } else {
                    request.setAttribute("error", "Missing required fields");
                    request.getRequestDispatcher("/editContract.jsp").forward(request, response);
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contract ID");
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
