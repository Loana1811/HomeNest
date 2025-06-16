<%-- 
    Document   : dashboard
    Created on : Jun 14, 2025, 6:10:42 PM
    Author     : kloane
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%
    Integer totalRooms = (Integer) request.getAttribute("totalRooms");
    Integer totalTenants = (Integer) request.getAttribute("totalTenants");
    Integer unpaidBills = (Integer) request.getAttribute("unpaidBills");
    Integer readings30 = (Integer) request.getAttribute("readings30");
    List<String> chartLabels = (List<String>) request.getAttribute("chartLabels");
    List<Number> chartData = (List<Number>) request.getAttribute("chartData");
    String ctx = request.getContextPath();

    StringBuilder lblJs = new StringBuilder();
    StringBuilder dataJs = new StringBuilder();
    if (chartLabels != null) {
        for (int i = 0; i < chartLabels.size(); i++) {
            lblJs.append("\"").append(chartLabels.get(i)).append("\"");
            if (i < chartLabels.size() - 1) {
                lblJs.append(",");
            }
        }
    }
    if (chartData != null) {
        for (int i = 0; i < chartData.size(); i++) {
            dataJs.append(chartData.get(i));
            if (i < chartData.size() - 1) {
                dataJs.append(",");
            }
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>🏠 HomeNest - Admin Dashboard</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <style>
            body {
                padding-top:56px;
            }
            .sidebar {
                width:240px;
                min-height:100vh;
                position:fixed;
                top:56px;
                left:0;
                background:#f8f9fa;
                padding:1rem;
            }
            main {
                margin-left:260px;
                padding:1rem;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">🏠 HomeNest</a>
                <button class="navbar-toggler" type="button" 
                        data-bs-toggle="collapse" data-bs-target="#navMenu">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navMenu">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="<%= ctx%>/admin/logout">Logout</a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="sidebar">
            <h5 class="text-primary">Admin Menu</h5>
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="<%= ctx%>/admin/dashboard">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= ctx%>/admin/utility?action=list">Utilities</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= ctx%>/admin/room?action=list">Rooms</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= ctx%>/admin/tenant?action=list">Tenants</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= ctx%>/admin/bill?action=list">Bills</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= ctx%>/admin/record-reading">Record Usage</a>
                </li>
                <a class="nav-link" href="${pageContext.request.contextPath}/admin/usage">
                    View Usage List
                </a>
            </ul>
        </div>

        <main>
            <h2>📊 Admin Dashboard</h2>
            <div class="row g-3 mb-4">
                <div class="col-md-3">
                    <div class="card text-white bg-primary p-3">
                        <h5>Total Rooms</h5>
                        <h2><%= totalRooms != null ? totalRooms : 0%></h2>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-white bg-success p-3">
                        <h5>Tenants</h5>
                        <h2><%= totalTenants != null ? totalTenants : 0%></h2>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-dark bg-warning p-3">
                        <h5>Unpaid Bills</h5>
                        <h2><%= unpaidBills != null ? unpaidBills : 0%></h2>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-white bg-info p-3">
                        <h5>Readings (30d)</h5>
                        <h2><%= readings30 != null ? readings30 : 0%></h2>
                    </div>
                </div>

                <a href="${pageContext.request.contextPath}/admin/usage"
                   class="btn btn-primary mb-4">
                    🚰📊 View Usage
                </a>

            </div>

            <div class="card">
                <div class="card-header">📈 Electricity Usage (Last 7 days)</div>
                <div class="card-body">
                    <canvas id="usageChart" style="width:100%; height:300px;"></canvas>
                </div>
            </div>
        </main>

        <script>
            const labels = [<%= lblJs%>];
            const data = [<%= dataJs%>];

            const ctx2 = document.getElementById('usageChart').getContext('2d');
            new Chart(ctx2, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                            label: 'kWh used',
                            data: data,
                            fill: true,
                            tension: 0.3
                        }]
                },
                options: {scales: {y: {beginAtZero: true}}}
            });
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>