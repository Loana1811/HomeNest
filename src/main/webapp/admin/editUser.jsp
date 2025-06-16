<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.User"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edit User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <style>
            :root {
                --primary: #3498db;
                --secondary: #2c3e50;
                --success: #28a745;
                --danger: #dc3545;
                --warning: #ffc107;
                --info: #17a2b8;
                --light: #f8f9fa;
                --dark: #343a40;
            }

            body {
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            }

            .container {
                max-width: 700px;
                margin-top: 30px;
                background: rgba(255, 255, 255, 0.95);
                backdrop-filter: blur(10px);
                padding: 40px;
                border-radius: 20px;
                box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                border: 1px solid rgba(255, 255, 255, 0.2);
            }

            .form-header {
                text-align: center;
                margin-bottom: 40px;
                position: relative;
            }

            .form-header::before {
                content: '';
                position: absolute;
                bottom: -10px;
                left: 50%;
                transform: translateX(-50%);
                width: 80px;
                height: 4px;
                background: linear-gradient(90deg, var(--primary), var(--info));
                border-radius: 2px;
            }

            .form-header h2 {
                color: var(--dark);
                font-weight: 700;
                font-size: 2.5rem;
                margin-bottom: 10px;
                background: linear-gradient(135deg, var(--primary), var(--info));
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                background-clip: text;
            }

            .form-header p {
                color: #6c757d;
                font-size: 1.1rem;
                margin: 0;
            }

            .form-section {
                background: rgba(248, 249, 250, 0.8);
                padding: 25px;
                border-radius: 15px;
                margin-bottom: 25px;
                border-left: 4px solid var(--primary);
            }

            .section-title {
                color: var(--dark);
                font-weight: 600;
                font-size: 1.3rem;
                margin-bottom: 20px;
                display: flex;
                align-items: center;
                gap: 10px;
            }

            .form-floating {
                margin-bottom: 20px;
            }

            .form-floating > .form-control,
            .form-floating > .form-select {
                height: 60px;
                border: 2px solid #e9ecef;
                border-radius: 12px;
                font-size: 16px;
                transition: all 0.3s ease;
            }

            .form-floating > .form-control:focus,
            .form-floating > .form-select:focus {
                border-color: var(--primary);
                box-shadow: 0 0 0 0.25rem rgba(52, 152, 219, 0.15);
                transform: translateY(-2px);
            }

            .form-floating > label {
                font-weight: 500;
                color: #6c757d;
                padding-left: 1rem;
            }

            .required {
                color: var(--danger);
                font-weight: bold;
            }

            .role-badge {
                padding: 8px 16px;
                border-radius: 20px;
                font-size: 14px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                margin-left: 10px;
            }

            .role-admin {
                background: linear-gradient(135deg, rgba(220, 53, 69, 0.2), rgba(220, 53, 69, 0.1));
                color: #721c24;
                border: 1px solid rgba(220, 53, 69, 0.3);
            }

            .role-manager {
                background: linear-gradient(135deg, rgba(255, 193, 7, 0.2), rgba(255, 193, 7, 0.1));
                color: #856404;
                border: 1px solid rgba(255, 193, 7, 0.3);
            }

            .status-badge {
                padding: 8px 16px;
                border-radius: 20px;
                font-size: 14px;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.5px;
            }

            .status-active {
                background: linear-gradient(135deg, rgba(40, 167, 69, 0.2), rgba(40, 167, 69, 0.1));
                color: #155724;
                border: 1px solid rgba(40, 167, 69, 0.3);
            }

            .status-inactive {
                background: linear-gradient(135deg, rgba(108, 117, 125, 0.2), rgba(108, 117, 125, 0.1));
                color: #383d41;
                border: 1px solid rgba(108, 117, 125, 0.3);
            }

            .btn-group-custom {
                display: flex;
                gap: 15px;
                justify-content: center;
                margin-top: 30px;
                flex-wrap: wrap;
            }

            .btn {
                padding: 12px 30px;
                border-radius: 25px;
                font-weight: 600;
                font-size: 16px;
                text-transform: uppercase;
                letter-spacing: 0.5px;
                transition: all 0.3s ease;
                border: none;
                position: relative;
                overflow: hidden;
            }

            .btn::before {
                content: '';
                position: absolute;
                top: 0;
                left: -100%;
                width: 100%;
                height: 100%;
                background: linear-gradient(90deg, transparent, rgba(255,255,255,0.2), transparent);
                transition: left 0.5s;
            }

            .btn:hover::before {
                left: 100%;
            }

            .btn-primary {
                background: linear-gradient(135deg, var(--primary), var(--info));
                color: white;
                min-width: 150px;
            }

            .btn-primary:hover {
                background: linear-gradient(135deg, var(--info), var(--primary));
                transform: translateY(-2px);
                box-shadow: 0 8px 25px rgba(52, 152, 219, 0.3);
            }

            .btn-secondary {
                background: linear-gradient(135deg, #6c757d, #495057);
                color: white;
                min-width: 120px;
            }

            .btn-secondary:hover {
                background: linear-gradient(135deg, #495057, #6c757d);
                transform: translateY(-2px);
                box-shadow: 0 8px 25px rgba(108, 117, 125, 0.3);
            }

            .alert {
                border-radius: 12px;
                border: none;
                padding: 15px 20px;
                margin-bottom: 25px;
                font-weight: 500;
            }

            .alert-danger {
                background: linear-gradient(135deg, rgba(220, 53, 69, 0.1), rgba(220, 53, 69, 0.05));
                color: var(--danger);
                border-left: 4px solid var(--danger);
            }

            .user-info-card {
                background: linear-gradient(135deg, rgba(52, 152, 219, 0.1), rgba(23, 162, 184, 0.05));
                border-radius: 15px;
                padding: 20px;
                margin-bottom: 25px;
                border: 1px solid rgba(52, 152, 219, 0.2);
            }

            .info-item {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 8px 0;
                border-bottom: 1px solid rgba(0, 0, 0, 0.1);
            }

            .info-item:last-child {
                border-bottom: none;
            }

            .info-label {
                font-weight: 600;
                color: var(--dark);
            }

            .info-value {
                color: #6c757d;
            }

            @media (max-width: 768px) {
                .container {
                    margin: 20px;
                    padding: 25px;
                }

                .form-header h2 {
                    font-size: 2rem;
                }

                .btn-group-custom {
                    flex-direction: column;
                    align-items: center;
                }

                .btn {
                    width: 100%;
                    max-width: 200px;
                }
            }

            .animate-form {
                animation: slideInUp 0.6s ease-out;
            }

            @keyframes slideInUp {
                from {
                    opacity: 0;
                    transform: translateY(30px);
                }
                to {
                    opacity: 1;
                    transform: translateY(0);
                }
            }

            .password-note {
                background: linear-gradient(135deg, rgba(255, 193, 7, 0.1), rgba(255, 193, 7, 0.05));
                border: 1px solid rgba(255, 193, 7, 0.3);
                border-radius: 10px;
                padding: 15px;
                margin-top: 15px;
                color: #856404;
            }
        </style>
    </head>
    <body>
        <div class="container animate-form">
            <div class="form-header">
                <h2><i class="fas fa-user-cog"></i> Edit User</h2>
                <p>Update system user information and permissions</p>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <i class="fas fa-exclamation-triangle me-2"></i>
                    ${error}
                </div>
            </c:if>

            <!-- Current User Info Display -->
            <c:if test="${not empty user}">
                <div class="user-info-card">
                    <div class="section-title">
                        <i class="fas fa-id-card"></i>
                        Current User Information
                    </div>
                    <div class="info-item">
                        <span class="info-label">User ID:</span>
                        <span class="info-value">#${user.userID}</span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Current Role:</span>
                        <span class="info-value">
                            ${user.role.roleName}
                            <span class="role-badge role-${user.role.roleName.toLowerCase()}">
                                ${user.role.roleName}
                            </span>
                        </span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Current Status:</span>
                        <span class="info-value">
                            <span class="status-badge status-${user.status.toLowerCase()}">
                                ${user.status}
                            </span>
                        </span>
                    </div>
                    <div class="info-item">
                        <span class="info-label">Created:</span>
                        <span class="info-value">${user.createdAt}</span>
                    </div>
                </div>
            </c:if>

            <!-- Edit Form -->
            <form action="${pageContext.request.contextPath}/admin/account" method="post">

                <input type="hidden" name="action" value="edit">
                <input type="hidden" name="userID" value="${user.userID}">

                <!-- Personal Information Section -->
                <div class="form-section">
                    <div class="section-title">
                        <i class="fas fa-user"></i>
                        Personal Information
                    </div>

                    <div class="form-floating">
                        <input type="text" class="form-control" id="fullName" name="fullName" 
                               value="${user.fullName}" required>
                        <label for="fullName">Full Name <span class="required">*</span></label>
                        <div class="invalid-feedback">
                            Please provide a valid full name.
                        </div>
                    </div>

                    <div class="form-floating">
                        <input type="email" class="form-control" id="email" name="email" 
                               value="${user.email}" required>
                        <label for="email">Email Address <span class="required">*</span></label>
                        <div class="invalid-feedback">
                            Please provide a valid email address.
                        </div>
                    </div>

                    <div class="form-floating">
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" 
                               value="${user.phoneNumber}" pattern="[0-9]{10,11}" required>
                        <label for="phoneNumber">Phone Number <span class="required">*</span></label>
                        <div class="invalid-feedback">
                            Please provide a valid phone number (10-11 digits).
                        </div>
                    </div>
                </div>

                <!-- Role & Status Section -->
                <div class="form-section">
                    <div class="section-title">
                        <i class="fas fa-cog"></i>
                        Role & Status Configuration
                    </div>

                    <div class="form-floating">
                        <select class="form-select" id="roleID" name="roleID" required>
                            <option value="">Select Role</option>
                            <option value="1" ${user.roleID == 1 ? 'selected' : ''}>Admin</option>
                            <option value="2" ${user.roleID == 2 ? 'selected' : ''}>Manager</option>
                        </select>
                        <label for="roleID">User Role <span class="required">*</span></label>
                        <div class="invalid-feedback">
                            Please select a user role.
                        </div>
                    </div>
                    <div class="form-floating" id="blockSection" style="<c:out value='${user.roleID == 2 ? "" : "display: none;"}'/>">
                        <select class="form-select" id="blockID" name="blockID" <c:if test="${user.roleID == 2}">required</c:if>>
                                <option value="">Select Block</option>
                            <c:forEach items="${blockList}" var="block">
                                <option value="${block.blockID}" <c:if test="${user.blockID == block.blockID}">selected</c:if>>
                                    ${block.blockName}
                                </option>
                            </c:forEach>
                        </select>
                        <label for="blockID">Managed Block (Manager only)</label>
                    </div>


                    <div class="form-floating">
                        <select class="form-select" id="status" name="status" required>
                            <option value="">Select Status</option>
                            <option value="Active" ${user.status == 'Active' ? 'selected' : ''}>Active</option>
                            <option value="Inactive" ${user.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                            
                        </select>
                        <label for="status">Account Status <span class="required">*</span></label>
                        <div class="invalid-feedback">
                            Please select an account status.
                        </div>
                    </div>

                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="Leave blank to keep current password" />
                        <label for="password">New Password (optional)</label>
                    </div>


                    <!-- Action Buttons -->
                    <div class="btn-group-custom">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save me-2"></i>
                            Update User
                        </button>
                        <a href="${pageContext.request.contextPath}/viewListAccount" class="btn btn-secondary">
                            <i class="fas fa-times me-2"></i>
                            Cancel
                        </a>
                    </div>
            </form>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Form Validation Script -->
        <script>
            // Bootstrap form validation
            (function () {
                'use strict';
                window.addEventListener('load', function () {
                    var forms = document.getElementsByClassName('needs-validation');
                    var validation = Array.prototype.filter.call(forms, function (form) {
                        form.addEventListener('submit', function (event) {
                            if (form.checkValidity() === false) {
                                event.preventDefault();
                                event.stopPropagation();
                            }
                            form.classList.add('was-validated');
                        }, false);
                    });
                }, false);
            })();

            // Phone number validation
            document.getElementById('phoneNumber').addEventListener('input', function (e) {
                let value = e.target.value.replace(/\D/g, '');
                e.target.value = value;
            });

            // Role change warning
            document.getElementById('roleID').addEventListener('change', function (e) {
                const currentRole = '${user.role.roleName}';
                const selectedOption = e.target.options[e.target.selectedIndex];
                const newRole = selectedOption.text;

                if (currentRole !== newRole && newRole !== 'Select Role') {
                    if (!confirm(`Are you sure you want to change this user's role from ${currentRole} to ${newRole}? This will affect their system permissions.`)) {
                        e.target.value = '${user.roleID}';
                    }
                }
            });

            // Status change warning
            document.getElementById('status').addEventListener('change', function (e) {
                const currentStatus = '${user.status}';
                const newStatus = e.target.value;

                if (currentStatus !== newStatus && newStatus !== '') {
                    if (newStatus === 'Inactive') {
                        if (!confirm('Are you sure you want to deactivate this user? They will not be able to log in until reactivated.')) {
                            e.target.value = currentStatus;
                        }
                    }
                }
            });

            // Form submission confirmation
            document.querySelector('form').addEventListener('submit', function (e) {
                if (!confirm('Are you sure you want to update this user\'s information?')) {
                    e.preventDefault();
                }
            });

            function toggleBlockSection() {
                var role = document.getElementById('roleID').value;
                var blockSection = document.getElementById('blockSection');
                if (role === '2') {
                    blockSection.style.display = '';
                    document.getElementById('blockID').setAttribute('required', 'required');
                } else {
                    blockSection.style.display = 'none';
                    document.getElementById('blockID').removeAttribute('required');
                    document.getElementById('blockID').value = '';
                }
            }

            document.getElementById('roleID').addEventListener('change', toggleBlockSection);

            // Gọi 1 lần khi load để đúng trạng thái nếu sửa Manager
            window.onload = function () {
                toggleBlockSection();
            };
        </script>
    </body>
</html>