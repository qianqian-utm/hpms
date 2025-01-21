<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
<title>Medical Records</title>
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
				<h2>Medical Records</h2>
				<a href="<c:url value='/medicalRecords/add'/>"
					class="btn btn-primary">Add Medical Record</a>
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
								<td><a
									href="<c:url value='/medicalRecords/edit/${record.id}'/>"
									class="btn btn-secondary">Edit</a> 
									<form action="<c:url value='/medicalRecords/delete/${record.id}'/>" method="post" style="display: inline;">
										<button type="submit" class="btn btn-danger"
											onclick="return confirm('Are you sure you want to delete this record?')">
											Delete
										</button>
									</form></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</main>
		</div>
	</div>
</body>
</html>