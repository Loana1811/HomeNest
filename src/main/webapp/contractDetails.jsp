<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Contract Details</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f9f9f9;
            }
            .contract-container {
                max-width: 900px;
                margin: auto;
                background: #ffffff;
                padding: 40px;
                border: 1px solid #ccc;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
                line-height: 1.7;
            }
            .contract-header {
                text-align: center;
                margin-bottom: 30px;
            }
            .contract-header strong {
                font-size: 1.1rem;
            }
            .contract-title {
                font-weight: bold;
                font-size: 1.5rem;
                margin-top: 10px;
                text-decoration: underline;
            }
            .contract-section h2 {
                font-size: 1.2rem;
                margin-top: 30px;
                font-weight: bold;
                border-bottom: 1px dashed #aaa;
                padding-bottom: 5px;
            }
            .info-columns {
                display: flex;
                justify-content: space-between;
                flex-wrap: wrap;
            }
            .info-columns > div {
                width: 48%;
            }
            .signature-section {
                display: flex;
                justify-content: space-between;
                margin-top: 50px;
            }
            .signature-box {
                text-align: center;
                width: 45%;
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
        <div class="container mt-4">
            <div class="contract-container">

                <div class="contract-header">
                    <strong>SOCIALIST REPUBLIC OF VIETNAM</strong><br>
                    <strong>Independence ? Freedom ? Happiness</strong><br>
                    <hr style="width: 200px; margin: 10px auto; border: 1px dashed #000;">
                    <div class="contract-title">RESIDENTIAL LEASE AGREEMENT</div>
                    <em>(For short-term rental)</em>
                </div>

                <div class="contract-section">
                    <h2>1. Information of the Parties</h2>
                    <div class="info-columns">
                        <div>
                            <strong>Lessor (Landlord)</strong><br>
                            Name: Nguyen Van A<br>
                            ID Card: 123456789<br>
                            Phone: 0901xxxxxx<br>
                            Address: 123 ABC Street, District 1, Ho Chi Minh City
                        </div>
                        <div>
                            <strong>Lessee (Tenant)</strong><br>
                            Name: ${tenant.name}<br>
                            ID Card: ${tenant.idCard}<br>
                            Phone: ${tenant.phone}<br>
                            Permanent Address: ${tenant.address}
                        </div>
                    </div>
                </div>

                <div class="contract-section">
                    <h2>2. Rental Property Information</h2>
                    Address: ${room.address}<br>
                    Area: ${room.area}m²<br>
                    Equipment: ${room.equipment}<br>
                    Condition: Clean, ready to use
                </div>

                <div class="contract-section">
                    <h2>3. Rental Period</h2>
                    Start Date: ${contract.startDate}<br>
                    End Date: ${contract.endDate}<br>
                    Contract Type: Short-term
                </div>

                <div class="contract-section">
                    <h2>4. Rental Price & Payment</h2>
                    Rental Price: ${contract.amount} VND / month<br>
                    Deposit: ${contract.amount} VND (1 month's rent)<br>
                    Payment Method: On the 1st of each month, by cash or bank transfer
                </div>

                <div class="contract-section">
                    <h2>5. Rights & Obligations</h2>
                    Tenant is responsible for electricity, water costs, keeping the place clean, and not damaging property.<br>
                    Landlord ensures property safety and handles minor repairs.<br>
                    Subletting or changing occupants without consent is prohibited.
                </div>

                <div class="contract-section">
                    <h2>6. Termination of Contract</h2>
                    Either party must notify at least 15 days in advance to terminate early.<br>
                    Deposit is forfeited if tenant leaves without notice.<br>
                    Property must be returned in its original condition at end of term.
                </div>

                <div class="contract-section">
                    <h2>7. Effectiveness</h2>
                    This contract is effective from the date of signing.<br>
                    Both parties agree to comply strictly. In case of disputes, they shall prioritize negotiation.
                </div>

                <div class="contract-section">
                    <strong>Date of Signing: ${contract.createdAt}</strong>
                </div>

                <div class="signature-section">
                    <div class="signature-box">
                        <strong>Lessor</strong><br><br>
                        (Signed)<br><br>
                        Nguyen Van A
                    </div>
                    <div class="signature-box">
                        <strong>Lessee</strong><br><br>
                        (Signed)<br><br>
                        ${tenant.name}
                    </div>
                </div>

                <div class="text-center mt-4">
                    <button onclick="window.print()" class="btn btn-teal">Print Contract</button>
                    <a href="contracts/generatePdf?id=${contract.id}" class="btn btn-teal ms-2" target="_blank">View PDF</a>
                </div>
            </div>
        </div>
    </body>
</html>
