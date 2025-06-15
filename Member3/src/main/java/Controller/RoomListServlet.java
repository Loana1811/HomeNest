package Controller;

import dao.RoomDAO;
import model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

@WebServlet("/rooms")
public class RoomListServlet extends HttpServlet {

    private static final int PAGE_SIZE = 6; // số phòng trên 1 trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Lấy các tham số lọc ---
        String status = request.getParameter("status");
        String type = request.getParameter("type");
        String minPriceStr = request.getParameter("minPrice");
        String maxPriceStr = request.getParameter("maxPrice");
        String minAreaStr = request.getParameter("minArea");
        String maxAreaStr = request.getParameter("maxArea");
        String blockStr = request.getParameter("block");

        Double minPrice = null, maxPrice = null;
        Double minArea = null, maxArea = null;
        Integer blockID = null;

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
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // --- Lấy số trang hiện tại ---
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
            conn = DBUtils.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(RoomListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        RoomDAO dao = new RoomDAO(conn);

        // --- Tổng số phòng theo filter ---
        int totalRooms = dao.totalRoomCount(status, type, minPrice, maxPrice, minArea, maxArea, blockID);
        int totalPages = (int) Math.ceil((double) totalRooms / PAGE_SIZE);
        int offset = (page - 1) * PAGE_SIZE;

        List<Room> roomList = dao.roomsPaginated(
                status, type, minPrice, maxPrice, minArea, maxArea, blockID,
                offset, PAGE_SIZE
        );

        // Truyền dữ liệu về JSP
        request.setAttribute("roomList", roomList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("room-list.jsp").forward(request, response);
    }
}
