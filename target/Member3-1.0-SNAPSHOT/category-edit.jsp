<%-- 
    Document   : category-edit
    Created on : Jun 13, 2025, 6:45:24 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.Category" %>
<%
    Category category = (Category) request.getAttribute("category");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Category</title>
        <style>
            body {
                font-family: sans-serif;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                background: #f9f9f9;
            }

            .form-container {
                background: #fff;
                padding: 32px;
                border-radius: 16px;
                width: 420px;
                box-shadow: 0 10px 20px rgba(0,0,0,0.1);
            }

            h2 {
                text-align: center;
                margin-bottom: 24px;
            }

            label {
                display: block;
                font-weight: bold;
                margin: 12px 0 6px;
            }

            input[type="text"], textarea {
                width: 100%;
                padding: 10px 14px;
                border-radius: 6px;
                border: 1px solid #ccc;
                margin-bottom: 16px;
                font-size: 14px;
            }

            .button-group {
                display: flex;
                justify-content: space-between;
            }

            .btn-cancel {
                border: 1px solid #007bff;
                background: white;
                color: #007bff;
                padding: 10px 24px;
                border-radius: 6px;
                font-weight: bold;
                cursor: pointer;
                width: 48%;
            }

            .btn-submit {
                background: #28a745;
                color: white;
                border: none;
                padding: 10px 24px;
                border-radius: 6px;
                font-weight: bold;
                cursor: pointer;
                width: 48%;
            }

            .btn-cancel:hover {
                background: #e6f0ff;
            }

            .btn-submit:hover {
                background: #218838;
            }
        </style>
    </head>
    <body>

        <div class="form-container">
            <h2>Edit Category</h2>
            <form action="category" method="post">
                <input type="hidden" name="action" value="update">

                <input type="hidden" name="categoryID" value="<%= category.getCategoryID() %>">

                <label>Name</label>
                <input type="text" name="categoryName" value="<%= category.getCategoryName() %>" required>

                <label>Description</label>
                <textarea name="description"><%= category.getDescription() %></textarea>

                <div class="button-group">
                    <button type="button" class="btn-cancel" onclick="window.location.href = 'category?action=list'"
                            >Cancel</button>
                    <button type="submit" class="btn-submit">Submit</button>
                </div>
            </form>


        </div>

    </body>
</html>
