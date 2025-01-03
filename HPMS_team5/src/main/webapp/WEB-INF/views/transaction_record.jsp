<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction Records</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <!-- Display success message if exists -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>

        <!-- Header Section -->
        <div class="d-flex justify-content-between mb-3">
            <h3>Transaction Records</h3>
            <a href="add_transaction">
                <button type="button" class="btn btn-primary">
                    <i class="bi bi-plus"></i>Add Transaction
                </button>
            </a>
        </div>

        <!-- Navigation Section -->
        <nav class="mb-3">
            <ul class="nav nav-pills">
                <li class="nav-item">
                    <a class="nav-link" href="appointments.jsp">Appointments</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="medical_history.jsp">Medical History</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="transaction_record.jsp">Transaction Records</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="profile.jsp">My Account</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout.jsp">Logout</a>
                </li>
            </ul>
        </nav>

        <!-- Table Section -->
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th scope="col">Doctor</th>
                    <th scope="col">Patient</th>
                    <th scope="col">Appointment Date</th>
                    <th scope="col">Start Time</th>
                    <th scope="col">End Time</th>
                    <th scope="col">Amount (USD)</th>
                    <th scope="col">Status</th>
                    <th scope="col">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${transactions}" var="transaction">
                    <tr>
                        <td>${transaction.doctorName}</td>
                        <td>${transaction.patientName}</td>
                        <td>${transaction.appointmentDate}</td>
                        <td>${transaction.startTime}</td>
                        <td>${transaction.endTime}</td>
                        <td>${transaction.amount}</td>
                        <td>${transaction.status == 'PAID' ? 'Paid' : 'Unpaid'}</td>
                        <td>
                            <form action="change_status/${transaction.id}" method="post" style="display: inline;">
                                <button type="submit" class="btn btn-outline-secondary" name="status" value="PAID" ${transaction.status == 'PAID' ? 'disabled' : ''}>
                                    Mark as Paid
                                </button>
                                <button type="submit" class="btn btn-outline-secondary" name="status" value="UNPAID" ${transaction.status == 'UNPAID' ? 'disabled' : ''}>
                                    Mark as Unpaid
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
