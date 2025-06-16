<%-- 
    Document   : utility-usage-list
    Created on : Jun 14, 2025, 10:39:37 PM
    Author     : kloane
--%>


<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List, java.util.ArrayList, model.UtilityUsageView" %>
<%
    List<UtilityUsageView> usages = (List<UtilityUsageView>) request.getAttribute("usages");
    if (usages == null) {
        usages = new ArrayList<>();
    }
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Usage List</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container mt-4">
        <h3>🚰📊 Water & Electricity Usage Records</h3>
        <a href="<%= ctx%>/admin/dashboard" class="btn btn-secondary mb-3">← Back to Dashboard</a>
        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <!--<th>ID</th>-->
                    <th>Room</th>
                    <th>Utility</th>
                    <th>Old</th>
                    <th>New</th>
                    <th>Used (₫)</th>
                    <th>By</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <% for (UtilityUsageView u : usages) {%>
                <tr>
                   
                    <td><%= u.getRoomNumber()%></td>
                    <td><%= u.getUtilityName()%></td>
                    <td><%= u.getOldIndex()%></td>
                    <td><%= u.getNewIndex()%></td>
                    <td><%= u.getPriceUsed()%></td>
                    <td><%= u.getChangedBy()%></td>
                    <td><%= u.getReadingDate()%></td>
                </tr>
                <% }%>
            </tbody>
        </table>
    </body>
</html>
