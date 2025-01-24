<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${medicalRecord == null ? 'Add' : 'Edit'} Medical Record</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <%@ include file="sidebar.jsp"%>
            <main class="col-md-10 ms-sm-auto px-4">
                <div class="container mt-5">
                    <h3 class="mb-4">${medicalRecord == null ? 'Add' : 'Edit'} Medical Record</h3>

                    <c:if test="${error != null}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form method="post" action="<c:url value='/medicalrecords/${medicalRecord == null ? "add" : "edit/".concat(medicalRecord.id)}'/>" >
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <input type="hidden" name="appointment.id" value="${appointment.id}" />

                        <div class="card mb-4">
                            <div class="card-header">
                                <h5 class="card-title">Appointment Details</h5>
                            </div>
                            <div class="card-body">
                                <dl class="row">
                                    <dt class="col-sm-3">Patient:</dt>
                                    <dd class="col-sm-9">${appointment.patient.firstName} ${appointment.patient.lastName}</dd>
                                    
                                    <dt class="col-sm-3">Date:</dt>
                                    <dd class="col-sm-9">${appointment.appointmentDate}</dd>
                                </dl>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="date" class="form-label">Record Date</label>
                            <input type="date" name="date" class="form-control" required 
                                value="${medicalRecord != null ? medicalRecord.date : appointment.appointmentDate}" />
                        </div>

                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea name="description" class="form-control" rows="6" maxlength="2000" required>${medicalRecord != null ? medicalRecord.description : ''}</textarea>
                        </div>

                        <div class="d-flex justify-content-between mt-4">
                            <a href="${pageContext.request.contextPath}/appointments/view/${appointment.id}" 
                               class="btn btn-secondary">Cancel</a>
                            <button type="submit" class="btn btn-primary">Save Medical Record</button>
                        </div>
                    </form>
                </div>
            </main>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>