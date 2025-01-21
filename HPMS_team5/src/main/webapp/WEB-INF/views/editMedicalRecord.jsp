<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
<title>Edit Medical Record</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<main class="col-md-10 ms-sm-auto px-4">
				<h2>Edit Medical Record</h2>
				<form
					action="<c:url value='/medicalRecords/edit/${medicalRecord.id}'/>"
					method="post">
					<div class="form-group">
						<label for="patient">Patient</label> <select name="patient.id"
							class="form-control" required>
							<c:forEach var="patient" items="${patients}">
								<option value="${patient.id}"
									${patient.id == medicalRecord.patient.id ? 'selected' : ''}>
									${patient.firstName} ${patient.lastName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="doctor">Doctor</label> <select name="doctor.id"
							class="form-control" required>
							<c:forEach var="doctor" items="${doctors}">
								<option value="${doctor.id}"
									${doctor.id == medicalRecord.doctor.id ? 'selected' : ''}>
									${doctor.firstName} ${doctor.lastName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="date">Date</label> <input type="date" name="date"
							class="form-control" value="${medicalRecord.date}" required>
					</div>
					<div class="form-group">
						<label for="description">Description</label>
						<textarea name="description" class="form-control" required>${medicalRecord.description}</textarea>
					</div>
					<button type="submit" class="btn btn-primary">Update
						Record</button>
				</form>
			</main>
		</div>
	</div>
</body>
</html>