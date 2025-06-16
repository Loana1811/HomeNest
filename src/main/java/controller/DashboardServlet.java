/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author kloane
 */
@WebServlet("/admin/dashboard")
public class DashboardServlet extends HttpServlet {

    private DashboardDAO dao = new DashboardDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("totalRooms", dao.countRooms());
            req.setAttribute("totalTenants", dao.countTenants());
            req.setAttribute("unpaidBills", dao.countUnpaidBills());
            req.setAttribute("recentReadings", dao.countReadingsLast30Days());
            req.setAttribute("electricityData", dao.getDailyElectricityUsageLastWeek());
            req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
