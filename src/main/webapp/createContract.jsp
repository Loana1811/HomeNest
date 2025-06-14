<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Create New Contract</title>
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
        .terms-box {
            border: 1px solid #ccc;
            padding: 10px;
            max-height: 200px;
            overflow-y: scroll;
            background-color: #f8f9fa;
            font-size: 0.95rem;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        h1>Create New Contract</h1>
        <form id="create" action="contracts/save" method="post">
            <div class="mb-3">
                <label for="tenantId" class="form-label">Tenant ID:</label>
                <input type="text" class="form-control" id="tenantId" name="tenantId" required>
            </div>
            <div class="mb-3">
                <label for="roomId" class="form-label">Room ID:</label>
                <input type="text" class="form-control" id="roomId" name="roomId" required>
            </div>
            <div class="mb-3">
                <label for="startDate" class="form-label">Start Date:</label>
                <input type="date" class="form-control" id="startDate" name="startDate" required>
            </div>
            <div class="mb-3">
                <label for="endDate" class="form-label">End Date:</label>
                <input type="date" class="form-control" id="endDate" name="endDate" required>
            </div>
            <div class="mb-3">
                <label for="status" class="form-label">Status:</label>
                <input type="text" class="form-control" id="status" name="status" required>
            </div>
            <div class="mb-3">
                <label for="createdAt" class="form-label">Created At:</label>
                <input type="date" class="form-control" id="createdAt" name="createdAt" required>
            </div>
            <div class="alert alert-danger" id="termsAlert" style="display:none;">You must agree to the terms and conditions to create a contract.</div>
            <div class="mb-3">
                <div class="form-check">
                    <input type="checkbox" class="form-check-input" id="agreeTerms" name="agreeTerms" required>
                    <label class="form-check-label" for="agreeTerms">I agree to the terms and conditions listed below:</label>
                </div>
            </div>

            <div class="terms-box mb-3">
                <strong>Rules and Conditions for Rental Agreement:</strong><br><br>
                1. Provide valid personal identification. For stays over 30 days, register temporary residence.<br>
                2. Pay rent on time monthly; late payments may result in penalties.<br>
                3. Deposit is required and non-refundable for early termination or property damage.<br>
                4. Tenant pays for all utilities: electricity, water, internet, garbage, etc.<br>
                5. Maintain property condition; do not alter structure or furniture without permission.<br>
                6. Prohibited: illegal activities, loud noise after 10 PM, unauthorized guests, dangerous pets.<br>
                7. Termination requires 15 days? notice. Room must be returned clean and undamaged.<br>
                8. Tenant must follow all local and national laws. Disputes resolved by negotiation or authorities.
            </div>

            <button type="submit" class="btn btn-teal">Create</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script>
    document.getElementById('createContractForm').addEventListener('submit', function(event) {
        var agreeTerms = document.getElementById('agreeTerms');
        if (!agreeTerms.checked) {
            event.preventDefault();
            document.getElementById('termsAlert').style.display = 'block';
            agreeTerms.focus();
        } else {
            document.getElementById('termsAlert').style.display = 'none';
        }
    });
    </script>
</body>
</html>
