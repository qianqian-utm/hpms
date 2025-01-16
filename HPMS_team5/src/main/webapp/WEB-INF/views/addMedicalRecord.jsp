<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Add Medical Record</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
    <div class="container">
        <h2>Add Medical Record</h2>
        <form action="<c:url value='/medicalRecords/add'/>" method="post">
            <div class="form-group">
                <label for="patient">Patient</label>
                <select name="patient.id" class="form-control" required>
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient.id}">${patient.firstName} ${patient.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="doctor">Doctor</label>
                <select name="doctor.id" class="form-control" required>
                    <c:forEach var="doctor" items="${doctors}">
                        <option value="${doctor.id}">${doctor.firstName} ${doctor.lastName}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" name="date" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea name="description" class="form-control" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Add Record</button>
        </form>
    </div>
</body>
</html>
