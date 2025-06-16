<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f5f7fa;
            font-family: Arial, sans-serif;
        }
        .container {
            max-width: 600px;
            margin-top: 50px;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            margin-bottom: 20px;
            color: #007bff;
        }
        .required {
            color: #e74c3c;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create Account</h2>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>

       <form action="${pageContext.request.contextPath}/admin/account" method="post">

            <!-- Hidden input để gửi action về servlet -->
            <input type="hidden" name="action" value="add" />

            <div class="mb-3">
                <label for="fullName" class="form-label">Full Name <span class="required">*</span></label>
                <input type="text" id="fullName" name="fullName" class="form-control" required placeholder="Enter your full name" />
                <div class="invalid-feedback">Please enter your full name.</div>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email <span class="required">*</span></label>
                <input type="email" id="email" name="email" class="form-control" required placeholder="Enter your email" />
                <div class="invalid-feedback">Please enter a valid email address.</div>
            </div>

            <div class="mb-3">
                <label for="phoneNumber" class="form-label">Phone Number <span class="required">*</span></label>
                <input type="tel" id="phoneNumber" name="phoneNumber" class="form-control" required placeholder="Enter your phone number" />
                <div class="invalid-feedback">Phone number must be exactly 10 digits.</div>
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password <span class="required">*</span></label>
                <input type="password" id="password" name="password" class="form-control" required placeholder="Enter your password" />
                <div class="invalid-feedback">Password must be at least 6 characters.</div>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password <span class="required">*</span></label>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required placeholder="Confirm your password" />
                <div class="invalid-feedback">Passwords do not match.</div>
            </div>

            <div class="mb-3">
                <label for="roleID" class="form-label">Role <span class="required">*</span></label>
                <select id="roleID" name="roleID" class="form-select" required>
                    <option value="" disabled selected>Select role</option>
                    <option value="1">Admin</option>
                    <option value="2">Manager</option>
                    <option value="3">Customer</option>
                </select>
                <div class="invalid-feedback">Please select a role.</div>
            </div>
            <div class="mb-3" id="blockSelection" style="display: none;">
    <label for="blockID" class="form-label">Managed Block (Manager only)</label>
    <select id="blockID" name="blockID" class="form-select">
        <option value="">Select a block</option>
        <c:forEach items="${blockList}" var="block">
            <option value="${block.blockID}">${block.blockName}</option>
        </c:forEach>
    </select>
</div>


            <button type="submit" class="btn btn-primary">Create Account</button>
            <a href="${pageContext.request.contextPath}/viewListAccount" class="btn btn-secondary">Back</a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        (function() {
            const form = document.getElementById('createAccountForm');
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            const phoneNumber = document.getElementById('phoneNumber');

            form.addEventListener('submit', function(event) {
                let isValid = true;

                // Reset custom validity
                password.setCustomValidity('');
                confirmPassword.setCustomValidity('');
                phoneNumber.setCustomValidity('');

                // Password >= 6 characters
                if (password.value.length < 6) {
                    password.setCustomValidity('Password must be at least 6 characters.');
                    isValid = false;
                }

                // Confirm password matches
                if (password.value !== confirmPassword.value) {
                    confirmPassword.setCustomValidity('Passwords do not match.');
                    isValid = false;
                }

                // Phone number = 10 digits
                if (!/^\d{10}$/.test(phoneNumber.value)) {
                    phoneNumber.setCustomValidity('Phone number must be exactly 10 digits.');
                    isValid = false;
                }

                if (!form.checkValidity() || !isValid) {
                    event.preventDefault();
                    event.stopPropagation();
                }

                form.classList.add('was-validated');
            });
        })();
        
        
        document.getElementById("roleID").addEventListener("change", function () {
    var blockSection = document.getElementById("blockSelection");
    blockSection.style.display = this.value === "2" ? "block" : "none";
});
    </script>
</body>
</html>
