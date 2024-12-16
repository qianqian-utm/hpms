<!DOCTYPE html>
<html>
<head>
    <title>Doctor Availability</title>
</head>
<body>
    <h1>Doctor Availability</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Date</th>
                <th>Time</th>
                <th>Doctor Name</th>
                <th>Patient Name</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="availability" items="${availabilities}">
                <tr>
                    <td>${availability.date}</td>
                    <td>${availability.time}</td>
                    <td>${availability.doctorName}</td>
                    <td>${availability.patientName}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>