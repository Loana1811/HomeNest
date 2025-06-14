<%@page import="Model.Contract"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    List<Contract> contracts = (List<Contract>) request.getAttribute("contracts");
    Integer currentPageObj = (Integer) request.getAttribute("currentPage");
    Integer totalPagesObj = (Integer) request.getAttribute("totalPages");

    int currentPage = (currentPageObj != null) ? currentPageObj : 1;
    int totalPages = (totalPagesObj != null) ? totalPagesObj : 1;
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Contract List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .container {
                margin-top: 40px;
            }

            .table-container {
                background: #fff;
                padding: 25px;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0,0,0,0.05);
            }

            .table thead {
                background-color: #008080;
                color: white;
            }

            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }

            .btn-teal {
                background-color: #008080;
                color: #fff;
                border: none;
                padding: 6px 12px;
                border-radius: 5px;
                font-size: 13px;
            }

            .btn-teal:hover {
                background-color: #006666;
            }

            .pagination {
                margin-top: 20px;
                text-align: center;
            }

            .pagination a {
                padding: 8px 14px;
                margin: 0 4px;
                text-decoration: none;
                background-color: #008080;
                color: white;
                font-weight: bold;
                border-radius: 5px;
            }

            .pagination a:hover, .pagination a.active {
                background-color: #006666;
            }

            .no-data {
                text-align: center;
                font-size: 18px;
                color: #e74c3c;
                margin-top: 20px;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h2 class="text-center mb-4">Contract List</h2>
            <div class="table-container">
                <% if (contracts != null && !contracts.isEmpty()) { %>
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>Contract ID</th>
                            <th>Tenant ID</th>
                            <th>Room ID</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Contract contract : contracts) {%>
                        <tr>
                            <td><%= contract.getContractId()%></td>
                            <td><%= contract.getTenantId()%></td>
                            <td><%= contract.getRoomId()%></td>
                            <td><%= contract.getStartDate()%></td>
                            <td><%= contract.getEndDate()%></td>
                            <td><%= contract.getStatus()%></td>
                            <td><%= contract.getCreatedAt()%></td>
                            <td>
                                <a href="<%= request.getContextPath() %>/contracts?action=show&id=<%= contract.getContractId() %>" class="btn btn-teal btn-sm mb-1">View</a>
                            <a href="<%= request.getContextPath() %>/contracts?action=edit&id=<%= contract.getContractId() %>" class="btn btn-teal btn-sm mb-1">Edit</a>
                            <a href="<%= request.getContextPath() %>/contracts?action=delete&id=<%= contract.getContractId() %>" class="btn btn-teal btn-sm mb-1" onclick="return confirm('Confirm delete?')">Delete</a>
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>

                <div class="pagination">
                    <% if (totalPages > 1) { %>
                    <% if (currentPage > 1) {%>
                    <a href="<%= request.getContextPath()%>/contracts?page=<%= currentPage - 1%>">« Prev</a>
                    <% } %>
                    <% for (int i = 1; i <= totalPages; i++) {%>
                    <a href="<%= request.getContextPath()%>/contracts?page=<%= i%>" class="<%= (i == currentPage) ? "active" : ""%>"><%= i%></a>
                    <% } %>
                    <% if (currentPage < totalPages) {%>
                    <a href="<%= request.getContextPath()%>/contracts?page=<%= currentPage + 1%>">Next »</a>
                    <% } %>
                    <% } %>
                </div>
                <% } else { %>
                <p class="no-data">No contracts to display.</p>
                <% }%>
            </div>

            <div class="text-center mt-3">
                <a href="<%= request.getContextPath()%>/contracts?action=create" class="btn btn-teal">+ Create New Contract</a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
