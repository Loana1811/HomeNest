<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Error Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-danger">Oops! Something went wrong.</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3" role="alert">
                ${error}
            </div>
        </c:if>
        <a href="javascript:history.back()" class="btn btn-secondary mt-3">Go Back</a>
    </div>
</body>
</html>
