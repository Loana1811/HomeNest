<%-- 
    Document   : list_blocks
    Created on : Jun 16, 2025, 10:56:24 AM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Block" %>
<%
    List<Block> blockList = (List<Block>) request.getAttribute("blockList");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Block Management</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
        <style>
            :root {
                --teal: rgb(0, 128, 128);
                --light-bg: #f5f7fa;
                --highlight-bg: #e0f7f7;
            }

            body {
                background-color: var(--light-bg);
                font-family: 'Segoe UI', sans-serif;
            }

            .sidebar {
                width: 260px;
                height: 100vh;
                position: fixed;
                top: 0;
                left: 0;
                background-color: var(--teal);
                color: white;
                padding: 2rem 1rem;
            }

            .sidebar h4 {
                font-weight: bold;
                margin-bottom: 2rem;
            }

            .sidebar a {
                color: white;
                text-decoration: none;
                display: block;
                margin: 1rem 0;
                font-weight: 500;
            }

            .sidebar a:hover {
                text-decoration: underline;
            }

            .main {
                margin-left: 270px;
                padding: 2rem;
            }

            .btn-teal {
                background-color: rgb(0, 128, 128);
                color: white;
                border-radius: 30px;
            }
            .btn-teal:hover {
                background-color: #006666;
                color: white;
            }

            .card-header {
                background-color: var(--highlight-bg);
                font-weight: 600;
                font-size: 18px;
            }

            .badge-full {
                background-color: #dc3545;
            }

            .badge-available {
                background-color: #198754;
            }

            .bi {
                margin-right: 5px;
            }

            @media (max-width: 768px) {
                .sidebar {
                    display: none;
                }

                .main {
                    margin-left: 0;
                    padding: 1rem;
                }
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <h4>ADMIN</h4>
            <a href="rooms?action=list"><i class="bi bi-layout-text-window"></i> Manage Rooms</a>
            <a href="blocks?action=list"><i class="bi bi-building"></i> Manage Blocks</a>
        </div>
        <!-- Main content -->
        <div class="main">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="text-dark"> Block Management</h2>
                <div>
                    <a href="rooms?action=new" class="btn btn-teal me-2">
                        <i class="bi bi-plus-circle"></i> Add Room
                    </a>
                    <a href="blocks?action=new" class="btn btn-outline-dark">
                        <i class="bi bi-building-add"></i> Add Block
                    </a>
                </div>
            </div>

            <div class="card shadow-sm">
                <div class="card-header">Block List</div>
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th><i class="bi bi-building"></i> Block</th>
                                <th><i class="bi bi-grid-3x3-gap"></i> Room Count</th>
                                <th><i class="bi bi-bar-chart-line"></i> Max Rooms</th>
                                <th><i class="bi bi-info-circle"></i> Status</th>
                                <th class="text-center"><i class="bi bi-tools"></i> Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (blockList != null && !blockList.isEmpty()) {
                                    for (Block b : blockList) {%>
                            <tr>
                                <td><%= b.getBlockName()%></td>
                                <td><%= b.getRoomCount()%></td>
                                <td><%= b.getMaxRooms()%></td>
                                <td>
                                    <span class="badge <%= b.getStatus().equalsIgnoreCase("Full") ? "badge-full" : "badge-available"%>">
                                        <%= b.getStatus()%>
                                    </span>
                                </td>
                                <td class="text-center">
                                    <a href="blocks?action=edit&id=<%= b.getBlockID()%>" class="btn btn-warning btn-sm">
                                        <i class="bi bi-pencil"></i> Edit
                                    </a>
                                    <%-- Xoá sẽ được bật lại nếu cần dùng
                                    <a href="blocks?action=delete&id=<%= b.getBlockID() %>" class="btn btn-danger btn-sm"
                                       onclick="return confirm('Are you sure you want to delete this block?')">
                                        <i class="bi bi-trash"></i> Delete
                                    </a>
                                    --%>
                                </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr>
                                <td colspan="5" class="text-center text-muted py-4">No blocks found.</td>
                            </tr>
                            <% }%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

