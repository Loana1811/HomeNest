<%-- 
    Document   : utility-edit
    Created on : Jun 13, 2025, 8:03:57 AM
    Author     : kloane
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.UtilityType" %>
<%@ page import="java.util.List" %>
<%
    UtilityType utility = (UtilityType) request.getAttribute("utility");
    List<Object[]> rooms = (List<Object[]>) request.getAttribute("rooms");
    String[] units = { "kWh", "Khối", "Tháng", "Người", "Chiếc", "Lần", "Cái", "Bình", "m2", "Giờ" };
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Utility</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h3 class="mb-3">🛠️ Edit Utility</h3>

    <form action="utility?action=update" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="id" value="<%= utility.getId() %>" />
        <input type="hidden" name="name" value="<%= utility.getName() %>"/>

        <div class="mb-3">
            <label>Name</label>
            <input type="text" class="form-control" value="<%= utility.getName() %>" readonly />
        </div>

        <div class="mb-3">
            <label>Unit</label>
            <select name="unit" class="form-select" required>
                <% for (String unit : units) { %>
                    <option value="<%= unit %>" <%= unit.equals(utility.getUnit()) ? "selected" : "" %>><%= unit %></option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label>Old Price (VND)</label>
            <input type="text" class="form-control bg-light" value="<%= String.format("%.0f", utility.getPrice()) %>" readonly />
        </div>

        <div class="mb-3">
            <label>New Price (VND)</label>
            <input type="number" step="0.01" min="0" name="price" class="form-control" required />
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold d-flex justify-content-between align-items-center">
                <span>✅ Select the room you want to apply</span>
                <span>
                    <input type="checkbox" id="selectAll" class="form-check-input me-1" onclick="toggleAllRooms(this)">
                    <label for="selectAll" class="form-check-label">Select all</label>
                </span>
            </label>
            <small class="text-muted mb-2 d-block">List of applicable rooms</small>

            <div class="row">
                <% if (rooms != null) {
                    for (int i = 0; i < rooms.size(); i++) {
                        Object[] room = rooms.get(i);
                        int roomId = (Integer) room[0];
                        String roomName = (String) room[1];
                        boolean isChecked = (Boolean) room[2];
                %>
                <div class="col-md-6 mb-2">
                    <div class="form-check">
                        <input type="checkbox" name="roomIds" value="<%= roomId %>" class="form-check-input room-checkbox" id="room_<%= roomId %>" <%= isChecked ? "checked" : "" %>>
                        <label class="form-check-label fw-semibold" for="room_<%= roomId %>">Room <%= roomName %></label>
                    </div>
                </div>
                <% }} %>
            </div>
        </div>

        <div class="mt-4">
            <button type="submit" class="btn btn-primary">💾 Save</button>
            <a href="utility?action=list" class="btn btn-secondary">Cancel</a>
        </div>
    </form>

    <script>
        function toggleAllRooms(source) {
            const checkboxes = document.querySelectorAll('.room-checkbox');
            checkboxes.forEach(cb => cb.checked = source.checked);
        }

        function validateForm() {
            const price = document.querySelector('input[name="price"]').value;
            if (isNaN(price) || parseFloat(price) < 0 || price.trim() === "") {
                alert("⚠️ Please enter a valid non-negative number for price.");
                return false;
            }
            return true;
        }
    </script>
</body>
</html>
