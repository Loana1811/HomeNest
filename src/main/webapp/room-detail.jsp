<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Room" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<%
    Room room = (Room) request.getAttribute("room");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    String postDateStr = (room != null && room.getPostedDate() != null)
                         ? sdf.format(room.getPostedDate())
                         : "N/A";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Room Detail</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <style>
            .room-image {
                width: 100%;
                max-width: 720px;
                height: auto;
                display: block;
                margin: 0 auto;
                border-radius: 10px;
                box-shadow: 0 4px 16px rgba(0,0,0,0.1);
            }

            .room-card {
                border: none;
                box-shadow: 0 4px 20px rgba(0,0,0,0.15);
                border-radius: 15px;
                overflow: hidden;
            }

            .room-info p {
                display: flex;
                align-items: baseline;
                gap: 0.75rem;
                font-size: 1.3rem;
                margin-bottom: 1rem;
                line-height: 1.6;
            }

            .room-info p strong {
                width: 110px;
                display: inline-block;
                font-weight: 700;
                color: #222;
                font-size: 1.35rem;
            }

            .room-price {
                font-size: 1.8rem;
                color: #ff6600;
                font-weight: bold;
                margin-top: 1rem;
            }

            .room-description {
                font-size: 1.25rem;
                padding: 1rem;
                background-color: #fdfdfd;
                border: 1px solid #ddd;
                border-radius: 8px;
                margin-top: 1rem;
                white-space: pre-line;
            }
            .contact-card, .highlight-card {
                font-size: 1.2rem;
                padding: 2rem;
                border-radius: 16px;
                box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            }

            .contact-card h5,
            .highlight-card h6 {
                font-size: 1.45rem;
                font-weight: 700;
                margin-bottom: 1.5rem;
            }

            .contact-avatar {
                width: 80px;
                height: 80px;
                border-radius: 50%;
                background-color: #bbb;
            }

            .contact-card .btn {
                font-size: 1.15rem;
                padding: 12px;
                font-weight: 600;
            }

            .highlight-card p {
                font-size: 1.15rem;
            }

            .highlight-card small {
                font-size: 1rem;
                color: #555;
            }


        </style>
    </head>
    <body class="d-flex flex-column min-vh-100">

        <main class="container my-5 flex-grow-1">
            <% if (room != null) { %>
            <%
    List<Room> featuredRooms = (List<Room>) request.getAttribute("featuredRooms");
            %>
            <div class="row">
                <!-- Cột trái: thông tin phòng -->
                <div class="col-md-8">
                    <div class="card room-card p-4 mb-4">
                        <div class="text-center mb-4">
                            <img src="images/rooms/<%= room.getImageName() != null ? room.getImageName() : "room-default.jpg" %>"
                                 alt="Room Image"
                                 class="room-image"
                                 onerror="this.onerror=null;this.src='images/rooms/room-default.jpg';" />
                        </div>

                        <div class="room-info">
                            <h3 class="fw-bold text-center mb-4">Room: <%= room.getRoomNumber() %></h3>
                            <p><strong>Type:</strong> <%= room.getRoomType() %></p>
                            <p><strong>Area:</strong> <%= room.getArea() %> m²</p>
                            <p><strong>Status:</strong> <%= room.getStatus() %></p>
                            <p><strong>Location:</strong> <%= room.getLocation() %></p>
                            <p><strong>Block:</strong> <%= room.getBlockName() %></p>
                            <p><strong>Post:</strong> <%= postDateStr %></p>
                            <p class="room-price"><strong>Price:</strong> <%= String.format("%,.0f", room.getRentPrice()) %> ₫ / month</p>
                        </div>

                        <p style="font-size: 1.35rem; font-weight: 700;"><strong>Description:</strong></p>
                        <div class="room-description">
                            <%= room.getDescription() != null ? room.getDescription() : "No description available." %>
                        </div>
                    </div>
                </div>

                <!-- Right Column -->
                <div class="col-md-4">
                    <!-- Contact Section -->
                    <div class="card contact-card mb-4">
                        <h5 class="fw-bold mb-3">Contact</h5>
                        <div class="d-flex align-items-center mb-3">
                            <div class="contact-avatar me-3"></div>
                            <div>
                                <p class="mb-0 fw-bold">HomeNest</p>
                                <p class="mb-0 text-muted small">Member since: 01/01/2024</p>
                            </div>
                        </div>
                        <a href="tel:0909123456" class="btn btn-success w-100 mb-2">
                            <i class="bi bi-telephone-fill me-2"></i>0909 123 456
                        </a>
                        <a href="#" class="btn btn-primary w-100">
                            <i class="bi bi-chat-dots me-2"></i>Message on Zalo
                        </a>
                    </div>

                    <!-- Featured Listings -->
                    <div class="card highlight-card">
                        <h6 class="fw-bold mb-3">Featured Listings</h6>

                        <% for (Room r : featuredRooms) { %>
                        <div class="d-flex mb-3 border-bottom pb-2">
                            <img src="images/rooms/<%= r.getImageName() != null ? r.getImageName() : "room-default.jpg" %>"
                                 alt="thumbnail"
                                 class="rounded"
                                 style="width: 64px; height: 64px; object-fit: cover; margin-right: 10px;">
                            <div>
                                <a href="room-detail?id=<%= r.getRoomID() %>" class="fw-semibold text-danger text-decoration-none d-block">
                                    Room <%= r.getRoomNumber() %> - <%= r.getRoomType() %>
                                </a>
                                <small><%= String.format("%,.1f", r.getRentPrice()/1000000) %> mil VND/month</small>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>

            </div>
            <% } else { %>
            <div class="alert alert-danger mt-5">No room found.</div>
            <% } %>
        </main>

        <!-- ====== FOOTER ====== -->
      <footer class="bg-light text-dark mt-5 pt-4 pb-3 border-top">
    <div class="container-fluid px-5">

        <div class="row gy-4 justify-content-between">
            <!-- Cột 1: Logo + địa chỉ -->
            <div class="col-md-3 text-start">
                <div class="d-flex align-items-center mb-2">
                    <img src="<%= request.getContextPath() %>/images/logo.jpg"
                         alt="HomeNest Logo"
                         style="height: 48px;"
                         class="me-2">
                    <h5 class="fw-bold text-success mb-0">HOMENEST</h5>
                </div>
                <p class="mb-1"><i class="bi bi-geo-alt-fill"></i> IT Campus, Ninh Kieu District, Can Tho City</p>
                <p><i class="bi bi-telephone-fill"></i> 0889 469 948</p>
            </div>

            <!-- Cột 2: Policies -->
            <div class="col-md-2">
                <h6 class="fw-bold">POLICIES</h6>
                <p>Operating Policy</p>
                <p>Terms of Use</p>
            </div>

            <!-- Cột 3: Guidelines -->
            <div class="col-md-3">
                <h6 class="fw-bold">GUIDELINES</h6>
                <p>How to post a room</p>
                <p>Contact Support</p>
            </div>

            <!-- Cột 4: Support -->
            <div class="col-md-3">
                <h6 class="fw-bold">CUSTOMER SUPPORT</h6>
                <p><i class="bi bi-envelope"></i> support@homenest.vn</p>
                <p><i class="bi bi-heart-fill text-danger"></i> Dedicated customer care</p>
            </div>
        </div>
        <hr>
        <div class="text-center small text-muted">
            &copy; 2025 HomeNest. Designed by FPTU SE team.
        </div>
    </div>
</footer>




    </body>
</html>
