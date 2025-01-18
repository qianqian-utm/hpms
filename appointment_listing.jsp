<!DOCTYPE html>
<html>
<head>
    <title>Appointment Listing</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-2 min-vh-100 bg-light">
                <jsp:include page="sidebar.jsp" />
            </div>

            <!-- Main Content -->
            <div class="col-md-10 p-4">
                <h2>Appointments</h2>
                
                <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#addAppointmentModal">Add Appointment</button>

                <!-- Search Form -->
                <div class="card mb-3">
                    <div class="card-body">
                        <form action="/appointments/search" method="GET" class="row g-3">
                            <div class="col-md-3">
                                <label for="searchDoctor" class="form-label">Doctor</label>
                                <select name="doctor" id="searchDoctor" class="form-select">
                                    <option value="">All Doctors</option>
                                    <c:forEach items="${doctors}" var="doctor">
                                        <option value="${doctor.id}">${doctor.firstName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label for="searchPatient" class="form-label">Patient</label>
                                <input type="text" name="patient" id="searchPatient" class="form-control" placeholder="Patient Name">
                            </div>
                            <div class="col-md-2">
                                <label for="searchDate" class="form-label">Date</label>
                                <input type="date" name="date" id="searchDate" class="form-control">
                            </div>
                            <div class="col-md-2">
                                <label for="searchStatus" class="form-label">Status</label>
                                <select name="status" id="searchStatus" class="form-select">
                                    <option value="">All Status</option>
                                    <option value="newpatients">New Patients</option>
                                    <option value="review">Review</option>
                                </select>
                            </div>
                            <div class="col-md-2 d-flex align-items-end">
                                <button type="submit" class="btn btn-primary w-100">Search</button>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- Modal for adding appointment -->
                <div class="modal fade" id="addAppointmentModal" tabindex="-1" aria-labelledby="addAppointmentModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="addAppointmentModalLabel">Add Appointment</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form method="post" action="/appointments/add">
                                <div class="modal-body">
                                    <!-- Form Fields: Doctor, Patient, Date, Time, Status, Remarks -->
                                    <div class="mb-3">
                                        <label for="doctor" class="form-label">Doctor</label>
                                        <select name="doctor" class="form-select">
                                            <c:forEach items="${doctors}" var="doctor">
                                                <option value="${doctor.id}">${doctor.firstName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="patient" class="form-label">Patient</label>
                                        <select name="patient" class="form-select">
                                            <c:forEach items="${patients}" var="patient">
                                                <option value="${patient.id}">${patient.firstName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <!-- Date and Time input -->
                                    <div class="mb-3">
                                        <label for="appointmentDate" class="form-label">Appointment Date</label>
                                        <input type="date" name="appointmentDate" class="form-control" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="appointmentTime" class="form-label">Appointment Time</label>
                                        <input type="time" name="appointmentTime" class="form-control" required />
                                    </div>
                                    <div class="mb-3">
                                        <label for="status" class="form-label">Status</label>
                                        <select name="status" class="form-select">
                                            <option value="newpatients">New Patients</option>
                                            <option value="review">Review</option>
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="remarks" class="form-label">Remarks</label>
                                        <input type="text" name="remarks" class="form-control" maxlength="20" />
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                    <button type="submit" class="btn btn-primary">Save Appointment</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Doctor Name</th>
                                <th>Appointment Date</th>
                                <th>Appointment Time</th>
                                <th>Patient Name</th>
                                <th>Status</th>
                                <th>Remarks</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${appointments}" var="appointment">
                                <tr>
                                    <td>${appointment.doctor.firstName}</td>
                                    <td>${appointment.appointmentDate}</td>
                                    <td>${appointment.appointmentTime}</td>
                                    <td>${appointment.patient.firstName}</td>
                                    <td>${appointment.status}</td>
                                    <td>${appointment.remarks}</td>
                                    <td>
                                        <a href="/appointments/edit/${appointment.id}" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="/appointments/delete/${appointment.id}" class="btn btn-danger btn-sm">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js"></script>
</body>
</html>