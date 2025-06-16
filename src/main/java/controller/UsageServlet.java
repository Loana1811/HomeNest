/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.UtilityReadingDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import model.UtilityUsageView;

/**
 *
 * @author kloane
 */
@WebServlet("/admin/usage")
public class UsageServlet extends HttpServlet {
    private UtilityReadingDAO dao = new UtilityReadingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<UtilityUsageView> usages = dao.getAllUsages();
            req.setAttribute("usages", usages);
            req.getRequestDispatcher("/admin/utility-usage-list.jsp")
               .forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    
}
