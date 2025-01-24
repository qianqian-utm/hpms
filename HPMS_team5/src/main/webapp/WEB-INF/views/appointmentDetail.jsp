<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<title>Appointment Details</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<main class="col-md-10 ms-sm-auto px-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h2>Appointment Details</h2>
					<div class="btn-toolbar mb-2 mb-md-0">
						<a href="<c:url value='/appointments'/>" class="btn btn-secondary">Back
							to List</a>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="card mb-4">
							<div class="card-header">
								<h5 class="card-title">Appointment Information</h5>
							</div>
							<div class="card-body">
								<dl class="row">
									<dt class="col-sm-4">Doctor:</dt>
									<dd class="col-sm-8">${appointment.doctor.firstName}
										${appointment.doctor.lastName}</dd>

									<dt class="col-sm-4">Patient:</dt>
									<dd class="col-sm-8">${appointment.patient.firstName}
										${appointment.patient.lastName}</dd>

									<dt class="col-sm-4">Date:</dt>
									<dd class="col-sm-8">${appointment.appointmentDate}</dd>

									<dt class="col-sm-4">Time:</dt>
									<dd class="col-sm-8">${appointment.startTime}-
										${appointment.endTime}</dd>

									<dt class="col-sm-4">Status:</dt>
									<dd class="col-sm-8">
										<c:choose>
											<c:when test="${appointment.appointmentStatus == 1}">Created</c:when>
											<c:when test="${appointment.appointmentStatus == 2}">Confirmed</c:when>
											<c:when test="${appointment.appointmentStatus == 3}">Cancelled</c:when>
											<c:when test="${appointment.appointmentStatus == 4}">Completed</c:when>
										</c:choose>
									</dd>

									<dt class="col-sm-4">Type:</dt>
									<dd class="col-sm-8">
										<c:choose>
											<c:when test="${appointment.appointmentType == 1}">New Patient</c:when>
											<c:when test="${appointment.appointmentType == 2}">Review</c:when>
										</c:choose>
									</dd>

									<dt class="col-sm-4">Remarks:</dt>
									<dd class="col-sm-8">${appointment.remarks}</dd>
								</dl>
							</div>
						</div>
					</div>

					<div class="col-md-6">
						<div class="card">
							<div
								class="card-header d-flex justify-content-between align-items-center">
								<h5 class="card-title mb-0">Medical Record</h5>
								<c:if
									test="${canEditMedicalRecord && appointment.appointmentStatus != 3}">
									<c:choose>
										<c:when test="${medicalRecord == null}">
											<a
												href="<c:url value='/medicalrecords/add/${appointment.id}'/>"
												class="btn btn-primary btn-sm">Add Medical Record
											</a>
										</c:when>
										<c:otherwise>
											<a
												href="<c:url value='/medicalrecords/edit/${appointment.id}'/>"
												class="btn btn-primary btn-sm">Edit Medical Record
											</a>
										</c:otherwise>
									</c:choose>
								</c:if>
							</div>
							<div class="card-body">
								<c:choose>
									<c:when test="${medicalRecord != null}">
										<dl class="row">
											<dt class="col-sm-4">Date:</dt>
											<dd class="col-sm-8">${medicalRecord.date}</dd>

											<dt class="col-sm-4">Description:</dt>
											<dd class="col-sm-8">${medicalRecord.description}</dd>
										</dl>
									</c:when>
									<c:otherwise>
										<p class="text-muted">No medical record available.</p>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>