<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h1>Customer Full Details</h1>
    <a href="${pageContext.request.contextPath}/viewListAccount" class="btn btn-secondary mb-3">Back to List</a>
    <c:choose>
        <c:when test="${not empty details}">
            <%
                List<Map<String, Object>> details = (List<Map<String, Object>>) request.getAttribute("details");
                Map<String, Object> customerRow = details != null && !details.isEmpty() ? details.get(0) : null;

                // Chuẩn bị các set chứa ID duy nhất cho từng bảng (Contract, Bill, UtilityReading, ...)
                Set<Integer> contractIds = new LinkedHashSet<>();
                Set<Integer> billIds = new LinkedHashSet<>();
                Set<Integer> readingIds = new LinkedHashSet<>();
                Set<Integer> requestIds = new LinkedHashSet<>();
                Set<Integer> incurredFeeIds = new LinkedHashSet<>();

                for (Map<String, Object> row : details) {
                    if (row.get("ContractID") != null) contractIds.add((Integer) row.get("ContractID"));
                    if (row.get("BillID") != null) billIds.add((Integer) row.get("BillID"));
                    if (row.get("ReadingID") != null) readingIds.add((Integer) row.get("ReadingID"));
                    if (row.get("RequestID") != null) requestIds.add((Integer) row.get("RequestID"));
                    if (row.get("IncurredFeeID") != null) incurredFeeIds.add((Integer) row.get("IncurredFeeID"));
                }
            %>
            <!-- Customer Info -->
            <div class="mb-3">
                <h4>Customer Info</h4>
                <ul>
                    <li><b>ID:</b> <%= customerRow != null ? customerRow.get("CustomerID") : "" %></li>
                    <li><b>Name:</b> <%= customerRow != null ? customerRow.get("CustomerName") : "" %></li>
                    <li><b>Phone:</b> <%= customerRow != null ? customerRow.get("PhoneNumber") : "" %></li>
                    <li><b>Email:</b> <%= customerRow != null ? customerRow.get("Email") : "" %></li>
                    <li><b>Status:</b> <%= customerRow != null ? customerRow.get("CustomerStatus") : "" %></li>
                </ul>
            </div>
            <!-- Contracts -->
            <h5>Contracts</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>Contract ID</th><th>Tenant</th><th>Room</th>
                    <th>Start</th><th>End</th><th>Status</th><th>Created</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Integer cid : contractIds) {
                        for (Map<String, Object> row : details) {
                            if (cid.equals(row.get("ContractID"))) {
                %>
                <tr>
                    <td><%= row.get("ContractID") %></td>
                    <td>
                        <%= row.get("TenantID") %><br>
                        Join: <%= row.get("JoinDate") %>
                    </td>
                    <td>
                        <%= row.get("RoomNumber") %> (<%= row.get("RoomType") %>)<br>
                        <%= row.get("Location") %>
                    </td>
                    <td><%= row.get("StartDate") %></td>
                    <td><%= row.get("EndDate") %></td>
                    <td><%= row.get("ContractStatus") %></td>
                    <td><%= row.get("ContractCreatedAt") %></td>
                </tr>
                <% break; }}} %>
                </tbody>
            </table>

            <!-- Bills -->
            <h5>Bills</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>Bill ID</th><th>Date</th><th>Total</th><th>Status</th>
                    <th>Room Rent</th><th>Elec</th><th>Water</th><th>Wifi</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Integer bid : billIds) {
                        for (Map<String, Object> row : details) {
                            if (bid.equals(row.get("BillID"))) {
                %>
                <tr>
                    <td><%= row.get("BillID") %></td>
                    <td><%= row.get("BillDate") %></td>
                    <td><%= row.get("TotalAmount") %></td>
                    <td><%= row.get("BillStatus") %></td>
                    <td><%= row.get("RoomRent") %></td>
                    <td><%= row.get("ElectricityCost") %></td>
                    <td><%= row.get("WaterCost") %></td>
                    <td><%= row.get("WifiCost") %></td>
                </tr>
                <% break; }}} %>
                </tbody>
            </table>

            <!-- Utility Readings -->
            <h5>Utility Readings</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>ReadingID</th><th>Type</th><th>Date</th>
                    <th>Old</th><th>New</th><th>Used</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Integer rid : readingIds) {
                        for (Map<String, Object> row : details) {
                            if (rid.equals(row.get("ReadingID"))) {
                %>
                <tr>
                    <td><%= row.get("ReadingID") %></td>
                    <td><%= row.get("UtilityName") %></td>
                    <td><%= row.get("ReadingDate") %></td>
                    <td><%= row.get("OldReading") %></td>
                    <td><%= row.get("NewReading") %></td>
                    <td><%= row.get("PriceUsed") %></td>
                </tr>
                <% break; }}} %>
                </tbody>
            </table>

            <!-- Rental Requests -->
            <h5>Rental Requests</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>ReqID</th><th>Date</th><th>Status</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Integer rqid : requestIds) {
                        for (Map<String, Object> row : details) {
                            if (rqid.equals(row.get("RequestID"))) {
                %>
                <tr>
                    <td><%= row.get("RequestID") %></td>
                    <td><%= row.get("RequestDate") %></td>
                    <td><%= row.get("RequestStatus") %></td>
                </tr>
                <% break; }}} %>
                </tbody>
            </table>

            <!-- Incurred Fees -->
            <h5>Incurred Fees</h5>
            <table class="table table-bordered table-sm">
                <thead>
                <tr>
                    <th>FeeID</th><th>Fee Name</th><th>Amount</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Integer fid : incurredFeeIds) {
                        for (Map<String, Object> row : details) {
                            if (fid.equals(row.get("IncurredFeeID"))) {
                %>
                <tr>
                    <td><%= row.get("IncurredFeeID") %></td>
                    <td><%= row.get("FeeName") %></td>
                    <td><%= row.get("Amount") %></td>
                </tr>
                <% break; }}} %>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="alert alert-info">No details found for this customer.</div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
s