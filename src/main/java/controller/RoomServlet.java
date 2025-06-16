/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BlockDAO;
import dao.CategoryDAO;
import dao.RoomDAO;
import model.Room;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import model.Block;
import model.Category;

/**
 *
 * @author Admin
 */
@WebServlet("/rooms")
@MultipartConfig
public class RoomServlet extends HttpServlet {

    RoomDAO roomDAO = new RoomDAO();
    BlockDAO blockDAO = new BlockDAO();
    CategoryDAO categoryDAO = new CategoryDAO();

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
            out.println("<title>Servlet RoomServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RoomServlet at " + request.getContextPath() + "</h1>");
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
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                request.setAttribute("room", new Room());
                request.setAttribute("blockList", blockDAO.getAllBlocks());
                request.setAttribute("categoryList", categoryDAO.getAll());
                request.setAttribute("action", "insert");
                request.getRequestDispatcher("/admin/create_room.jsp").forward(request, response);
                break;

            case "edit":
                int editId = Integer.parseInt(request.getParameter("id"));
                Room room = roomDAO.getById(editId);
                request.setAttribute("room", room);
                request.setAttribute("blockList", blockDAO.getAllBlocks());
                request.setAttribute("categoryList", categoryDAO.getAll());
                request.setAttribute("action", "update");
                request.getRequestDispatcher("/admin/edit_room.jsp").forward(request, response);
                break;

            case "delete":
                int deleteId = Integer.parseInt(request.getParameter("id"));
                roomDAO.delete(deleteId);
                response.sendRedirect("rooms?action=list");
                break;
            case "view":
                int viewId = Integer.parseInt(request.getParameter("id"));
                Room viewRoom = roomDAO.getById(viewId); // Phương thức lấy 1 phòng theo ID
                List<Block> blocks = blockDAO.getAllBlocks();      // Nếu cần danh sách Block
                List<Category> categories = categoryDAO.getAll(); // Nếu cần danh sách Category

                request.setAttribute("room", viewRoom);
                request.setAttribute("blockList", blocks);
                request.setAttribute("categoryList", categories);
                request.getRequestDispatcher("/admin/view_room.jsp").forward(request, response);
                break;

            case "list":
                String selectedCategoryID = request.getParameter("categoryID");
                String selectedBlockID = request.getParameter("blockID");

                List<Room> roomList = roomDAO.filterRoomsByCategoryAndBlock(selectedCategoryID, selectedBlockID);
                request.setAttribute("list", roomList);
                request.setAttribute("blockList", blockDAO.getAllBlocks());
                request.setAttribute("categoryID", selectedCategoryID);
                request.setAttribute("blockID", selectedBlockID);

                request.getRequestDispatcher("/admin/list_rooms.jsp").forward(request, response);
                break;
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
        Room room = extractRoomFromRequest(request);
        Integer blockID = room.getBlockID();

        if ("insert".equals(action)) {
            if (blockID != null) {
                Block block = blockDAO.getBlockById(blockID);
                if (block != null && block.getRoomCount() >= block.getMaxRooms()) {
                    request.setAttribute("error", "This block is already full. Please choose another block.");
                    request.setAttribute("room", room);
                    request.setAttribute("blockList", blockDAO.getAllBlocks());
                    request.setAttribute("categoryList", categoryDAO.getAll());
                    request.getRequestDispatcher("/admin/create_room.jsp").forward(request, response);
                    return;
                }
            }
            roomDAO.insert(room);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            room.setRoomID(id);
            roomDAO.update(room);
        }

        response.sendRedirect("rooms?action=list");
    }

    private Room extractRoomFromRequest(HttpServletRequest request) {
        String roomNumber = request.getParameter("roomNumber");
        double rentPrice = Double.parseDouble(request.getParameter("rentPrice"));
        String areaStr = request.getParameter("area");
        Double area = (areaStr == null || areaStr.isEmpty()) ? null : Double.parseDouble(areaStr);
        String status = request.getParameter("status");

        Integer blockID = request.getParameter("blockID").isEmpty() ? null : Integer.parseInt(request.getParameter("blockID"));
        Integer categoryID = request.getParameter("categoryID").isEmpty() ? null : Integer.parseInt(request.getParameter("categoryID"));

        String[] highlightsArr = request.getParameterValues("highlights");
        String highlights = (highlightsArr != null) ? String.join(",", highlightsArr) : null;

        // Mới thêm: description
        String description = request.getParameter("description");

        // Mới thêm: postedDate (hiện tại)
        Timestamp postedDate = new Timestamp(System.currentTimeMillis());

        // Xử lý ảnh
        Part imagePart = null;
        String imagePath = null;
        try {
            imagePart = request.getPart("image");
            if (imagePart != null && imagePart.getSize() > 0) {
                String fileName = extractFileName(imagePart);
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                String fullPath = uploadPath + File.separator + fileName;
                imagePart.write(fullPath);
                imagePath = "uploads/" + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Room(roomNumber, rentPrice, area, status, blockID, categoryID, highlights, imagePath, description, postedDate);
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf('=') + 2, token.length() - 1);
            }
        }
        return null;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "RoomServlet handles CRUD for rooms.";
    }

}
