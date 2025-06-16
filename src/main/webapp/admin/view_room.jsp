<%-- 
    Document   : view_room
    Created on : Jun 16, 2025, 4:11:37 PM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Room, model.Block, model.Category" %>
<%
    Room room = (Room) request.getAttribute("room");
    Block block = (Block) request.getAttribute("block");
    Category category = (Category) request.getAttribute("category");
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Room Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            :root {
                --teal: rgb(0, 128, 128);
                --light-bg: #f5f7fa;
                --highlight: #e0f7f7;
            }

            body {
                background-color: var(--light-bg);
                font-family: 'Segoe UI', sans-serif;
            }

            .sidebar {
                width: 250px;
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

            .card {
                max-width: 900px;
                margin: auto;
                border-radius: 1rem;
                overflow: hidden;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            .card-header {
                background-color: var(--teal);
                color: white;
                font-size: 30px;
                font-weight: bold;
                padding: 1.25rem;
            }

            .room-img {
                width: 100%;
                height: 350px;
                object-fit: cover;
                background-color: #ddd;
            }

            .card-body {
                background-color: white;
                padding: 2rem;
            }

            .label {
                font-weight: bold;
                color: var(--teal);
            }

            .back-btn {
                background-color: var(--teal);
                color: white;
                border-radius: 30px;
                padding: 10px 25px;
                font-weight: 500;
            }

            .back-btn:hover {
                background-color: #006666;
            }

            @media (max-width: 768px) {
                .main {
                    margin-left: 0;
                    padding: 1rem;
                }

                .sidebar {
                    display: none;
                }

                .card {
                    margin-top: 1rem;
                }
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <h4>ADMIN</h4>
            <a href="rooms?action=list"><i class="bi bi-layout-text-window"></i> Manage Room</a>
            <a href="blocks?action=list"><i class="bi bi-building"></i> Manage Blocks</a>
        </div>

        <!-- Main -->
        <div class="main">
            <div class="card">
                <% if (room.getImagePath() != null && !room.getImagePath().isEmpty()) {%>
                <img src="<%= room.getImagePath()%>" alt="Room Image" class="room-img">
                <% } else { %>
                <div class="room-img d-flex align-items-center justify-content-center text-muted">
                    No image available
                </div>
                <% }%>

                <div class="card-header">
                    <%= room.getRoomNumber()%>
                </div>

                <div class="card-body">
<!--                    <p><span class="label">Room ID:</span> <%= room.getRoomID()%></p>-->
                    <p><span class="label">Rent Price:</span> <%= String.format("%,.0f VND", room.getRentPrice())%></p>
                    <p><span class="label">Area:</span> <%= room.getArea() != null ? room.getArea() + " m²" : "N/A"%></p>
                    <p><span class="label">Status:</span> <%= room.getStatus()%></p>
                    <p><span class="label">Block:</span> <%= block != null ? block.getBlockName() : "N/A"%></p>
                    <p><span class="label">Category:</span> <%= category != null ? category.getCategoryName() : "N/A"%></p>
                    <p><span class="label">Highlights:</span> <%= room.getHighlights() != null ? room.getHighlights() : "None"%></p>
                    <p><span class="label">Description:</span> <%= room.getDescription() != null ? room.getDescription() : "None"%></p>
                    <p><span class="label">Posted Date:</span> <%= room.getPostedDate() != null ? room.getPostedDate().toString() : "N/A"%></p>

                    <div class="mt-4">
                        <a href="rooms?action=list" class="btn back-btn">← Back to Room List</a>
                    </div>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>

