<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Appointment</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<main class="col-md-10 ms-sm-auto px-4">
				<div class="container mt-5">
					<h3 class="mb-4">Add Appointment</h3>

					<c:if test="${error != null}">
						<div class="alert alert-danger">${error}</div>
					</c:if>

					<form method="post" action="<c:url value='/appointments/add'/>">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<div class="row">
							<div class="col-md-6 mb-3">
								<label for="doctor.id" class="form-label">Doctor</label> <select
									name="doctor.id" class="form-select" required>
									<option value="">Select Doctor</option>
									<c:forEach items="${doctors}" var="doctor">
										<option value="${doctor.id}">${doctor.firstName}
											${doctor.lastName}</option>
									</c:forEach>
								</select>
							</div>

							<sec:authorize access="hasRole('ADMIN')">
								<div class="col-md-6 mb-3">
									<label for="patient.id">Patient</label> <select
										name="patient.id" class="form-select" required>
										<option value="">Select Patient</option>
										<c:forEach items="${patients}" var="patient">
											<option value="${patient.id}">${patient.firstName}
												${patient.lastName}</option>
										</c:forEach>
									</select>
								</div>
							</sec:authorize>

							<sec:authorize access="!hasRole('ADMIN')">
								<input type="hidden" name="patient.id" value="${currentUser.id}" />
							</sec:authorize>
						</div>

						<div class="row">
							<div class="col-md-4 mb-3">
								<label for="appointmentDate" class="form-label">Appointment
									Date</label> <input type="date" name="appointmentDate"
									class="form-control" required min="${LocalDate.now()}" />
							</div>

							<div class="col-md-4 mb-3">
								<label for="startTime" class="form-label">Start Time</label> <input
									type="time" name="startTime" class="form-control" required
									step="900" />
							</div>

							<div class="col-md-4 mb-3">
								<label for="endTime" class="form-label">End Time</label> <input
									type="time" name="endTime" class="form-control" required
									step="900" />
							</div>
						</div>

						<div class="row">

							<sec:authorize access="hasRole('ADMIN')">
								<div class="col-md-6 mb-3">
									<label for="appointmentStatus" class="form-label">Status</label>
									<select name="appointmentStatus" class="form-select" required>
										<option value="1">Created</option>
										<option value="2">Confirmed</option>
										<option value="3">Cancelled</option>
										<option value="4">Completed</option>
									</select>
								</div>
							</sec:authorize>

							<div class="col-md-6 mb-3">
								<label for="appointmentType" class="form-label">Type</label> <select
									name="appointmentType" class="form-select" required>
									<option value="1">New Patient</option>
									<option value="2">Review</option>
								</select>
							</div>
						</div>

						<div class="mb-3">
							<label for="remarks" class="form-label">Remarks</label>
							<textarea name="remarks" class="form-control" maxlength="200"
								rows="3"></textarea>
						</div>

						<div class="d-flex justify-content-between mt-4">
							<a href="${pageContext.request.contextPath}/appointments"
								class="btn btn-secondary">Cancel</a>
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</form>
				</div>
			</main>
		</div>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>