<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="model.Customer"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Customer</title>
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
            max-width: 800px;
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
        
        .status-badge {
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .status-potential {
            background: linear-gradient(135deg, rgba(255, 193, 7, 0.2), rgba(255, 193, 7, 0.1));
            color: #856404;
            border: 1px solid rgba(255, 193, 7, 0.3);
        }
        
        .status-converted {
            background: linear-gradient(135deg, rgba(40, 167, 69, 0.2), rgba(40, 167, 69, 0.1));
            color: #155724;
            border: 1px solid rgba(40, 167, 69, 0.3);
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
        
        .input-icon {
            position: relative;
        }
        
        .input-icon i {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: #6c757d;
            z-index: 5;
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
    </style>
</head>
<body>
    <div class="container animate-form">
        <div class="form-header">
            <h2><i class="fas fa-user-edit"></i> Edit Customer</h2>
            <p>Update customer information and details</p>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <i class="fas fa-exclamation-triangle me-2"></i>
                ${error}
            </div>
        </c:if>
        
       <form action="${pageContext.request.contextPath}/admin/account" method="post">

            <input type="hidden" name="action" value="edit" />
            <input type="hidden" name="customerID" value="${customer.customerID}" />
            
            <!-- Basic Information Section -->
            <div class="form-section">
                <div class="section-title">
                    <i class="fas fa-user"></i>
                    Basic Information
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-floating">
                            <input type="text" 
                                   id="fullName" 
                                   name="fullName" 
                                   class="form-control" 
                                   placeholder="Full Name"
                                   required 
                                   value="${customer.fullName}" />
                            <label for="fullName">Full Name <span class="required">*</span></label>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-floating">
                            <input type="email" 
                                   id="email" 
                                   name="email" 
                                   class="form-control" 
                                   placeholder="Email Address"
                                   required 
                                   value="${customer.email}" />
                            <label for="email">Email Address <span class="required">*</span></label>
                        </div>
                    </div>
                </div>
                
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-floating">
                            <input type="tel" 
                                   id="phoneNumber" 
                                   name="phoneNumber" 
                                   class="form-control" 
                                   placeholder="Phone Number"
                                   required 
                                   value="${customer.phoneNumber}" />
                            <label for="phoneNumber">Phone Number <span class="required">*</span></label>
                        </div>
                    </div>
                    
                    <div class="col-md-6">
                        <div class="form-floating">
                            <input type="text" 
                                   id="cccd" 
                                   name="cccd" 
                                   class="form-control" 
                                   placeholder="ID Card Number"
                                   value="${customer.cccd}" />
                            <label for="cccd">ID Card Number (CCCD)</label>
                        </div>
                    </div>
                </div>
            </div>
            
            <!-- Personal Details Section -->
            <div class="form-section">
                <div class="section-title">
                    <i class="fas fa-info-circle"></i>
                    Personal Details
                </div>
                
                <div class="row">
                    <div class="col-md-4">
                        <div class="form-floating">
                            <select id="gender" name="gender" class="form-select">
                                <option value="">Select Gender</option>
                                <option value="Male" <c:if test="${customer.gender == 'Male'}">selected</c:if>>Male</option>
                                <option value="Female" <c:if test="${customer.gender == 'Female'}">selected</c:if>>Female</option>
                                <option value="Other" <c:if test="${customer.gender == 'Other'}">selected</c:if>>Other</option>
                            </select>
                            <label for="gender">Gender</label>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="form-floating">
                            <input type="date" 
                                   id="birthDate" 
                                   name="birthDate" 
                                   class="form-control" 
                                   placeholder="Birth Date"
                                   value="${customer.birthDate}" />
                            <label for="birthDate">Birth Date</label>
                        </div>
                    </div>
                    
                    <div class="col-md-4">
                        <div class="form-floating">
                            <select id="status" name="status" class="form-select" required>
                                <option value="">Select Status</option>
                                <option value="Potential" <c:if test="${customer.status == 'Potential'}">selected</c:if>>Potential</option>
                                <option value="Converted" <c:if test="${customer.status == 'Converted'}">selected</c:if>>Converted</option>
                            </select>
                            <label for="status">Status <span class="required">*</span></label>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
    <div class="form-floating">
        <input type="text" 
               id="password" 
               name="password" 
               class="form-control" 
               placeholder="Password"
               value="${customer.customerPassword}" />
        <label for="password">Password</label>
    </div>
</div>

                <div class="form-floating">
                    <textarea id="address" 
                              name="address" 
                              class="form-control" 
                              placeholder="Address"
                              style="height: 100px">${customer.address}</textarea>
                    <label for="address">Address</label>
                </div>
            </div>
            
            <!-- Current Status Display -->
            <c:if test="${not empty customer.status}">
                <div class="form-section">
                    <div class="section-title">
                        <i class="fas fa-chart-line"></i>
                        Current Status
                    </div>
                    <div class="text-center">
                        <span class="status-badge status-${customer.status.toLowerCase()}">
                            <i class="fas fa-${customer.status == 'Potential' ? 'hourglass-half' : 'check-circle'}"></i>
                            ${customer.status}
                        </span>
                    </div>
                </div>
            </c:if>
            
            <!-- Action Buttons -->
            <div class="btn-group-custom">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save me-2"></i>
                    Update Customer
                </button>
                <a href="${pageContext.request.contextPath}/viewListAccount" class="btn btn-secondary">
                    <i class="fas fa-arrow-left me-2"></i>
                    Back to List
                </a>
            </div>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Form validation
        document.getElementById('editCustomerForm').addEventListener('submit', function(e) {
            const requiredFields = ['fullName', 'email', 'phoneNumber', 'status'];
            let isValid = true;
            
            requiredFields.forEach(field => {
                const input = document.getElementById(field);
                if (!input.value.trim()) {
                    input.classList.add('is-invalid');
                    isValid = false;
                } else {
                    input.classList.remove('is-invalid');
                    input.classList.add('is-valid');
                }
            });
            
            // Email validation
            const email = document.getElementById('email');
            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (email.value && !emailRegex.test(email.value)) {
                email.classList.add('is-invalid');
                isValid = false;
            }
            
            // Phone validation
            const phone = document.getElementById('phoneNumber');
            const phoneRegex = /^[0-9]{10,15}$/;
            if (phone.value && !phoneRegex.test(phone.value.replace(/\s+/g, ''))) {
                phone.classList.add('is-invalid');
                isValid = false;
            }
            
            if (!isValid) {
                e.preventDefault();
                alert('Please fill in all required fields correctly.');
            }
        });
        
        // Real-time validation feedback
        document.querySelectorAll('.form-control, .form-select').forEach(input => {
            input.addEventListener('blur', function() {
                if (this.hasAttribute('required') && !this.value.trim()) {
                    this.classList.add('is-invalid');
                    this.classList.remove('is-valid');
                } else if (this.value.trim()) {
                    this.classList.remove('is-invalid');
                    this.classList.add('is-valid');
                }
            });
        });
    </script>
</body>
</html>