<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Invoice Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                background-color: #f8f9fa;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }
            .invoice-card {
                max-width: 700px;
                margin: 40px auto;
                background: #fff;
                border: 1px solid #dee2e6;
                border-radius: 8px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
            }
            .invoice-header {
                background-color: #008080;
                color: #fff;
                padding: 20px;
                border-top-left-radius: 8px;
                border-top-right-radius: 8px;
            }
            .invoice-header h2 {
                margin: 0;
                font-size: 1.5rem;
            }
            .invoice-body {
                padding: 30px;
            }
            .invoice-body table {
                width: 100%;
            }
            .invoice-body th {
                width: 35%;
                text-align: left;
                padding: 8px;
                background-color: #f1f1f1;
            }
            .invoice-body td {
                padding: 8px;
            }
            .btn-teal {
                background-color: #008080;
                color: white;
            }
            .btn-teal:hover {
                background-color: #006666;
            }
        </style>
    </head>
    <body>
        <div class="invoice-card">
            <div class="invoice-header text-center">
                <h2>Invoice Details</h2>
            </div>
            <div class="invoice-body">
                <table class="table table-bordered">
                    <tbody>
                        <tr>
                            <th scope="row">Invoice ID</th>
                            <td>${invoice.id}</td>
                        </tr>
                        <tr>
                            <th>Contract ID</th>
                            <td>${invoice.contractId}</td>
                        </tr>
                        <tr>
                            <th>Invoice Date</th>
                            <td>${invoice.date}</td>
                        </tr>
                        <tr>
                            <th>Amount</th>
                            <td>${invoice.amount} VND</td>
                        </tr>
                        <tr>
                            <th>Due Date</th>
                            <td>${invoice.dueDate}</td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td>${invoice.status}</td>
                        </tr>
                    </tbody>
                </table>
                <div class="text-center mt-4">
                    <a href="contracts/show?id=${invoice.contractId}" class="btn btn-teal">Back to Contract</a>
                </div>
            </div>
        </div>
    </body>
</html>
