<%-- 
    Document   : list_rooms
    Created on : Jun 14, 2025, 4:24:44 PM
    Author     : Admin
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Room, model.Block" %>
<%
    List<Room> list = (List<Room>) request.getAttribute("list");
    List<Block> blocks = (List<Block>) request.getAttribute("blockList");
    String selectedBlockID = (String) request.getAttribute("blockID");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Room Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">s
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
                background-color: var(--teal);
                color: white;
                position: fixed;
                top: 0;
                left: 0;
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
                background-color: var(--teal);
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

            .room-img {
                width: 70px;
                height: 70px;
                object-fit: cover;
                border-radius: 12px;
                border: 2px solid #ddd;
            }

            .badge-available {
                background-color: #17c964;
                font-size: 0.85rem;
                border-radius: 1rem;
                padding: 0.4em 1em;
            }

            .badge-occupied {
                background-color: #adb5bd;
                font-size: 0.85rem;
                border-radius: 1rem;
                padding: 0.4em 1em;
            }

            .table td, .table th {
                vertical-align: middle;
            }

            .action-buttons .btn {
                margin-right: 0.5rem;
            }

            .bi {
                margin-right: 5px;
            }

            @media (max-width: 768px) {
                .main {
                    margin-left: 0;
                    padding: 1rem;
                }

                .sidebar {
                    display: none;
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

        <!-- Main Content -->
        <div class="main">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="text-dark"> Room Management</h2>
                <div>
                    <a href="rooms?action=new" class="btn btn-teal me-2"><i class="bi bi-plus-circle"></i> Add Room</a>
                    <a href="blocks?action=new" class="btn btn-outline-dark"><i class="bi bi-building-add"></i> Add Block</a>
                </div>
            </div>

            <!-- Filter -->
            <form method="get" action="rooms" class="bg-white p-3 rounded shadow-sm mb-4">
                <input type="hidden" name="action" value="list" />
                <div class="row g-3 align-items-end">
                    <div class="col-md-4">
                        <label class="form-label">Block</label>
                        <select name="blockID" class="form-select">
                            <option value="">All Blocks</option>
                            <% for (Block b : blocks) {%>
                            <option value="<%= b.getBlockID()%>" <%= String.valueOf(b.getBlockID()).equals(selectedBlockID) ? "selected" : ""%>>
                                <%= b.getBlockName()%>
                            </option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col-md-4 d-grid">
                        <button type="submit" class="btn btn-teal">
                            <i class="bi bi-funnel"></i> Filter
                        </button>
                    </div>

                </div>
            </form>

            <!-- Room Table -->
            <div class="card shadow-sm">
                <div class="card-header">Room List</div>
                <div class="card-body p-0">
                    <table class="table table-hover align-middle mb-0">
                        <thead class="table-light">
                            <tr>
                                <th>Image</th>
                                <th><i class="bi bi-door-closed"></i> Room</th>
                                <th><i class="bi bi-cash-stack"></i> Price</th>
                                <th><i class="bi bi-stars"></i> Highlights</th>
                                <th><i class="bi bi-info-circle"></i> Status</th>
                                <th class="text-center"><i class="bi bi-tools"></i>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (list != null && !list.isEmpty()) {
                                    for (Room r : list) { %>
                            <tr>
                                <td>
                                    <% if (r.getImagePath() != null && !r.getImagePath().isEmpty()) {%>
                                    <img src="<%= r.getImagePath()%>" alt="Room Image" class="room-img">
                                    <% } else { %>
                                    <span class="text-muted">No image</span>
                                    <% }%>
                                </td>
                                <td><strong><%= r.getRoomNumber()%></strong></td>
                                <td><%= String.format("%,.0f VND", r.getRentPrice())%></td>
                                <td><%= r.getHighlights() != null ? r.getHighlights() : "-"%></td>
                                <td>
                                    <span class="badge <%= "Available".equalsIgnoreCase(r.getStatus()) ? "badge-available" : "badge-occupied"%>">
                                        <i class="bi <%= "Available".equalsIgnoreCase(r.getStatus()) ? "bi-check-circle" : "bi-x-circle"%>"></i>
                                        <%= r.getStatus()%>
                                    </span>
                                </td>
                                <td class="text-center action-buttons">
                                    <a href="rooms?action=view&id=<%= r.getRoomID()%>" class="btn btn-sm btn-info">View</a>
                                    <a href="rooms?action=edit&id=<%= r.getRoomID()%>" class="btn btn-sm btn-outline-primary">Edit</a>
                                    <!-- Button trigger modal -->
                                    <button type="button" class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteModal<%= r.getRoomID()%>">
                                        Delete
                                    </button>

                                    <!-- Modal -->
                                    <div class="modal fade" id="deleteModal<%= r.getRoomID()%>" tabindex="-1" aria-labelledby="deleteModalLabel<%= r.getRoomID()%>" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered">
                                            <div class="modal-content">
                                                <div class="modal-header bg-danger text-white">
                                                    <h5 class="modal-title" id="deleteModalLabel<%= r.getRoomID()%>">Confirm Delete</h5>
                                                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    Are you sure you want to delete room <strong><%= r.getRoomNumber()%></strong>?
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                    <a href="rooms?action=delete&id=<%= r.getRoomID()%>" class="btn btn-danger">Yes, Delete</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr>
                                <td colspan="6" class="text-center text-muted py-4">No rooms found.</td>
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