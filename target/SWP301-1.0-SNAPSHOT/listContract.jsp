<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>List of Contracts</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">List of Contracts</h2>
            <c:if test="${not empty contracts}">
                <table class="table table-bordered table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>Contract ID</th>
                            <th>Tenant Name</th>
                            <th>Room Number</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Status</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="contract" items="${contracts}">
                            <tr>
                                <td>${contract.contractId}</td>
                                <td>${contract.tenantName}</td>
                                <td>${contract.roomNumber}</td>
                                <td>${contract.startDate}</td>
                                <td>${contract.endDate}</td>
                                <td>${contract.contractstatus}</td>
                                <td>${contract.contractcreatedAt}</td>
                                <td>
                                    <a href="<%= request.getContextPath()%>/Contracts?action=view&id=${contract.contractId}" class="btn btn-info btn-sm">View</a>
                                    <a href="<%= request.getContextPath()%>/Contracts?action=edit&id=${contract.contractId}" class="btn btn-warning btn-sm">Edit</a>                                                                   
                                    <!-- Nút xoá dùng form để tránh lỗi GET không an toàn -->
                                    <form action="Contracts" method="post" style="display:inline;" onsubmit="return confirm('Are you sure you want to delete this contract?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="contractId" value="${contract.contractId}">
                                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                    </form>
                                

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty contracts}">
                <p class="text-center text-danger">No contracts to display</p>
            </c:if>
            <div class="text-center">
                <a href="<%= request.getContextPath()%>/Contracts?action=create" class="btn btn-primary">Create New Contract</a>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>