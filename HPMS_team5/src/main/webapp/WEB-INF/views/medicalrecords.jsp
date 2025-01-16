<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Medical Records</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>">
</head>
<body>
    <div class="container">
        <h2>Medical Records</h2>
        <a href="<c:url value='/medicalRecords/add'/>" class="btn btn-primary">Add Medical Record</a>
        <table class="table table-bordered mt-3">
            <thead>
                <tr>
                    <th>Patient</th>
                    <th>Doctor</th>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="record" items="${medicalRecords}">
                    <tr>
                        <td>${record.patient.firstName} ${record.patient.lastName}</td>
                        <td>${record.doctor.firstName} ${record.doctor.lastName}</td>
                        <td>${record.date}</td>
                        <td>${record.description}</td>
                        <td>
                            <a href="<c:url value='/medicalRecords/edit/${record.id}'/>" class="btn btn-secondary">Edit</a>
                            <a href="<c:url value='/medicalRecords/delete/${record.id}'/>" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
