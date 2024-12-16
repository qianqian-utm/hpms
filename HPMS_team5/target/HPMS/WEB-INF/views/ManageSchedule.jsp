<!DOCTYPE html>
<html>
<head>
    <title>Manage Schedule</title>
</head>
<body>
    <h1>Manage Schedule</h1>
    <div>
        <button onclick="window.location.href='NewAppointment.jsp'">+ New Appointment</button>
    </div>
    <form method="get" action="ManageScheduleController">
        <label for="doctorName">Doctor Name:</label>
        <input type="text" id="doctorName" name="doctorName">

        <label for="date">Date:</label>
        <input type="date" id="date" name="date">

        <label for="time">Time:</label>
        <input type="time" id="time" name="time">

        <label for="patientName">Patient Name:</label>
        <input type="text" id="patientName" name="patientName">

        <label for="status">Status:</label>
        <select id="status" name="status">
            <option value="Confirmed">Confirmed</option>
            <option value="Complete">Complete</option>
        </select>

        <button type="submit">Search</button>
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>Doctor Name</th>
                <th>Date</th>
                <th>Time</th>
                <th>Patient Name</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="schedule" items="${schedules}">
                <tr>
                    <td>${schedule.doctorName}</td>
                    <td>${schedule.date}</td>
                    <td>${schedule.time}</td>
                    <td>${schedule.patientName}</td>
                    <td>${schedule.status}</td>
                    <td>
                        <form method="post" action="EditAppointmentController">
                            <input type="hidden" name="id" value="${schedule.id}">
                            <button type="submit">Edit</button>
                        </form>
                        <form method="post" action="DeleteAppointmentController">
                            <input type="hidden" name="id" value="${schedule.id}">
                            <button type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>