<%-- 
    Document   : register
    Created on : Jun 12, 2025, 9:16:26 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <title>Register - HomeNest</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: 'Segoe UI', sans-serif;
      background-color: #fff;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 100vh;
    }

    .logo {
      text-align: center;
      margin-top: 40px;
    }

    .logo img {
      width: 80px;
      height: 80px;
    }

    .logo h1 {
      color: rgb(37, 150, 190); /* Updated color */
      margin: 10px 0 30px 0;
      font-size: 28px;
    }

    .form-container {
      background-color: #fff;
      border: 1px solid #ddd;
      padding: 30px 40px;
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
      width: 100%;
      max-width: 400px;
      text-align: center;
      margin: 20px; /* Added margin for spacing */
    }

    .form-container h2 {
      color: rgb(37, 150, 190); /* Updated color */
      margin-bottom: 25px;
    }

    .form-group {
      margin-bottom: 20px; /* Increased margin for spacing */
      position: relative;
    }

    .form-group input,
    .form-group select {
      width: 100%;
      padding: 12px;
      border: 1px solid #ccc;
      border-radius: 12px;
      box-sizing: border-box;
      font-size: 16px;
    }

    .form-group input:focus,
    .form-group select:focus {
      outline: none;
      border-color: rgb(37, 150, 190); /* Updated color */
      box-shadow: 0 0 5px rgba(37, 150, 190, 0.6); /* Updated color */
    }

    .toggle-password-icon {
      position: absolute;
      right: 12px;
      top: 50%;
      transform: translateY(-50%);
      cursor: pointer;
      width: 20px;
      height: 20px;
      fill: rgb(37, 150, 190); /* Updated color */
      user-select: none;
    }

    .error-message {
      color: red;
      font-size: 13px;
      margin-top: 5px; /* Adjusted margin for better spacing */
      text-align: left;
      padding-left: 4px;
    }

    .btn-group {
      display: flex;
      justify-content: space-between;
      margin-top: 20px;
    }

    .btn-group button {
      width: 48%;
      padding: 10px;
      font-size: 16px;
      border: none;
      border-radius: 12px;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .btn-back {
      background-color: #ccc;
      color: #333;
    }

    .btn-back:hover {
      background-color: #b3b3b3;
    }

    .btn-register {
      background-color: rgb(37, 150, 190); /* Updated color */
      color: white;
    }

    .btn-register:hover {
      background-color: rgb(30, 130, 170); /* Darker shade for hover effect */
    }

    .footer {
      margin-top: 30px;
      font-size: 14px;
      color: #777;
    }

    /* New container for phone and gender side by side */
    .form-inline-group {
      display: flex;
      gap: 12px;
      margin-bottom: 15px;
    }

    .form-inline-group .form-group {
      margin-bottom: 0; /* override margin bottom set by .form-group */
      position: relative;
    }

    .form-inline-group .phone-group {
      flex: 2;
    }

    .form-inline-group .gender-group {
      flex: 1;
    }

    /* Responsive: stack vertically on small widths */
    @media (max-width: 480px) {
      .form-inline-group {
        flex-direction: column;
      }
      .form-inline-group .form-group {
        margin-bottom: 15px;
      }
    }
  </style>
</head>
<body>
  <div class="logo">
    <img
      src="https://cdn-icons-png.flaticon.com/512/1946/1946436.png"
      alt="Logo"
    />
    <h1>HomeNest</h1>
  </div>

  <div class="form-container">
    <h2>REGISTER</h2>

    <form id="registerForm" action="/submit_registration" method="POST" novalidate>
      <div class="form-group">
        <input
          type="text"
          placeholder="Fullname"
          required
          aria-label="Fullname"
          name="fullname"
          id="fullname"
        />
        <div class="error-message" id="fullnameError"></div>
      </div>
      <div class="form-group">
        <input
          type="email"
          placeholder="Email"
          required
          aria-label="Email"
          name="email"
          id="email"
        />
        <div class="error-message" id="emailError"></div>
      </div>
      <div class="form-group">
        <input
          type="password"
          id="password"
          placeholder="Password"
          required
          aria-label="Password"
          name="password"
        />
        <span class="toggle-password-icon" onclick="togglePassword('password', this)" aria-label="Toggle password visibility" role="button" tabindex="0">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20">
            <path d="M12 5C7 5 2.73 8.11 1 12c1.73 3.89 6 7 11 7s9.27-3.11 11-7c-1.73-3.89-6-7-11-7zM12 17a5 5 0 1 1 0-10 5 5 0 0 1 0 10z"/>
            <circle cx="12" cy="12" r="2.5"/>
          </svg>
        </span>
        <div class="error-message" id="passwordError"></div>
      </div>
      <div class="form-group">
        <input
          type="password"
          id="confirmPassword"
          placeholder="Confirm Password"
          required
          aria-label="Confirm Password"
          name="confirmPassword"
        />
        <span class="toggle-password-icon" onclick="togglePassword('confirmPassword', this)" aria-label="Toggle password visibility" role="button" tabindex="0">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20">
            <path d="M12 5C7 5 2.73 8.11 1 12c1.73 3.89 6 7 11 7s9.27-3.11 11-7c-1.73-3.89-6-7-11-7zM12 17a5 5 0 1 1 0-10 5 5 0 0 1 0 10z"/>
            <circle cx="12" cy="12" r="2.5"/>
          </svg>
        </span>
        <div class="error-message" id="confirmPasswordError"></div>
      </div>
      <div class="form-inline-group">
        <div class="form-group phone-group">
          <input
            type="text"
            placeholder="Phone"
            required
            aria-label="Phone"
            name="phone"
            id="phone"
            maxlength="10"
            pattern="\d{10}"
          />
          <div class="error-message" id="phoneError"></div>
        </div>
        <div class="form-group gender-group">
          <select required aria-label="Gender" name="gender" id="gender">
            <option value="" disabled selected>Gender</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
          </select>
          <div class="error-message" id="genderError"></div>
        </div>
      </div>

      <div class="btn-group">
        <button type="button" class="btn-back" onclick="history.back()">
          Back
        </button>
        <button type="submit" class="btn-register">Register</button>
      </div>
    </form>
  </div>

  <div class="footer">Copyright © HomeNest – Smart Rental Management</div>

  <script>
    function getEyeIconSVG() {
      return `
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20">
        <path d="M12 5C7 5 2.73 8.11 1 12c1.73 3.89 6 7 11 7s9.27-3.11 11-7c-1.73-3.89-6-7-11-7zM12 17a5 5 0 1 1 0-10 5 5 0 0 1 0 10z"/>
        <circle cx="12" cy="12" r="2.5"/>
      </svg>`;
    }

    function getEyeOffIconSVG() {
      return `
      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="20" height="20">
        <path d="M17.94 17.94a10.98 10.98 0 0 1-5.94 1.56c-5 0-9.27-3.11-11-7a10.8 10.8 0 0 1 3.11-4.72M1 1l22 22-1.41 1.41-3.75-3.75M10.18 10.18a3 3 0 0 0 4.24 4.24M12 7a5 5 0 0 1 5 5c0 .58-.11 1.13-.3 1.64"/>
        <path d="M21 21L3 3"/>
      </svg>`;
    }

    function togglePassword(inputId, toggleElement) {
      const input = document.getElementById(inputId);
      if (input.type === 'password') {
        input.type = 'text';
        // Show eye icon when visible
        toggleElement.innerHTML = getEyeIconSVG();
      } else {
        input.type = 'password';
        // Show eye off icon when hidden
        toggleElement.innerHTML = getEyeOffIconSVG();
      }
    }

    // Initialize icons so that hidden password shows eye off (indicating hidden)
    window.addEventListener('DOMContentLoaded', () => {
      document.querySelectorAll('.toggle-password-icon').forEach(span => {
        span.innerHTML = getEyeOffIconSVG();
      });
    });

    document.getElementById('registerForm').addEventListener('submit', function(event) {
      // Clear previous errors
      ['fullnameError', 'emailError', 'passwordError', 'confirmPasswordError', 'phoneError', 'genderError'].forEach(id => {
        document.getElementById(id).textContent = '';
      });

      let valid = true;

      // Password validation: min 6 characters, at least one uppercase letter, one lowercase letter, one number
      const password = document.getElementById('password').value;
      const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{6,}$/;
      if (!passwordPattern.test(password)) {
        document.getElementById('passwordError').textContent = 'Password must be at least 6 characters long, including uppercase, lowercase, and a number.';
        valid = false;
      }

      // Confirm password match
      const confirmPassword = document.getElementById('confirmPassword').value;
      if (password !== confirmPassword) {
        document.getElementById('confirmPasswordError').textContent = 'Passwords do not match.';
        valid = false;
      }

      // Phone validation: exactly 10 digits
      const phone = document.getElementById('phone').value;
      const phonePattern = /^\d{10}$/;
      if (!phonePattern.test(phone)) {
        document.getElementById('phoneError').textContent = 'Phone number must be exactly 10 digits.';
        valid = false;
      }

      // Fullname validation
      const fullname = document.getElementById('fullname').value.trim();
      if (fullname.length === 0) {
        document.getElementById('fullnameError').textContent = 'Please enter your full name.';
        valid = false;
      }

      // Email validation
      const email = document.getElementById('email').value.trim();
      const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/; // Basic email regex
      if (!emailPattern.test(email)) {
        document.getElementById('emailError').textContent = 'Please enter a valid email address.';
        valid = false;
      }

      // Gender must be selected
      const gender = document.getElementById('gender').value;
      if (!gender) {
        document.getElementById('genderError').textContent = 'Please select your gender.';
        valid = false;
      }

      if (!valid) {
        event.preventDefault();
      }
    });
  </script>
</body>
</html>
