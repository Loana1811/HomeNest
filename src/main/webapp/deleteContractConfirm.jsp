<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Confirm Delete Contract</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        .btn-teal {
            background-color: #008080;
            color: white;
        }
        .btn-teal:hover {
            background-color: #006666;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1>Confirm Delete Contract ${contract.id}</h1>
        <p>Are you sure you want to delete this contract?</p>
        <form action="contracts/doDelete" method="post">
            <input type="hidden" name="id" value="${contract.id}">
            <button type="submit" class="btn btn-teal">Yes, Delete</button>
            <a href="contracts/list" class="btn btn-teal ms-2">No, Go Back</a>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>