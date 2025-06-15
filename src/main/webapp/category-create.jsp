<%-- 
    Document   : category-create
    Created on : Jun 13, 2025, 4:22:45 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Category</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .form-container {
            max-width: 600px;
            margin: 60px auto;
            background-color: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 16px rgba(0, 0, 0, 0.1);
        }

        .form-title {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 25px;
            text-align: center;
        }

        .btn-group {
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        textarea.form-control {
            resize: vertical;
            min-height: 80px;
        }
    </style>
</head>
<body class="bg-light">
    <div class="form-container">
        <div class="form-title">Add New Category</div>
        <form method="post" action="createcategogy">
            <div class="form-group">
                <label for="categoryName">Name <span class="text-danger">*</span></label>
                <input type="text" id="categoryName" name="categoryName" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" class="form-control" placeholder="Write something..."></textarea>
            </div>

            <div class="btn-group mt-4">
                <a href="category-list.jsp" class="btn btn-outline-primary">Cancel</a>
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
</body>
</html>
