/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.RoomDAO;
import dao.CategoryDAO;
import model.Room;
import model.Category;
import utils.DBContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/rooms")
public class RoomListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status = request.getParameter("status");
         
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String minAreaStr = request.getParameter("minArea");
        String maxAreaStr = request.getParameter("maxArea");
        String blockStr = request.getParameter("block");
        String categoryStr = request.getParameter("category");

        Double minPrice = null, maxPrice = null;
        Double minArea = null, maxArea = null;
        Integer blockID = null, categoryID = null;

        try {
            if (minPriceStr != null && !minPriceStr.trim().isEmpty()) {
                minPrice = Double.parseDouble(minPriceStr);
            }
            if (maxPriceStr != null && !maxPriceStr.trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceStr);
            }
            if (minAreaStr != null && !minAreaStr.trim().isEmpty()) {
                minArea = Double.parseDouble(minAreaStr);
            }
            if (maxAreaStr != null && !maxAreaStr.trim().isEmpty()) {
                maxArea = Double.parseDouble(maxAreaStr);
            }
            if (blockStr != null && !blockStr.trim().isEmpty()) {
                blockID = Integer.parseInt(blockStr);
            }
            if (categoryStr != null && !categoryStr.trim().isEmpty()) {
                categoryID = Integer.parseInt(categoryStr);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        int page = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        Connection conn = null;
        try {
            conn = DBContext.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(RoomListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        RoomDAO dao = new RoomDAO(conn);
        CategoryDAO categoryDAO = new CategoryDAO(conn);

        // Lấy danh sách phòng phân trang
        int totalRooms = dao.totalRoomCount(status, minPrice, maxPrice, minArea, maxArea, blockID, categoryID);
        int totalPages = (int) Math.ceil((double) totalRooms / PAGE_SIZE);
        int offset = (page - 1) * PAGE_SIZE;

        List<Room> roomList = dao.roomsPaginated(
                status,  minPrice, maxPrice, minArea, maxArea, blockID, categoryID,
                offset, PAGE_SIZE
        );

        // Lấy danh sách category để đổ vào dropdown filter
        List<Category> categoryList = categoryDAO.getAllCategories();

        request.setAttribute("roomList", roomList);
        request.setAttribute("categoryList", categoryList); // ✅ Quan trọng
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("selectedCategory", categoryID);

        request.getRequestDispatcher("room-list.jsp").forward(request, response);
    }
}
