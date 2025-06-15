<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, model.Room" %>
<%
    List<Room> roomList = (List<Room>) request.getAttribute("roomList");

    String selectedStatus = request.getParameter("status") != null ? request.getParameter("status") : "";
    String selectedType = request.getParameter("type") != null ? request.getParameter("type") : "";
    String minPrice = request.getParameter("minPrice") != null ? request.getParameter("minPrice") : "";
    String maxPrice = request.getParameter("maxPrice") != null ? request.getParameter("maxPrice") : "";
     String minArea = request.getParameter("minArea") != null ? request.getParameter("minArea") : "";
    String maxArea = request.getParameter("maxArea") != null ? request.getParameter("maxArea") : "";
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Room List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet"/>
        <style>





            .room-card {
                border: 1px solid #ddd;
                border-radius: 15px;
                overflow: hidden;
                box-shadow: 0 4px 16px rgba(0,0,0,0.1);
                transition: 0.3s;
                background: #fff;
                min-height: 400px;
            }
            .room-card:hover {
                transform: translateY(-6px);
            }
            .room-img {
                width: 100%;
                height: 220px;
                object-fit: cover;
            }
            .price {
                font-size: 2.4rem;      /* ~36px */
                font-weight: 900;
                color: red;
                margin-top: 0.5rem;
            }
            .room-info p {
                margin-bottom: 3px;
                font-size: 16px;
                color: #444;
            }
            .status-available {
                color: green;
            }
            .status-occupied {
                color: red;
            }

            .filter-popup {
                position: fixed;
                top: 10%;
                left: 50%;
                transform: translateX(-50%);
                width: 90%;
                max-width: 900px;
                background: #fff;
                border-radius: 15px;
                z-index: 1000;
                padding: 20px;
                box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2);
            }
            .overlay {
                position: fixed;
                inset: 0;
                background: rgba(0, 0, 0, 0.5);
                z-index: 999;
            }
            .d-none {
                display: none !important;
            }
            .price-option-group .btn.active {
                background-color: #495057;
                color: white;
            }
            .area-option-group .btn.active {
                background-color: #495057;
                color: white;
            }
            footer .container-fluid {
                max-width: 100% !important;
            }
            .pagination .page-link {
                color: #198754; /* Bootstrap xanh lá */
                border-color: #198754;
            }

            .pagination .page-link:hover {
                background-color: #198754;
                color: white;
            }

            .pagination .active .page-link {
                background-color: #198754;
                border-color: #198754;
                color: white;
            }

        </style>
    </head>
    <body style="padding-top: 100px; overflow-x: hidden;">



        <nav class="navbar navbar-light bg-white shadow-sm fixed-top px-4 py-2">
            <div class="container-fluid d-flex justify-content-between align-items-center">

                <!-- Logo bên trái -->
                <div class="d-flex align-items-center">

                    <img src="<%= request.getContextPath() %>/images/logo.jpg" alt="HomeNest Logo" style="height: 70px;" class="mb-2 me-2">
                    <span class="fw-bold fs-4 text-success">HomeNest</span>
                </div>

                <!-- Nút Filter bên phải -->
                <button class="btn btn-outline-dark" onclick="toggleFilter()">Filter</button>


            </div>
        </nav>
        <!-- Overlay -->
        <div id="overlay" class="overlay d-none" onclick="toggleFilter()"></div>

        <!-- Filter Popup -->
        <div id="filterPopup" class="filter-popup d-none">
            <form method="get" action="rooms">
                <h5 class="fw-bold mb-3">Room Filters</h5>
                <!-- Block -->
                <div class="mb-3">
                    <label class="fw-semibold mb-2">Block</label><br/>
                    <div class="d-flex flex-wrap gap-2">
                        <input type="radio" class="btn-check" name="block" id="blockAll" value="" 
                               <%= request.getParameter("block") == null ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="blockAll">All</label>

                        <input type="radio" class="btn-check" name="block" id="blockA" value="1"
                               <%= "1".equals(request.getParameter("block")) ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="blockA">Khu A</label>

                        <input type="radio" class="btn-check" name="block" id="blockB" value="2"
                               <%= "2".equals(request.getParameter("block")) ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="blockB">Khu B</label>

                        <input type="radio" class="btn-check" name="block" id="blockC" value="3"
                               <%= "3".equals(request.getParameter("block")) ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="blockC">Khu C</label>

                        <input type="radio" class="btn-check" name="block" id="blockD" value="4"
                               <%= "4".equals(request.getParameter("block")) ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="blockD">Khu D</label>
                    </div>
                </div>

                <!-- Room Type -->
                <div class="mb-3">
                    <label class="fw-semibold mb-2">Room Type</label><br/>
                    <div class="d-flex flex-wrap gap-2">
                        <input type="radio" class="btn-check" name="type" id="withLoft" value="WithLoft" <%= selectedType.equals("WithLoft") ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="withLoft">With Loft</label>

                        <input type="radio" class="btn-check" name="type" id="noLoft" value="NoLoft" <%= selectedType.equals("NoLoft") ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="noLoft">No Loft</label>

                        <input type="radio" class="btn-check" name="type" id="allType" value="" <%= selectedType.isEmpty() ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="allType">All</label>
                    </div>
                </div>

                <!-- Status -->
                <div class="mb-3">
                    <label class="fw-semibold mb-2">Status</label><br/>
                    <div class="d-flex flex-wrap gap-2">
                        <input type="radio" class="btn-check" name="status" id="statusAll" value="" <%= selectedStatus.isEmpty() ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="statusAll">All</label>

                        <input type="radio" class="btn-check" name="status" id="statusAvailable" value="Available" <%= selectedStatus.equals("Available") ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="statusAvailable">Available</label>

                        <input type="radio" class="btn-check" name="status" id="statusOccupied" value="Occupied" <%= selectedStatus.equals("Occupied") ? "checked" : "" %>>
                        <label class="btn btn-outline-secondary rounded" for="statusOccupied">Occupied</label>
                    </div>
                </div>

                <!-- Price Range -->
                <div class="mb-3">
                    <label class="fw-semibold mb-2">Price Range (VND)</label><br/>
                    <div class="d-flex flex-wrap gap-2 price-option-group" id="priceGroup">
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="0" data-max="1000000" onclick="selectPrice(this, 0, 1000000)">Under 1M</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="1000000" data-max="2000000" onclick="selectPrice(this, 1000000, 2000000)">1 - 2M</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="2000000" data-max="3000000" onclick="selectPrice(this, 2000000, 3000000)">2 - 3M</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="3000000" data-max="5000000" onclick="selectPrice(this, 3000000, 5000000)">3 - 5M</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="5000000" data-max="7000000" onclick="selectPrice(this, 5000000, 7000000)">5 - 7M</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="7000000" data-max="10000000" onclick="selectPrice(this, 7000000, 10000000)">7 - 10M</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="10000000" data-max="" onclick="selectPrice(this, 10000000, null)">Above 10M</button>
                        <button type="button" class="btn btn-outline-danger rounded" onclick="clearPrice()">Clear</button>
                    </div>
                </div>

                <!-- Area Range -->
                <div class="mb-3">
                    <label class="fw-semibold mb-2">Area Range (m²)</label><br/>
                    <div class="d-flex flex-wrap gap-2 area-option-group" id="areaGroup">
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="0" data-max="18" onclick="selectArea(this, 0, 18)">Under 18</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="18" data-max="20" onclick="selectArea(this, 18, 20)">18 - 20</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="20" data-max="25" onclick="selectArea(this, 20, 25)">20 - 25</button>
                        <button type="button" class="btn btn-outline-secondary rounded" data-min="25" data-max="" onclick="selectArea(this, 25, null)">Above 25</button>
                        <button type="button" class="btn btn-outline-danger rounded" onclick="clearArea()">Clear</button>
                    </div>
                </div>

                <input type="hidden" name="minArea" id="minArea" value="<%= minArea %>">
                <input type="hidden" name="maxArea" id="maxArea" value="<%= maxArea %>">



                <input type="hidden" name="minPrice" id="minPrice" value="<%= minPrice %>">
                <input type="hidden" name="maxPrice" id="maxPrice" value="<%= maxPrice %>">

                <div class="text-end mt-4">
                    <button type="submit" class="btn btn-primary">Apply</button>
                    <button type="button" class="btn btn-secondary ms-2" onclick="toggleFilter()">Cancel</button>
                </div>
            </form>
        </div>

        <% if (roomList != null && !roomList.isEmpty()) { %>
        <div class="row g-4 px-4 px-md-5">

            <% for (Room r : roomList) { %>
            <div class="col-md-4">
                <div class="room-card">
                    <img src="images/rooms/<%= r.getImageName() != null ? r.getImageName() : "room-default.jpg" %>"
                         onerror="this.onerror=null;this.src='images/rooms/room-default.jpg';"
                         class="room-img" alt="Room Image">
                    <div class="p-3 room-info">

                        <h5 class="fw-semibold mb-2">
                            <%= r.getRoomNumber() %>
                        </h5>


                        <p><i class="bi bi-house-door"></i> Type: <%= r.getRoomType() %></p>
                        <p><i class="bi bi-diagram-3"></i> Block: <%= r.getBlockName() %></p>
                        <p class="text-danger fw-semibold">
                            <i class="bi bi-arrows-fullscreen me-1"></i> <%= r.getArea() %> m²
                        </p>


                        <p><i class="bi bi-geo-alt"></i> <%= r.getLocation() %></p>
                        <p>
                            <i class="bi bi-door-closed"></i> Status:
                            <span class="<%= r.getStatus().equalsIgnoreCase("Available") ? "status-available" : "status-occupied" %>">
                                <%= r.getStatus() %>
                            </span>
                        </p>
                        <p class="price" style="font-size: 1.2rem;">
                            Price:
                            <span style="font-size: 1.5rem; font-weight: 600; color: red;">
                                <%= String.format("%,.0f", r.getRentPrice()) %> ₫
                            </span> / month
                        </p>


                        <div class="text-end">
                            <a href="room-detail?id=<%= r.getRoomID() %>" class="btn btn-warning fw-bold px-4 py-2">View details</a>
                        </div>


                        <%
                            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
                            java.util.Date postedDate = r.getPostedDate();
                        %>
                        <% if (postedDate != null) { %>
                        <p class="text-muted fst-italic small mt-2 mb-0">
                            Post: <%= sdf.format(postedDate) %>
                        </p>
                        <% } %>
                    </div>
                </div>
            </div>
            <% } %>
        </div> <!-- Kết thúc .row -->

        <!-- ===== PHÂN TRANG ===== -->
        <%
            Integer currentPage = (Integer) request.getAttribute("currentPage");
            Integer totalPages = (Integer) request.getAttribute("totalPages");

            if (currentPage == null) currentPage = 1;
            if (totalPages == null) totalPages = 1;
        %>

        <div class="d-flex justify-content-center my-4">
            <nav>
                <ul class="pagination">
                    <% if (currentPage > 1) { %>
                    <li class="page-item">
                        <a class="page-link" href="rooms?page=<%= currentPage - 1 %>">&laquo;</a>
                    </li>
                    <% } %>

                    <% for (int i = 1; i <= totalPages; i++) { %>
                    <li class="page-item <%= (i == currentPage) ? "active" : "" %>">
                        <a class="page-link" href="rooms?page=<%= i %>"><%= i %></a>
                    </li>
                    <% } %>

                    <% if (currentPage < totalPages) { %>
                    <li class="page-item">
                        <a class="page-link" href="rooms?page=<%= currentPage + 1 %>">&raquo;</a>
                    </li>
                    <% } %>
                </ul>
            </nav>
        </div>

        <% } else { %>
        <div class="alert alert-warning mt-4">No rooms available to display.</div>
        <% } %>



        <script>
            function toggleFilter() {
                document.getElementById('filterPopup').classList.toggle('d-none');
                document.getElementById('overlay').classList.toggle('d-none');
            }

            function selectPrice(btn, min, max) {
                document.getElementById("minPrice").value = min !== null ? min : '';
                document.getElementById("maxPrice").value = max !== null ? max : '';

                document.querySelectorAll('#priceGroup .btn').forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
            }

            function clearPrice() {
                document.getElementById("minPrice").value = '';
                document.getElementById("maxPrice").value = '';
                document.querySelectorAll('#priceGroup .btn').forEach(b => b.classList.remove('active'));
            }
            function selectArea(btn, min, max) {
                document.getElementById("minArea").value = min !== null ? min : '';
                document.getElementById("maxArea").value = max !== null ? max : '';
                document.querySelectorAll('#areaGroup .btn').forEach(b => b.classList.remove('active'));
                btn.classList.add('active');
            }

            function clearArea() {
                document.getElementById("minArea").value = '';
                document.getElementById("maxArea").value = '';
                document.querySelectorAll('#areaGroup .btn').forEach(b => b.classList.remove('active'));
            }

            window.onload = function () {
                const min = document.getElementById("minPrice").value;
                const max = document.getElementById("maxPrice").value;
                document.querySelectorAll('#priceGroup .btn').forEach(btn => {
                    const btnMin = btn.getAttribute("data-min");
                    const btnMax = btn.getAttribute("data-max");
                    if (btnMin === min && btnMax === max) {
                        btn.classList.add("active");
                    }
                });

                const minArea = document.getElementById("minArea").value;
                const maxArea = document.getElementById("maxArea").value;
                document.querySelectorAll('#areaGroup .btn').forEach(btn => {
                    const btnMin = btn.getAttribute("data-min");
                    const btnMax = btn.getAttribute("data-max");
                    if (btnMin === minArea && btnMax === maxArea) {
                        btn.classList.add("active");
                    }
                });
            };
        </script>

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