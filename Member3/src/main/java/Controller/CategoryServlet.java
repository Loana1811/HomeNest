/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dao.CategoryDAO;
import model.Category;
import utils.DBUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try ( Connection conn = DBUtils.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);

            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Category category = dao.getCategoryById(id);
                request.setAttribute("category", category);
                request.getRequestDispatcher("category-edit.jsp").forward(request, response);

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                dao.deleteCategory(id);
                response.sendRedirect("category?action=list");

            } else {
                // Default: list categories
                List<Category> list = dao.getAllCategories();
                request.setAttribute("categoryList", list);
                request.getRequestDispatcher("category-list.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try ( Connection conn = DBUtils.getConnection()) {
            CategoryDAO dao = new CategoryDAO(conn);

            if ("create".equals(action)) {
                String name = request.getParameter("categoryName");
                String desc = request.getParameter("description");
                Category category = new Category(name, desc);
                boolean success = dao.insertCategory(category);
                if (success) {
                    response.sendRedirect("category?action=list");
                } else {
                    request.setAttribute("error", "Failed to create category.");
                    request.getRequestDispatcher("category-create.jsp").forward(request, response);
                }

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("categoryID"));
                String name = request.getParameter("categoryName");
                String desc = request.getParameter("description");
                dao.updateCategory(id, name, desc);
                response.sendRedirect("category?action=list");
            }

        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            request.setAttribute("errorMessage", sw.toString());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
