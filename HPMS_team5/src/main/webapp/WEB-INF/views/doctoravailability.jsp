<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Doctor Availability</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.0/main.min.css"
	rel="stylesheet">

<style>
.sidebar {
	height: 100vh;
	position: fixed;
	top: 0;
	left: 0;
	padding-top: 60px;
	background-color: #343a40;
	width: 250px;
}

.main-content {
	margin-left: 250px;
	padding: 20px;
}

.calendar-container {
	background: #fff;
	padding: 20px;
	border-radius: 8px;
	box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
}

.doctor-filter {
	margin-bottom: 20px;
}

.fc-event.appointment {
	background-color: #dc3545 !important;
	border-color: #dc3545 !important;
	color: white !important;
}

.fc-event.available {
	background-color: #0d6efd !important;
	border-color: #0d6efd !important;
	color: white !important;
}

.fc-event.holiday {
	background-color: #6c757d !important;
	border-color: #6c757d !important;
	opacity: 0.7;
	color: white !important;
}

.legend {
	margin-top: 20px;
	padding: 10px;
	border-radius: 4px;
	background: #f8f9fa;
}

.legend-item {
	display: inline-block;
	margin-right: 20px;
}

.legend-color {
	display: inline-block;
	width: 20px;
	height: 20px;
	margin-right: 5px;
	vertical-align: middle;
	border-radius: 3px;
}
</style>
</head>
<body>

	<div class="container-fluid">
		<div class="row">
			<%@ include file="sidebar.jsp"%>
			<main class="col-md-10 ms-sm-auto px-4">
				<div
					class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">Doctor Availability Calendar</h1>
				</div>

				<!-- Doctor Filter -->
				<div class="doctor-filter">
					<div class="row">
						<div class="col-md-4">
							<select class="form-select" id="doctorSelect">
								<option value="">All Doctors</option>
								<c:forEach items="${users}" var="doctor">
									<option value="${doctor.id}">${doctor.firstName}
										${doctor.lastName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>

				<!-- Calendar Container -->
				<div class="calendar-container">
					<div id="calendar"></div>
				</div>

				<!-- Legend -->
				<div class="legend">
					<div class="legend-item">
						<span class="legend-color" style="background-color: #dc3545;"></span>
						<span>Appointment</span>
					</div>
					<div class="legend-item">
						<span class="legend-color" style="background-color: #0d6efd;"></span>
						<span>Available</span>
					</div>
					<div class="legend-item">
						<span class="legend-color"
							style="background-color: #6c757d; opacity: 0.7;"></span> <span>Public
							Holiday</span>
					</div>
				</div>
			</main>
		</div>
	</div>
	<!-- JavaScript Dependencies -->
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.0/main.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js"></script>

	<!-- Calendar Initialization Script -->
	<script>
document.addEventListener('DOMContentLoaded', function() {
    const calendarEl = document.getElementById('calendar');
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        themeSystem: 'bootstrap5',
        height: 'auto',
        events: function(info, successCallback, failureCallback) {
            const doctorId = $('#doctorSelect').val();
            
            $.ajax({
                url: '${pageContext.request.contextPath}/api/availability',
                method: 'GET',
                data: {
                    start: info.startStr,
                    end: info.endStr,
                    doctorId: doctorId
                },
                success: function(response) {
                    const events = [];
                    
                    // Process appointments
                    response.appointments.forEach(appointment => {
                        events.push({
                            title: `Appointment: ${appointment.patientName}`,
                            start: `${appointment.appointmentDate}T${appointment.appointmentTime}`,
                            className: 'appointment',
                            extendedProps: {
                                type: 'appointment',
                                doctor: `${appointment.user.firstName} ${appointment.user.lastName}`,
                                patient: appointment.patientName
                            }
                        });
                    });
                    
                    // Process available slots
                    response.availableSlots.forEach(slot => {
                        events.push({
                            title: 'Available',
                            start: slot.start,
                            end: slot.end,
                            className: 'available',
                            extendedProps: {
                                type: 'available'
                            }
                        });
                    });
                    
                    // Process holidays
                    response.holidays.forEach(holiday => {
                        events.push({
                            title: holiday.name,
                            start: holiday.date,
                            allDay: true,
                            className: 'holiday',
                            extendedProps: {
                                type: 'holiday'
                            }
                        });
                    });
                    
                    successCallback(events);
                },
                error: function(err) {
                    console.error('Error fetching calendar data:', err);
                    failureCallback(err);
                }
            });
        },
        eventDidMount: function(info) {
            let tooltipContent = '';
            
            if (info.event.extendedProps.type === 'appointment') {
                tooltipContent = `
                    <div>
                        <strong>Doctor:</strong> ${info.event.extendedProps.doctor}<br>
                        <strong>Patient:</strong> ${info.event.extendedProps.patient}
                    </div>
                `;
            }
            
            if (tooltipContent) {
                $(info.el).tooltip({
                    title: tooltipContent,
                    html: true,
                    placement: 'top',
                    container: 'body'
                });
            }
        },
        slotMinTime: '08:00:00',
        slotMaxTime: '18:00:00',
        weekends: false,
        businessHours: {
            daysOfWeek: [1, 2, 3, 4, 5],
            startTime: '09:00',
            endTime: '17:00',
        }
    });
    
    calendar.render();
    
    // Handle doctor selection change
    $('#doctorSelect').change(function() {
        calendar.refetchEvents();
    });
});
</script>

</body>
</html>