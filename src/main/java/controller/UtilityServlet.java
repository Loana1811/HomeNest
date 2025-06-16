/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RoomDAO;
import dao.UtilityHistoryDAO;
import dao.UtilityReadingDAO;
import dao.UtilityTypeDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.UtilityHistoryView;
import model.UtilityType;

/**
 *
 * @author kloane
 */
@WebServlet("/admin/utility")
public class UtilityServlet extends HttpServlet {

    private UtilityTypeDAO dao = new UtilityTypeDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action == null ? "list" : action) {
                case "create": {
                    RoomDAO roomDAO = new RoomDAO();
                    List<Object[]> roomsCreate = roomDAO.getAllRoomIdName();
                    request.setAttribute("rooms", roomsCreate);
                    request.getRequestDispatcher("/admin/createIncurredFee.jsp").forward(request, response);
                    break;
                }

                case "edit": {
                    int editId = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("utility", dao.getById(editId));

                    RoomDAO roomDAO2 = new RoomDAO();
                    List<Object[]> rooms = roomDAO2.getRoomsAppliedToUtility(editId);
                    request.setAttribute("rooms", rooms);

                    request.getRequestDispatcher("/admin/utility-edit.jsp").forward(request, response);
                    break;
                }

                case "delete": {
                    int deleteId = Integer.parseInt(request.getParameter("id"));
                    UtilityType utility = dao.getById(deleteId);

                    if (utility.isSystem()) {
                        request.setAttribute("error", "❌ This is a default system utility and cannot be deleted.");
                    } else {
                        new UtilityReadingDAO().deleteZeroReadingsByUtilityType(deleteId);

                        if (dao.isUtilityTypeInUse(deleteId)) {
                            request.setAttribute("error", "❌ This utility has been used in actual readings or billing and cannot be deleted.");
                        } else {
                            dao.delete(deleteId);
                            response.sendRedirect("utility?action=list");
                            return;
                        }
                    }

                    loadUtilityLists(request);
                    request.getRequestDispatcher("/admin/utility-list.jsp").forward(request, response);
                    break;
                }

                case "history": {
                    List<UtilityHistoryView> history = new UtilityHistoryDAO().getHistory();
                    request.setAttribute("historyList", history);
                    request.getRequestDispatcher("/admin/utility-history.jsp").forward(request, response);
                    break;
                }

                case "list":
                default: {
                    loadUtilityLists(request);
                    request.getRequestDispatcher("/admin/utility-list.jsp").forward(request, response);
                    break;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void loadUtilityLists(HttpServletRequest request) throws Exception {
        List<UtilityType> all = dao.getAll();
        List<UtilityType> systemList = new ArrayList<>();
        List<UtilityType> userList = new ArrayList<>();

        for (UtilityType u : all) {
            if (u.isSystem()) {
                systemList.add(u);
            } else {
                userList.add(u);
            }
        }

        request.setAttribute("systemList", systemList);
        request.setAttribute("userList", userList);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            String unit = request.getParameter("unit");

            if ("create".equals(action)) {
                if (dao.isUtilityNameExists(name)) {
                    request.setAttribute("error", "❌ Utility name already exists. Please choose another name!");
                    RoomDAO roomDAO = new RoomDAO();
                    List<Object[]> roomsCreate = roomDAO.getAllRoomIdName();
                    request.setAttribute("rooms", roomsCreate);
                    request.getRequestDispatcher("/admin/createIncurredFee.jsp").forward(request, response);
                    return;
                }

                UtilityType newUtility = new UtilityType(0, name, price, unit, false);
                dao.insert(newUtility);

                int newId = dao.getLastInsertedId();
                String[] selectedRoomIds = request.getParameterValues("roomIds");

                if (selectedRoomIds != null) {
                    UtilityReadingDAO urDao = new UtilityReadingDAO();
                    for (String rid : selectedRoomIds) {
                        urDao.assignUtilityToRoom(Integer.parseInt(rid), newId);
                    }
                }

            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                UtilityType existing = dao.getById(id); 

                double oldPrice = existing.getPrice();
                double newPrice = Double.parseDouble(request.getParameter("price"));

                dao.update(new UtilityType(id, name, newPrice, unit, existing.isSystem()));

                if (Double.compare(oldPrice, newPrice) != 0) {
                    new UtilityHistoryDAO().insertHistory(
                            id, 
                            existing.getName(),
                            oldPrice,
                            newPrice,
                            "admin", 
                            java.sql.Date.valueOf(java.time.LocalDate.now())
                    );
                }

            }

            response.sendRedirect("utility?action=list");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
