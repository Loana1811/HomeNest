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

        if ("create".equals(action)) {
            String tenantIdStr = request.getParameter("tenantId");
            String roomIdStr = request.getParameter("roomId");
            String startDateStr = request.getParameter("startDate");
            String endDateStr = request.getParameter("endDate");
            String status = request.getParameter("status");
            String createdAtStr = request.getParameter("createdAt");

            if (tenantIdStr != null && roomIdStr != null && startDateStr != null && endDateStr != null
                    && status != null && createdAtStr != null) {
                try {
                    int tenantId = Integer.parseInt(tenantIdStr);
                    int roomId = Integer.parseInt(roomIdStr);
                    java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                    java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);
                    java.sql.Date createdAt = java.sql.Date.valueOf(createdAtStr);

                    Contract newContract = new Contract(0, tenantId, roomId, startDate, endDate, status, createdAt);
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
                String status = request.getParameter("status");
                String createdAtStr = request.getParameter("createdAt");

                if (tenantIdStr != null && roomIdStr != null && startDateStr != null && endDateStr != null
                        && status != null && createdAtStr != null) {
                    try {
                        int tenantId = Integer.parseInt(tenantIdStr);
                        int roomId = Integer.parseInt(roomIdStr);
                        java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                        java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);
                        java.sql.Date createdAt = java.sql.Date.valueOf(createdAtStr);

                        Contract updatedContract = new Contract(id, tenantId, roomId, startDate, endDate, status, createdAt);
                        ContractDAO contractDAO = new ContractDAO();
                        contractDAO.updateContract(updatedContract);
                        response.sendRedirect(request.getContextPath() + "/contracts?action=show&id=" + id);
                    }  catch (SQLException e) {
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
         * Handles the HTTP <code>POST</code> method.
         *
         * @param request servlet request
         * @param response servlet response
         * @throws ServletException if a servlet-specific error occurs
         * @throws IOException if an I/O error occurs
         */
        @Override
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
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

                    if (tenantIdStr != null && roomIdStr != null && startDateStr != null && endDateStr != null) {
                        try {
                            int tenantId = Integer.parseInt(tenantIdStr);
                            int roomId = Integer.parseInt(roomIdStr);
                            java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
                            java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

                            Contract updatedContract = new Contract(id, tenantId, roomId, startDate, endDate, "Active", new java.sql.Date(System.currentTimeMillis()));
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
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
