<%-- 
    Document   : utility-history
    Created on : Jun 14, 2025, 7:01:23 AM
    Author     : kloane
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ page import="java.util.*, model.UtilityHistoryView" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Utility Price History</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="container mt-4">
        <h3>📜 Utility Price Change History</h3>

        <c:if test="${empty historyList}">
            <div class="alert alert-warning">⚠️ No history records found.</div>
        </c:if>

        <c:if test="${not empty historyList}">
            <table class="table table-bordered table-hover mt-3">
                <thead class="table-dark">
                    <tr>
                        <th>Utility</th>
                        <th>Old Price (₫)</th>
                        <th>New Price (₫)</th>
                        <th>Changed By</th>
                        <th>Change Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="h" items="${historyList}">
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${empty h.utilityName}">
                                        <span class="text-danger">[Removed]</span>
                                    </c:when>
                                    <c:otherwise>
                                        ${h.utilityName}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${h.oldPrice}</td>
                            <td>${h.newPrice}</td>
                            <td>${h.changedBy}</td>
                            <td>${h.createdAt}</td>
                        </tr>
                    </c:forEach>
                </tbody>

            </table>
        </c:if>

        <a href="utility?action=list" class="btn btn-secondary">Back</a>
    </body>
</html>
