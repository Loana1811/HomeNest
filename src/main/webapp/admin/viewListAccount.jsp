<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Customer" %>
<%@page import="model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Account Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            :root {
                --primary: #3498db;
                --secondary: #2c3e50;
                --success: #28a745;
                --info: #17a2b8;
                --warning: #ffc107;
                --danger: #dc3545;
                --light: #f8f9fa;
                --dark: #343a40;
            }
            body {
                background-color: #f5f7fa;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .header {
                background: linear-gradient(135deg, var(--primary), var(--secondary));
                color: white;
                padding: 2rem 0;
                margin-bottom: 2rem;
                border-radius: 0 0 20px 20px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 20px;
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            }
            .create-buttons {
                text-align: center;
                margin: 20px 0;
            }
            .btn {
                padding: 10px 20px;
                margin: 0 10px;
                border-radius: 5px;
                font-weight: bold;
                transition: background-color 0.3s;
            }
            .btn-create {
                background: linear-gradient(135deg, var(--success), #1e7e34);
                color: white;
            }
            .btn-create:hover {
                background: linear-gradient(135deg, #1e7e34, var(--success));
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
            }
            th, td {
                border: 1px solid #ddd;
                padding: 12px;
                text-align: left;
            }
            th {
                background-color: var(--primary);
                color: white;
                font-weight: bold;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9;
            }
            tr:hover {
                background-color: rgba(52, 152, 219, 0.1);
            }
            .status-badge {
                padding: 0.4em 0.8em;
                border-radius: 20px;
                font-size: 0.85em;
                font-weight: 500;
            }
            .status-active {
                background-color: rgba(40, 167, 69, 0.15);
                color: var(--success);
            }
            .status-inactive {
                background-color: rgba(220, 53, 69, 0.15);
                color: var(--danger);
            }
            .status-potential {
                background-color: rgba(255, 193, 7, 0.15);
                color: var(--warning);
            }
            .status-converted {
                background-color: rgba(23, 162, 184, 0.15);
                color: var(--info);
            }
            .empty-state {
                text-align: center;
                padding: 3rem;
                color: #6c757d;
            }
            .empty-state i {
                font-size: 5rem;
                margin-bottom: 1rem;
                color: #dee2e6;
            }
            .dashboard-stats {
                display: flex;
                gap: 32px;
                justify-content: flex-start;
                margin-bottom: 32px;
            }

            .stat-card {
                background: #fff;
                border-radius: 18px;
                box-shadow: 0 2px 24px #a9b8c822;
                min-width: 210px;
                padding: 28px 24px 20px 24px;
                display: flex;
                flex-direction: column;
                align-items: flex-start;
                transition: transform 0.13s, box-shadow 0.15s;
                border: none;
            }
            .stat-card:hover {
                transform: translateY(-5px) scale(1.03);
                box-shadow: 0 8px 32px #2996da19, 0 2px 12px #b4c6f733;
            }

            .stat-icon {
                display: flex;
                align-items: center;
                justify-content: center;
                border-radius: 12px;
                width: 52px;
                height: 52px;
                font-size: 2rem;
                margin-bottom: 18px;
            }
            .stat-blue {
                background: #e1f1fb;
                color: #2596e0;
            }
            .stat-green {
                background: #e2f9ea;
                color: #1fb865;
            }
            .stat-grey {
                background: #e7e9ed;
                color: #75797d;
            }

            .stat-number {
                font-size: 2rem;
                font-weight: 700;
                color: #222c23;
                margin-bottom: 3px;
                margin-left: 4px;
            }

            .stat-label {
                font-size: 1.08rem;
                color: #868e96;
                font-weight: 500;
                margin-left: 4px;
                letter-spacing: 0.2px;
            }

            @media (max-width: 900px) {
                .dashboard-stats {
                    flex-wrap: wrap;
                    gap: 18px;
                }
                .stat-card {
                    min-width: 140px;
                    padding: 16px 7px 10px 14px;
                }
                .stat-icon {
                    width: 42px;
                    height: 42px;
                    font-size: 1.5rem;
                    margin-bottom: 11px;
                }
                .stat-number {
                    font-size: 1.28rem;
                }
            }
.dashboard-search {
    display: flex;
    align-items: center;
    gap: 8px;
    margin-bottom: 28px;
    margin-left: 2px;
}

.search-input {
    padding: 10px 16px;
    border-radius: 8px 0 0 8px;
    border: 1.5px solid #22b573;
    outline: none;
    font-size: 1.08rem;
    width: 240px;
    transition: border 0.22s, box-shadow 0.15s;
    background: #f6fbf7;
    color: #222c23;
}

.search-input:focus {
    border-color: #17994a;
    box-shadow: 0 2px 12px #22b57333;
}

.search-btn {
    padding: 0 20px;
    height: 42px;
    background: linear-gradient(90deg, #22b573, #17994a);
    color: #fff;
    border: none;
    border-radius: 0 8px 8px 0;
    font-size: 1.16rem;
    cursor: pointer;
    transition: background 0.17s, box-shadow 0.15s;
    box-shadow: 0 1px 5px #22b57333;
    display: flex;
    align-items: center;
    justify-content: center;
}
.search-btn:hover, .search-btn:focus {
    background: linear-gradient(90deg, #17994a, #22b573);
    box-shadow: 0 2px 12px #22b57344;
    color: #eafff2;
}

        </style>
    </head>
    <body>
        <div class="container">
            <!-- Header -->
            <div class="header">
                <h1>Account Management</h1>
            </div>
            <div class="dashboard-stats">
                <div class="stat-card">
                    <div class="stat-icon stat-green">
                        <i class="fas fa-user-tie"></i>
                    </div>
                    <div class="stat-number">${totalCustomers}</div>
                    <div class="stat-label">Total Customers</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon stat-blue">
                        <i class="fas fa-user-check"></i>
                    </div>
                    <div class="stat-number">${convertedCustomers}</div>
                    <div class="stat-label">Converted</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon stat-yellow">
                        <i class="fas fa-user-plus"></i>
                    </div>
                    <div class="stat-number">${potentialCustomers}</div>
                    <div class="stat-label">Potential</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon stat-grey">
                        <i class="fas fa-user-slash"></i>
                    </div>
                    <div class="stat-number">${inactiveCustomers}</div>
                    <div class="stat-label">Inactive</div>
                </div>
            </div>

            <!-- Create Buttons -->
            <div class="create-buttons">
                <a href="${pageContext.request.contextPath}/admin/account?action=create" class="btn btn-create text-white">
                    <i class="fas fa-user-plus me-2"></i>Create New Account
                </a>
            </div>


            <!-- Users Table -->
            <h2 class="section-title">System Users List</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone Number</th>
                        <th>Role</th>
                        <th>Block</th>
                        <th>Status</th>
                        <th>Created Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty userList}">
                            <c:forEach items="${userList}" var="user">
                                <tr>
                                    <td>${user.userID}</td>
                                    <td>${user.fullName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>${user.role.roleName}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.role.roleID == 2}">
                                                ${user.block != null ? user.block.blockName : "N/A"}
                                            </c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.status eq 'Active'}">
                                                <span class="status-badge status-active">Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-inactive">Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${user.createdAt}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/editUser?userID=${user.userID}" class="btn btn-sm btn-outline-success">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <button type="button" class="btn btn-sm btn-outline-warning" onclick="showDisableUserModal(${user.userID}, '${user.fullName}')">
                                            <i class="fas fa-user-slash"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="9" class="no-data">No user data available</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>

            <!-- Customers Table -->
            <h2 class="section-title">Customers List</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Full Name</th>
                        <th>Phone Number</th>
                        <th>Email</th>
                        <th>ID Card</th>
                        <th>Gender</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty customerList}">
                            <c:forEach items="${customerList}" var="customer">
                                <tr>
                                    <td>${customer.customerID}</td>
                                    <td>${customer.fullName}</td>
                                    <td>${customer.phoneNumber}</td>
                                    <td>${customer.email}</td>
                                    <td>${customer.cccd}</td>
                                    <td>${customer.gender}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${customer.status eq 'Potential'}">
                                                <span class="status-badge status-potential">Potential</span>
                                            </c:when>
                                            <c:when test="${customer.status eq 'Converted'}">
                                                <span class="status-badge status-converted">Converted</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-inactive">${customer.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/editCustomer?customerID=${customer.customerID}" class="btn btn-sm btn-outline-success">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/account?action=viewDetail&customerID=${customer.customerID}" class="btn btn-info btn-sm">
                                            <i class="fas fa-eye"></i> ViewDetails
                                        </a>
                                        <button type="button" class="btn btn-sm btn-outline-warning" onclick="showDisableCustomerModal(${customer.customerID}, '${customer.fullName}')">
                                            <i class="fas fa-user-slash"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="8" class="no-data">No customer data available</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <!-- Modal Disable User -->
        <div class="modal fade" id="disableUserModal" tabindex="-1" aria-labelledby="disableUserLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form id="disableUserForm" method="post">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="disableUserLabel">Disable User</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="userID" id="disableUserID">
                            <div class="mb-2">
                                <label><b>User:</b> <span id="disableUserName"></span></label>
                            </div>
                            <div class="mb-2">
                                <label for="disableUserReason" class="form-label">Reason for disabling</label>
                                <textarea class="form-control" id="disableUserReason" name="reason" rows="3" required></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-warning">Disable</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <!-- Modal Disable Customer -->
        <div class="modal fade" id="disableCustomerModal" tabindex="-1" aria-labelledby="disableCustomerLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form id="disableCustomerForm" method="post">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="disableCustomerLabel">Disable Customer</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <input type="hidden" name="customerID" id="disableCustomerID">
                            <div class="mb-2">
                                <label><b>Customer:</b> <span id="disableCustomerName"></span></label>
                            </div>
                            <div class="mb-2">
                                <label for="disableCustomerReason" class="form-label">Reason for disabling</label>
                                <textarea class="form-control" id="disableCustomerReason" name="reason" rows="3" required></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" class="btn btn-warning">Disable</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                                            document.addEventListener('DOMContentLoaded', function () {
                                                // User Modal
                                                function showDisableUserModal(userID, fullName) {
                                                    document.getElementById('disableUserID').value = userID;
                                                    document.getElementById('disableUserName').innerText = fullName;
                                                    document.getElementById('disableUserReason').value = '';

                                                    // SET ACTION CHO FORM
                                                    document.getElementById('disableUserForm').action =
                                                            window.location.origin + '${pageContext.request.contextPath}/admin/account?action=disable';

                                                    var modal = new bootstrap.Modal(document.getElementById('disableUserModal'));
                                                    modal.show();
                                                }
                                                window.showDisableUserModal = showDisableUserModal; // Đảm bảo hàm global

                                                var userForm = document.getElementById('disableUserForm');
                                                if (userForm) {
                                                    userForm.onsubmit = function (e) {
                                                        e.preventDefault();
                                                        var form = e.target;
                                                        var data = new FormData(form);

                                                        fetch(form.action, {
                                                            method: 'POST',
                                                            body: data
                                                        }).then(res => {
                                                            if (res.redirected) {
                                                                window.location.href = res.url;
                                                            } else {
                                                                location.reload();
                                                            }
                                                        }).catch(error => {
                                                            console.error('Error:', error);
                                                            alert('Có lỗi xảy ra khi vô hiệu hóa!');
                                                        });
                                                    };
                                                }

                                                // Customer Modal
                                                function showDisableCustomerModal(customerID, fullName) {
                                                    document.getElementById('disableCustomerID').value = customerID;
                                                    document.getElementById('disableCustomerName').innerText = fullName;
                                                    document.getElementById('disableCustomerReason').value = '';

                                                    // SET ACTION CHO FORM
                                                    document.getElementById('disableCustomerForm').action =
                                                            window.location.origin + '${pageContext.request.contextPath}/admin/account?action=disable';

                                                    var modal = new bootstrap.Modal(document.getElementById('disableCustomerModal'));
                                                    modal.show();
                                                }
                                                window.showDisableCustomerModal = showDisableCustomerModal; // Đảm bảo hàm global

                                                var customerForm = document.getElementById('disableCustomerForm');
                                                if (customerForm) {
                                                    customerForm.onsubmit = function (e) {
                                                        e.preventDefault();
                                                        var form = e.target;
                                                        var data = new FormData(form);

                                                        fetch(form.action, {
                                                            method: 'POST',
                                                            body: data
                                                        }).then(res => {
                                                            if (res.redirected) {
                                                                window.location.href = res.url;
                                                            } else {
                                                                location.reload();
                                                            }
                                                        }).catch(error => {
                                                            console.error('Error:', error);
                                                            alert('Có lỗi xảy ra khi vô hiệu hóa!');
                                                        });
                                                    };
                                                }
                                            });



        </script>
    </body>
</html>
