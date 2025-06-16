/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.BlockDAO;
import dao.RoomDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Block;
import model.Room;

/**
 *
 * @author Admin
 */
@WebServlet("/blocks")
public class BlockServlet extends HttpServlet {

    BlockDAO blockDAO = new BlockDAO();

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
            out.println("<title>Servlet BlockServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlockServlet at " + request.getContextPath() + "</h1>");
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
                request.setAttribute("block", new Block());
                request.setAttribute("action", "insert");
                request.getRequestDispatcher("/admin/add_block.jsp").forward(request, response);
                break;

            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Block blockEdit = blockDAO.getBlockById(idEdit);
                request.setAttribute("block", blockEdit);
                request.setAttribute("action", "update");
                request.getRequestDispatcher("/admin/edit_block.jsp").forward(request, response);
                break;

//            case "delete":
//                int idDelete = Integer.parseInt(request.getParameter("id"));
//                boolean success = blockDAO.deleteBlock(idDelete);  // KHÔNG dùng delete(id)!
//                if (!success) {
//                    request.setAttribute("message", "Cannot delete block because it has rooms.");
//                    request.setAttribute("alertType", "error");
//                    request.getRequestDispatcher("list_blocks.jsp").forward(request, response);
//                    return;
//                }
//                response.sendRedirect("blocks?action=list");
//                break;

            case "list":
            default:
                List<Block> blockList = blockDAO.getAllBlocks();
                request.setAttribute("blockList", blockList);
                request.getRequestDispatcher("/admin/list_blocks.jsp").forward(request, response);
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
        Block block = extractBlockFromRequest(request);

        if ("insert".equals(action)) {
            blockDAO.insert(block);
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            block.setBlockID(id);
            blockDAO.updateBlock(block);
        }

        response.sendRedirect("blocks?action=list");
    }

    private Block extractBlockFromRequest(HttpServletRequest request) {
        String blockName = request.getParameter("blockName");
        int maxRooms = Integer.parseInt(request.getParameter("maxRooms"));
        return new Block(blockName, maxRooms);
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
