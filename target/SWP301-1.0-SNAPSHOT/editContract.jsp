<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 class="text-center">Edit Contract</h2>
<form action="${pageContext.request.contextPath}/Contracts" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="contractId" value="${contract.contractId}">

    <!-- Tenant (read-only) -->
    <div class="mb-3">
        <label>Tenant ID</label>
        <input type="text" class="form-control" name="tenantId" value="${contract.tenantId}" readonly>
    </div>

    <!-- Room -->
    <div class="mb-3">
        <label for="roomId" class="form-label">Room</label>
        <select class="form-select" name="roomId" required>
            <c:forEach var="room" items="${rooms}">
                <option value="${room.roomID}" ${room.roomID == contract.roomId ? "selected" : ""}>
                    ${room.roomNumber}
                </option>
            </c:forEach>
        </select>
    </div>



    <!-- Dates -->
    <div class="mb-3">
        <label>Start Date</label>
        <input type="date" name="startDate" class="form-control" value="${contract.startDate}" required>
    </div>
    <div class="mb-3">
        <label>End Date</label>
        <input type="date" name="endDate" class="form-control" value="${contract.endDate}">
    </div>

    <!-- Status -->
    <div class="mb-3">
        <label>Status</label>
        <select name="status" class="form-select" required>
            <option value="Active" ${contract.status == 'Active' ? 'selected' : ''}>Active</option>
            <option value="Expired" ${contract.status == 'Expired' ? 'selected' : ''}>Expired</option>
            <option value="Terminated" ${contract.status == 'Terminated' ? 'selected' : ''}>Terminated</option>
        </select>
    </div>

    <div class="text-center">
        <button type="submit" class="btn btn-success">Update</button>
        <a href="${pageContext.request.contextPath}/Contracts" class="btn btn-secondary">Cancel</a>
    </div>
</form>
