package controller;

import dao.CustomerDAO;
import dao.UserDAO;
import dao.BlockDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Customer;
import model.User;
import model.Block;

@WebServlet("/admin/account")
public class AdminAccountServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            List<Block> blockList = null;
            try {
                blockList = new BlockDAO().getAllBlocks();
            } catch (SQLException ex) {
                Logger.getLogger(AdminAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("blockList", blockList);
            request.getRequestDispatcher("/admin/createAccount.jsp").forward(request, response);
            return; // <-- Quan trọng, không chạy tiếp xuống dưới
        }
        // Xử lý xem chi tiết khách hàng
        if ("viewDetail".equals(action)) {
            String customerIdStr = request.getParameter("customerID");
            if (customerIdStr == null) {
                response.sendRedirect("error.jsp");
                return;
            }
            int customerID = Integer.parseInt(customerIdStr);
            List<Map<String, Object>> detailList = null;
            try {
                detailList = customerDAO.getFullCustomerDetails(customerID);
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("error", "Database Error: " + e.getMessage());
                request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                return;
            }
            request.setAttribute("details", detailList);
            request.getRequestDispatcher("/admin/viewDetailsCustomer.jsp").forward(request, response);
            return;
        }
        // ==== CHỖ SỬA: Thêm xử lý action delete cho GET ====
        if ("delete".equals(action)) {
            try {
                deleteAccount(request, response);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
            return;
        }
        // ==== HẾT CHỖ SỬA ====

        // Phần load danh sách vẫn giữ nguyên, chuyển nó vào else (nếu cần), hoặc làm như sau:
        List<Customer> customerList = null;
        try {
            customerList = customerDAO.getAllCustomers();
        } catch (SQLException ex) {
            Logger.getLogger(AdminAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<Integer> cannotDeleteCustomerIds = new ArrayList<>();
        for (Customer c : customerList) {
            try {
                if (customerDAO.hasContractOrTenant(c.getCustomerID())) {
                    cannotDeleteCustomerIds.add(c.getCustomerID());
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminAccountServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        request.setAttribute("customerList", customerList);
        request.setAttribute("cannotDeleteCustomerIds", cannotDeleteCustomerIds);

        response.sendRedirect(request.getContextPath() + "/viewListAccount");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        try {
            switch (action) {
                case "add":
                    addAccount(request, response);
                    break;
                case "edit":
                    editAccount(request, response);
                    break;
                case "delete":
                    deleteAccount(request, response);
                    break;
                case "disable":
                    disableAccount(request, response);
                    break;
                default:
                    response.sendRedirect("error.jsp");
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Database Error: " + e.getMessage());
            request.getRequestDispatcher("admin/error.jsp").forward(request, response);
        }
    }

    private void addAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String roleIDStr = request.getParameter("roleID");

        // Kiểm tra các tham số bắt buộc
        if (fullName == null || fullName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phoneNumber == null || phoneNumber.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || confirmPassword == null || confirmPassword.trim().isEmpty()
                || roleIDStr == null || roleIDStr.trim().isEmpty()) {
            request.setAttribute("error", "All fields are required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int roleID = Integer.parseInt(roleIDStr);

            // Tạo đối tượng User hoặc Customer với thông tin cơ bản
            if (roleID == 3) { // Customer
                Customer customer = new Customer();
                customer.setFullName(fullName.trim());
                customer.setEmail(email.trim());
                customer.setPhoneNumber(phoneNumber.trim());
                customer.setCustomerPassword(customerDAO.hashMD5(password));
                customer.setCccd(null);
                customer.setGender(null);
                customer.setBirthDate(null);
                customer.setAddress(null);
                customer.setStatus("Potential");

                boolean success = customerDAO.createCustomer(customer);
                if (!success) {
                    request.setAttribute("error", "Failed to create customer account.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else { // Admin or Manager
                User user = new User();
                user.setFullName(fullName.trim());
                user.setEmail(email.trim());
                user.setPhoneNumber(phoneNumber.trim());
                user.setPassword(password); // UserDAO sẽ hash password
                user.setRoleID(roleID);
                user.setStatus("Active");
                if (roleID == 2) {
                    String blockIDStr = request.getParameter("blockID");
                    if (blockIDStr != null && !blockIDStr.trim().isEmpty()) {
                        user.setBlockID(Integer.parseInt(blockIDStr));
                    }
                }
                boolean success = userDAO.addUser(user);
                if (!success) {
                    request.setAttribute("error", "Failed to create user account.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            }
            response.sendRedirect(request.getContextPath() + "/viewListAccount");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid role ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    private void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String userIDStr = request.getParameter("userID");
        String customerIDStr = request.getParameter("customerID");

        try {
            if (userIDStr != null && !userIDStr.trim().isEmpty()) {
                int userID = Integer.parseInt(userIDStr);
                boolean success = userDAO.deactivateUser(userID);
                if (!success) {
                    request.setAttribute("error", "Failed to disable user.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else if (customerIDStr != null && !customerIDStr.trim().isEmpty()) {
                int customerID = Integer.parseInt(customerIDStr);

                // ======= Thêm kiểm tra quan hệ contract/tenant trước khi xóa =======
                if (customerDAO.hasContractOrTenant(customerID)) {
                    request.setAttribute("error", "This customer has related contracts or has rented a room, cannot delete!");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
                // ====================================================================

                Customer customer = customerDAO.getCustomerById(customerID);
                if (customer != null) {
                    customer.setStatus("Inactive");
                    boolean success = customerDAO.updateCustomer(customer);
                    if (!success) {
                        request.setAttribute("error", "Failed to disable customer.");
                        request.getRequestDispatcher("error.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("error", "Customer not found.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("error", "User ID or Customer ID is required for deletion.");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/viewListAccount");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

  private void disableAccount(HttpServletRequest request, HttpServletResponse response) 
        throws SQLException, IOException, ServletException {
    String userIDStr = request.getParameter("userID");
    String customerIDStr = request.getParameter("customerID");
    String reason = request.getParameter("reason");

    try {
        if (userIDStr != null && !userIDStr.trim().isEmpty()) {
            // Vô hiệu hóa User
            int userID = Integer.parseInt(userIDStr);
            boolean success = userDAO.deactivateUser(userID);
            
            if (!success) {
                request.setAttribute("error", "Failed to disable user.");
                request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                return;
            }
            
        } else if (customerIDStr != null && !customerIDStr.trim().isEmpty()) {
            // Vô hiệu hóa Customer
            int customerID = Integer.parseInt(customerIDStr);
            
            // Kiểm tra ràng buộc
            if (customerDAO.hasContractOrTenant(customerID)) {
                request.setAttribute("error", "Cannot disable customer with active contracts or tenant records!");
                request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                return;
            }
            
            // Sử dụng method mới để update status
            boolean success = customerDAO.updateCustomerStatus(customerID, "Inactive");
            
            if (!success) {
                request.setAttribute("error", "Failed to disable customer.");
                request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
                return;
            }
            
        } else {
            request.setAttribute("error", "User ID or Customer ID is required for disabling.");
            request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
            return;
        }
        
        // Redirect về trang danh sách
        response.sendRedirect(request.getContextPath() + "/viewListAccount");
        
    } catch (NumberFormatException e) {
        request.setAttribute("error", "Invalid ID format.");
        request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "System error: " + e.getMessage());
        request.getRequestDispatcher("/admin/error.jsp").forward(request, response);
    }
}
    private void editAccount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        String userIDStr = request.getParameter("userID");
        String customerIDStr = request.getParameter("customerID");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String roleIDStr = request.getParameter("roleID");
        String status = request.getParameter("status");

        // Kiểm tra các tham số
        if (userIDStr == null && customerIDStr == null) {
            request.setAttribute("error", "User ID or Customer ID is required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        if (fullName == null || fullName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phoneNumber == null || phoneNumber.trim().isEmpty()) {
            request.setAttribute("error", "Full name, email and phone number are required.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            if (userIDStr != null && !userIDStr.trim().isEmpty()) {
                int userID = Integer.parseInt(userIDStr);
                int roleID = Integer.parseInt(roleIDStr);

                User user = userDAO.getUserById(userID);
                if (user == null) {
                    request.setAttribute("error", "User not found.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                user.setFullName(fullName.trim());
                user.setEmail(email.trim());
                user.setPhoneNumber(phoneNumber.trim());
                user.setStatus(status != null ? status : "Active");
                user.setRoleID(roleID);

                // ======== Thêm đoạn này để cập nhật block cho Manager ==========
                if (roleID == 2) {
                    String blockIDStr = request.getParameter("blockID");
                    if (blockIDStr != null && !blockIDStr.trim().isEmpty()) {
                        user.setBlockID(Integer.parseInt(blockIDStr));
                    } else {
                        user.setBlockID(null);
                    }
                } else {
                    user.setBlockID(null);
                }
                // ==============================================================

                // Password update (nếu admin nhập)
                String newPassword = request.getParameter("password");
                if (newPassword != null && !newPassword.trim().isEmpty()) {
                    user.setPassword(userDAO.hashMD5(newPassword.trim()));
                }

                boolean success = userDAO.updateUser(user);
                if (!success) {
                    request.setAttribute("error", "Failed to update user.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            } else if (customerIDStr != null && !customerIDStr.trim().isEmpty()) {
                // Cập nhật Customer
                int customerID = Integer.parseInt(customerIDStr);
                Customer customer = customerDAO.getCustomerById(customerID);

                if (customer == null) {
                    request.setAttribute("error", "Customer not found.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }

                customer.setFullName(fullName.trim());
                customer.setEmail(email.trim());
                customer.setPhoneNumber(phoneNumber.trim());
                customer.setStatus(status != null ? status : "Potential");

                // Lấy thêm các thông tin khác nếu có
                String cccd = request.getParameter("cccd");
                String gender = request.getParameter("gender");
                String birthDate = request.getParameter("birthDate");
                String address = request.getParameter("address");
                String customerPassword = request.getParameter("customerPassword");
                if (customerPassword != null && !customerPassword.trim().isEmpty()) {
                    // Hash lại mật khẩu nếu admin thay đổi
                    customer.setCustomerPassword(customerDAO.hashMD5(customerPassword.trim()));
                }

                if (cccd != null && !cccd.trim().isEmpty()) {
                    customer.setCccd(cccd.trim());
                }
                if (gender != null && !gender.trim().isEmpty()) {
                    customer.setGender(gender.trim());
                }
                if (birthDate != null && !birthDate.trim().isEmpty()) {
                    try {
                        // Chuyển đổi String thành Date
                        Date sqlDate = Date.valueOf(birthDate.trim()); // Định dạng: yyyy-MM-dd
                        customer.setBirthDate(sqlDate);
                    } catch (IllegalArgumentException e) {
                        // Xử lý lỗi định dạng ngày
                        System.out.println("Định dạng ngày không hợp lệ: " + birthDate);
                    }
                }
                if (address != null && !address.trim().isEmpty()) {
                    customer.setAddress(address.trim());
                }

                boolean success = customerDAO.updateCustomer(customer);
                if (!success) {
                    request.setAttribute("error", "Failed to update customer.");
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                    return;
                }
            }

            response.sendRedirect(request.getContextPath() + "/viewListAccount");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
