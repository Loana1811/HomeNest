<%-- 
    Document   : edit_room
    Created on : Jun 14, 2025, 2:12:43 PM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Room, model.Block, model.Category" %>
<%@ page import="java.util.List" %>
<%
    Room room = (Room) request.getAttribute("room");
    List<Block> blockList = (List<Block>) request.getAttribute("blockList");
    List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Room</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            :root {
                --teal: rgb(0, 128, 128);
            }
            .bg-teal {
                background-color: var(--teal) !important;
            }
            .btn-teal {
                background-color: var(--teal);
                color: white;
            }
            .btn-teal:hover {
                background-color: #006666;
                color: white;
            }
            .form-check-label {
                font-size: 14px;
            }
        </style>
    </head>
    <body class="bg-light">

        <div class="container mt-5">
            <div class="card shadow-sm">
                <div class="card-header bg-teal text-white">
                    <h4 class="mb-0">Edit Room</h4>
                </div>
                <div class="card-body">
                    <form action="rooms" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="update" />
                        <input type="hidden" name="id" value="<%= room.getRoomID()%>" />

                        <!-- Room Number -->
                        <div class="mb-3">
                            <label class="form-label">Room Number:</label>
                            <input type="text" class="form-control" name="roomNumber" value="<%= room.getRoomNumber()%>" required>
                        </div>

                        <!-- Rent Price -->
                        <div class="mb-3">
                            <label class="form-label">Rent Price (VND):</label>
                            <input type="number" class="form-control" name="rentPrice" step="1000" value="<%= room.getRentPrice()%>" required>
                        </div>

                        <!-- Area -->
                        <div class="mb-3">
                            <label class="form-label">Area (m²):</label>
                            <input type="number" class="form-control" name="area" step="0.1"
                                   value="<%= room.getArea() != null ? room.getArea() : ""%>">
                        </div>

                        <!-- Status -->
                        <div class="mb-3">
                            <label class="form-label">Status:</label>
                            <select class="form-select" name="status" required>
                                <option value="Available" <%= "Available".equals(room.getStatus()) ? "selected" : ""%>>Available</option>
                                <option value="Occupied" <%= "Occupied".equals(room.getStatus()) ? "selected" : ""%>>Occupied</option>
                            </select>
                        </div>

                        <!-- Block -->
                        <div class="mb-3">
                            <label class="form-label">Block:</label>
                            <select class="form-select" name="blockID">
                                <option value="">-- Select Block --</option>
                                <% for (Block b : blockList) {%>
                                <option value="<%= b.getBlockID()%>" <%= room.getBlockID() != null && room.getBlockID().equals(b.getBlockID()) ? "selected" : ""%>>
                                    <%= b.getBlockName()%>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Category -->
                        <div class="mb-3">
                            <label class="form-label">Category:</label>
                            <select class="form-select" name="categoryID">
                                <option value="">-- Select Category --</option>
                                <% for (Category c : categoryList) {%>
                                <option value="<%= c.getCategoryID()%>" <%= room.getCategoryID() != null && room.getCategoryID().equals(c.getCategoryID()) ? "selected" : ""%>>
                                    <%= c.getCategoryName()%>
                                </option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Highlights -->
                        <div class="mb-3">
                            <label class="form-label">Highlights:</label>
                            <div class="row">
                                <%
                                    String[] options = {"Free drinking water", "Coin laundry", "Motorbike parking", "Elevator access", "Security camera", "High-speed wifi", "Drying area", "Daily cleaning"};
                                    String currentHighlights = room.getHighlights() != null ? room.getHighlights() : "";
                                    for (String opt : options) {
                                %>
                                <div class="col-md-6">
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" name="highlights" value="<%= opt%>"
                                               <%= currentHighlights.contains(opt) ? "checked" : ""%>>
                                        <label class="form-check-label"><%= opt%></label>
                                    </div>
                                </div>
                                <% } %>
                            </div>
                        </div>

                        <!-- Image -->
                        <div class="mb-3">
                            <label class="form-label">Room Image:</label>
                            <input class="form-control" type="file" name="image" accept="image/*" />
                            <% if (room.getImagePath() != null && !room.getImagePath().isEmpty()) {%>
                            <div class="mt-2">
                                <img src="<%= room.getImagePath()%>" alt="Current Image" style="max-height: 120px; border-radius: 8px;">
                            </div>
                            <% }%>
                        </div>

                        <!-- Description -->
                        <div class="mb-3">
                            <label class="form-label">Description:</label>
                            <textarea class="form-control" name="description" rows="4"><%= room.getDescription() != null ? room.getDescription() : ""%></textarea>
                        </div>

                        <!-- Submit -->
                        <button type="submit" class="btn btn-teal">Update Room</button>
                    </form>
                </div>
            </div>
        </div>

    </body>
</html>


