/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.RoomDAO;
import model.Room;
import utils.DBContext;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/room-detail")
public class RoomDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        Room room = null;
        List<Room> featuredRooms = null;

        if (idParam != null) {
            try ( Connection conn = DBContext.getConnection()) {
                int id = Integer.parseInt(idParam);
                RoomDAO dao = new RoomDAO(conn);
                room = dao.getRoomById(id);
                featuredRooms = dao.getFeaturedRooms(5);

                System.out.println("Room fetched = " + (room != null ? room.getRoomNumber() : "null"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("room", room);
        request.setAttribute("featuredRooms", featuredRooms);
        request.getRequestDispatcher("/room-detail.jsp").forward(request, response);
    }
}
