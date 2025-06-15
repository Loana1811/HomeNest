<%-- 
    Document   : error
    Created on : Jun 13, 2025, 5:17:04 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h2 style="color:red;">Oops! Something went wrong.</h2>
    <pre style="color:gray;"><%= request.getAttribute("errorMessage") %></pre>
    <a href="category-create.jsp">← Back to Add Category</a>
</body>
</html>
