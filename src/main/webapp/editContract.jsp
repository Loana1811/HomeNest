<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Edit Contract</title>
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
            <h1>Edit Contract</h1>
            <form action="" method="post">
              
                <div class="mb-3">
                    <label for="tenantId" class="form-label">Tenant ID:</label>
                    <input type="text" class="form-control" id="tenantId" name="tenantId" value="${contract.tenantId}" required>
                </div>
                <div class="mb-3">
                    <label for="roomId" class="form-label">Room ID:</label>
                    <input type="text" class="form-control" id="roomId" name="roomId" value="${contract.roomId}" required>
                </div>
                <div class="mb-3">
                    <label for="startDate" class="form-label">Start Date:</label>
                    <input type="date" class="form-control" id="startDate" name="startDate" value="${contract.startDate}" required>
                </div>
                <div class="mb-3">
                    <label for="endDate" class="form-label">End Date:</label>
                    <input type="date" class="form-control" id="endDate" name="endDate" value="${contract.endDate}" required>
                </div>
                <div class="mb-3">
                    <label for="status" class="form-label">Status:</label>
                    <input type="text" class="form-control" id="status" name="status" value="${contract.status}" required>
                </div>
                <button type="submit" class="btn btn-teal">Edit</button>
            </form>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>
</html>