<%-- 
    Document   : category-list
    Created on : Jun 13, 2025, 5:39:18 PM
    Author     : ADMIN
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<html>
    <head>
        <title>Category List</title>
        <style>
            table {
                width: 100%;
                border-collapse: collapse;
            }

            th, td {
                padding: 12px;
                border: 1px solid #ccc;
                text-align: center;
            }

            th {
                background-color: green;
                color: white;
            }

            .no-data {
                text-align: center;
                padding: 20px;
                color: #999;
            }

            .delete-btn {
                background-color: #e74c3c;
                color: white;
                border: none;
                padding: 6px 12px;
                border-radius: 5px;
                cursor: pointer;
            }

            h2 {
                padding: 16px;
            }

            .action-buttons {
                display: flex;
                justify-content: center;
                gap: 8px;
            }

            .btn {
                padding: 6px 12px;
                border: none;
                border-radius: 6px;
                font-weight: bold;
                cursor: pointer;
                font-size: 14px;
            }

            .btn-edit {
                background-color: green;
                color: white;
            }

            .btn-edit:hover {
                background-color: #0056b3;
            }

            .btn-delete {
                background-color: red;
                color: white;
            }

            .btn-delete:hover {
                background-color: #c82333;
            }

        </style>
    </head>
    <body>

        <h2>Category List</h2>

        <%
            List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");

            int index = 1;
        %>

        <table>
            <thead>
                <tr>
                    <th>#</th>
                    <th>Category ID</th>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Action</th>  
                </tr>
            </thead>
            <tbody>
                <% if (categoryList != null && !categoryList.isEmpty()) {
            for (Category c : categoryList) { %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= c.getCategoryID() %></td>
                    <td><%= c.getCategoryName() %></td>
                    <td><%= c.getDescription() %></td>
                    <td>
                        <div class="action-buttons">
                            <form action="category" method="get" style="display:inline;">
                                <input type="hidden" name="action" value="edit">
                                <input type="hidden" name="id" value="<%= c.getCategoryID() %>">
                                <button type="submit" class="btn btn-edit">Edit</button>
                            </form>

                            <form action="category" method="get" style="display:inline;" onsubmit="return confirm('Are you sure?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="<%= c.getCategoryID() %>">
                                <button type="submit" class="btn btn-delete">Delete</button>
                            </form>

                        </div>
                    </td>


                </tr>
                <%  }
           } else { %>
                <tr>
                    <td colspan="5" class="no-data">No categories found.</td>
                </tr>
                <% } %>
            </tbody>
        </table>

    </body>
</html>
