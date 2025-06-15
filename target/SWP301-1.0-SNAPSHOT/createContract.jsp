<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Create New Contract</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-5">
            <h2 class="text-center">Create New Contract</h2>

            <c:if test="${not empty error}">
                <p class="text-danger text-center">${error}</p>
            </c:if>

            <form action="<%= request.getContextPath()%>/Contracts" method="post">
                <input type="hidden" name="action" value="create">

                <!-- Tenant Selection -->
                <div class="mb-3">
                    <label for="tenantId" class="form-label">Tenant</label>
                    <select class="form-select" id="tenantId" name="tenantId" required>
                        <option value="">Select Tenant</option>
                        <c:forEach var="tenant" items="${tenants}">
                            <option value="${tenant.tenantID}">
                                Tenant ID: ${tenant.tenantID} - Customer ID: ${tenant.customerID}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Room Selection -->
                <div class="mb-3">
                    <label for="roomId" class="form-label">Room</label>
                    <select class="form-select" id="roomId" name="roomId" required>
                        <option value="">Select Room</option>
                        <c:forEach var="room" items="${rooms}">
                            <option value="${room.roomID}">
                                Room ${room.roomNumber} - ${room.roomType} - ${room.location}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Dates -->
                <div class="mb-3">
                    <label for="startDate" class="form-label">Start Date</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" required>
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">End Date (optional)</label>
                    <input type="date" class="form-control" id="endDate" name="endDate">
                </div>

                <!-- Buttons -->
                <div class="text-center">
                    <button type="submit" class="btn btn-success">Create Contract</button>
                    <a href="contracts" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
