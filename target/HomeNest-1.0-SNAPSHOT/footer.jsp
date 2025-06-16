<%-- 
    Document   : footer
    Created on : Jun 16, 2025, 11:05:42 AM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- ====== FOOTER ====== -->
<footer class="bg-light text-dark mt-5 pt-4 pb-3 border-top">
    <div class="container-fluid px-5">
        <div class="row gy-4 justify-content-between">

            <!-- Logo + địa chỉ -->
            <div class="col-md-3 text-start">
                <div class="d-flex align-items-center mb-2">
                    <img src="<%= request.getContextPath()%>/images/logo.jpg"
                         alt="HomeNest Logo" style="height: 48px;" class="me-2">
                    <h5 class="fw-bold text-success mb-0">HOMENEST</h5>
                </div>
                <p class="mb-1"><i class="bi bi-geo-alt-fill"></i> FPT Campus, Ninh Kieu District, Can Tho</p>
                <p><i class="bi bi-telephone-fill"></i> 0889 469 948</p>
            </div>

            <!-- Chính sách -->
            <div class="col-md-2 text-center">
                <h6 class="fw-bold">POLICIES</h6>
                <p>Operating Policy</p>
                <p>Terms of Use</p>
            </div>

            <!-- Hướng dẫn -->
            <div class="col-md-3 text-center">
                <h6 class="fw-bold">GUIDELINES</h6>
                <p>How to post a room</p>
                <p>Contact Support</p>
            </div>

            <!-- Hỗ trợ -->
            <div class="col-md-3 text-end">
                <h6 class="fw-bold">CUSTOMER SUPPORT</h6>
                <p><i class="bi bi-envelope"></i> group3@homenest.vn</p>
                <p><i class="bi bi-heart-fill text-danger"></i> Dedicated customer care</p>
            </div>

        </div>
        <hr>
        <div class="text-center small text-muted">
            &copy; 2025 HomeNest. Designed by Group 3 team.
        </div>
    </div>
</footer>

