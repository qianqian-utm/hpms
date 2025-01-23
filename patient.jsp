<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Patient Appointment Management</title>
    <link href="<%= request.getContextPath() %>/webjars/bootstrap/5.1.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
        }
        .sidebar {
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            z-index: 100;
            padding-top: 2rem;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .content-area {
            margin-left: 220px;
            padding: 20px;
        }
        .table-hover tbody tr:hover {
            background-color: #f1f3f5;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <%@ include file="sidebar.jsp" %>

            <main class="col-md-10 ms-sm-auto col-lg-10 px-md-4 content-area">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Patient Appointments</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#appointmentModal">
                            <i class="bi bi-plus-circle me-2"></i>Create Appointment
                        </button>
                    </div>
                </div>

                <div class="modal fade" id="appointmentModal" tabindex="-1" aria-labelledby="appointmentModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="modal-header bg-primary text-white">
                                <h5 class="modal-title" id="appointmentModalLabel">New Appointment</h5>
                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <form action="createAppointment" method="post"> 
                                <div class="modal-body">
                                    <div class="mb-3">
                                        <label for="user" class="form-label">Patient</label>
                                        <select id="user" name="userId" class="form-select">
                                            <option value="1">John Doe</option> 
                                            <option value="2">Jane Smith</option> 
                                        </select>
                                    </div>
                                    <div class="mb-3">
                                        <label for="appointmentDate" class="form-label">Appointment Date</label>
                                        <input type="date" id="appointmentDate" name="appointmentDate" class="form-control" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="appointmentTime" class="form-label">Appointment Time</label>
                                        <input type="time" id="appointmentTime" name="appointmentTime" class="form-control" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="remarks" class="form-label">Remarks</label>
                                        <textarea id="remarks" name="remarks" class="form-control" maxlength="200" rows="3"></textarea>
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

                <div class="card shadow-sm">
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead class="table-light">
                                <tr>
                                    <th>Patient</th>
                                    <th>Date</th>
                                    <th>Time</th>
                                    <th>Remarks</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>John Doe</td>
                                    <td>2024-12-12</td>
                                    <td>10:00 AM</td>
                                    <td>General Checkup</td>
                                    <td>
                                        <a href="#" class="btn btn-sm btn-outline-danger">
                                            <i class="bi bi-trash me-1"></i> Cancel
                                        </a>
                                        <a href="#" class="btn btn-sm btn-outline-warning">
                                            <i class="bi bi-pencil me-1"></i> Edit
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Jane Smith</td>
                                    <td>2024-12-15</td>
                                    <td>02:30 PM</td>
                                    <td>Dental Cleaning</td>
                                    <td>
                                        <a href="#" class="btn btn-sm btn-outline-danger">
                                            <i class="bi bi-trash me-1"></i> Cancel
                                        </a>
                                        <a href="#" class="btn btn-sm btn-outline-warning">
                                            <i class="bi bi-pencil me-1"></i> Edit
                                        </a>
                                    </td>
                                </tr>
                                <tr>
                                    <td>John Doe</td>
                                    <td>2024-12-20</td>
                                    <td>09:00 AM</td>
                                    <td>Follow-up</td>
                                    <td>
                                        <a href="#" class="btn btn-sm btn-outline-danger">
                                            <i class="bi bi-trash me-1"></i> Cancel
                                        </a>
                                        <a href="#" class="btn btn-sm btn-outline-warning">
                                            <i class="bi bi-pencil me-1"></i> Edit
                                        </a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="<%= request.getContextPath() %>/webjars/bootstrap/5.1.0/js/bootstrap.bundle.min.js"></script>
</body>
</html>