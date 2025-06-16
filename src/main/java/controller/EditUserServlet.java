package controller;

import dao.UserDAO;
import dao.BlockDAO;            // THÊM DÒNG NÀY
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.Block;            // THÊM DÒNG NÀY

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;         // THÊM DÒNG NÀY

@WebServlet("/admin/editUser")
public class EditUserServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userIDStr = request.getParameter("userID");

        try {
            if (userIDStr != null && !userIDStr.trim().isEmpty()) {
                int userID = Integer.parseInt(userIDStr);
                User user = userDAO.getUserById(userID);

                if (user != null) {
                    // LẤY DANH SÁCH BLOCKS VÀ TRUYỀN LÊN
                    List<Block> blockList = new BlockDAO().getAllBlocks();
                    request.setAttribute("user", user);
                    request.setAttribute("blockList", blockList);    // TRUYỀN blockList lên JSP
                    request.getRequestDispatcher("/admin/editUser.jsp").forward(request, response);
                    return;
                }
            }

            request.setAttribute("error", "User not found.");
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Error loading user: " + e.getMessage());
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
        }
    }
}
