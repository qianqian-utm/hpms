<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<title>Appointment Listing</title>
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
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h2>Appointments</h2>
					<div class="btn-toolbar mb-2 mb-md-0">
						<sec:authorize access="hasRole('USER') or hasRole('ADMIN')">
							<a href="<c:url value='/appointments/add'/>"
								class="btn btn-primary">Add Appointment</a>
						</sec:authorize>
					</div>
				</div>

				<c:if test="${param.error != null}">
					<div class="alert alert-danger">${param.error}</div>
				</c:if>
				<c:if test="${param.success != null}">
					<div class="alert alert-success">${param.success}</div>
				</c:if>

				<div class="table-responsive">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Doctor Name</th>
								<th>Appointment Date</th>
								<th>Start Time</th>
								<th>End Time</th>
								<th>Patient Name</th>
								<th>Status</th>
								<th>Type</th>
								<th>Remarks</th>
								<th>Payment status</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${appointments}" var="appointment">
								<tr>
									<td>${appointment.doctor.firstName}
										${appointment.doctor.lastName}</td>
									<td>${appointment.appointmentDate}</td>
									<td>${appointment.startTime}</td>
									<td>${appointment.endTime}</td>
									<td>${appointment.patient.firstName}
										${appointment.patient.lastName}</td>
									<td><c:choose>
											<c:when test="${appointment.appointmentStatus == 1}">Created</c:when>
											<c:when test="${appointment.appointmentStatus == 2}">Confirmed</c:when>
											<c:when test="${appointment.appointmentStatus == 3}">Cancelled</c:when>
											<c:when test="${appointment.appointmentStatus == 4}">Completed</c:when>
										</c:choose></td>
									<td><c:choose>
											<c:when test="${appointment.appointmentType == 1}">New appointment</c:when>
											<c:when test="${appointment.appointmentType == 2}">Review</c:when>
										</c:choose></td>
									<td>${appointment.remarks}</td>
									<td><c:choose>
											<c:when test="${appointment.transactionRecord == null}">-</c:when>
											<c:when test="${appointment.transactionRecord != null}">
												${appointment.transactionRecord.transactionStatus}
											</c:when>
										</c:choose></td>
									<td><sec:authorize access="hasRole('ADMIN')">
											<a
												href="<c:url value='/appointments/view/${appointment.id}'/>"
												class="btn btn-primary btn-sm"> <i
												class="bi bi-eye-fill"></i>
											</a>
											<a
												href="<c:url value='/appointments/edit/${appointment.id}'/>"
												class="btn btn-warning btn-sm"> <i
												class="bi bi-pencil-fill"></i>
											</a>
											<a
												href="<c:url value='/appointments/delete/${appointment.id}'/>"
												class="btn btn-danger btn-sm"
												onclick="return confirm('Are you sure you want to delete this appointment?')"><i
												class="bi bi-trash-fill"></i></a>
										</sec:authorize> <sec:authorize access="hasRole('USER')">
											<a
												href="<c:url value='/appointments/view/${appointment.id}'/>"
												class="btn btn-primary btn-sm"> <i
												class="bi bi-eye-fill"></i>
											</a>
											<c:if
												test="${appointment.appointmentStatus != 3 && appointment.appointmentStatus != 4}">
												<button type="button" class="btn btn-danger btn-sm"
													data-bs-toggle="modal"
													data-bs-target="#cancelModal${appointment.id}">
													<i class="bi bi-x-circle"></i> Cancel
												</button>
											</c:if>
										</sec:authorize></td>
								</tr>

								<sec:authorize access="hasRole('USER')">
									<div class="modal fade" id="cancelModal${appointment.id}"
										tabindex="-1">
										<div class="modal-dialog">
											<div class="modal-content">
												<div class="modal-header">
													<h5 class="modal-title">Cancel Appointment</h5>
													<button type="button" class="btn-close"
														data-bs-dismiss="modal"></button>
												</div>
												<div class="modal-body">Are you sure you want to
													cancel this appointment?</div>
												<div class="modal-footer">
													<button type="button" class="btn btn-secondary"
														data-bs-dismiss="modal">No</button>
													<a
														href="<c:url value='/appointments/cancel/${appointment.id}'/>"
														class="btn btn-danger">Yes, Cancel</a>
												</div>
											</div>
										</div>
									</div>
								</sec:authorize>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</main>
		</div>
	</div>
</body>
</html>