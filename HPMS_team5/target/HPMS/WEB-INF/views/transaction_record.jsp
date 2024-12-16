<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction Records</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
        }
        .container {
            width: 80%;
            margin: 40px auto;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            overflow: hidden;
        }
        .header {
            background: #007bff;
            color: #fff;
            padding: 20px;
            text-align: center;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .content {
            padding: 20px;
        }
        .nav {
            display: flex;
            flex-direction: column;
            width: 20%;
            background: #f1f1f1;
            padding: 20px;
            float: left;
            height: 100%;
        }
        .nav a {
            text-decoration: none;
            padding: 10px 15px;
            color: #333;
            display: block;
            margin-bottom: 10px;
            border-radius: 4px;
        }
        .nav a:hover {
            background: #007bff;
            color: #fff;
        }
        .main {
            margin-left: 22%;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table th, table td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        table th {
            background-color: #f4f4f4;
        }
        .save-button {
            display: inline-block;
            background-color: #007bff;
            color: #fff;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            text-align: center;
            cursor: pointer;
        }
        .save-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Transaction Records</h1>
        </div>
        <div class="nav">
            <a href="appointments.jsp">Appointments</a>
            <a href="medical_history.jsp">Medical History</a>
            <a href="transaction_record.jsp" style="background-color: #007bff; color: white;">Transaction Records</a>
            <a href="profile.jsp">My Account</a>
            <a href="logout.jsp">Logout</a>
        </div>
        <div class="main">
            <table>
                <thead>
                    <tr>
                        <th>Doctor</th>
                        <th>Patient</th>
                        <th>Appointment Date</th>
                        <th>Start Time</th>
                        <th>End Time</th>
                        <th>Amount (USD)</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="transaction" items="${sessionScope.transactionList}">
                        <tr>
                            <td>${transaction.appointment.doctor.first_name} ${transaction.appointment.doctor.last_name}</td>
                            <td>${transaction.appointment.patient.first_name} ${transaction.appointment.patient.last_name}</td>
                            <td>${transaction.appointment.appointment_date}</td>
                            <td>${transaction.appointment.start_time}</td>
                            <td>${transaction.appointment.end_time}</td>
                            <td>${transaction.amount}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button class="save-button">Save Changes</button>
        </div>
    </div>
</body>
</html>
