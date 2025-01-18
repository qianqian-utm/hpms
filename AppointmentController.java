package com.hpms.controller;

import com.hpms.model.Appointment;
import com.hpms.service.AppointmentJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentJdbcService appointmentJdbcService;

    // Endpoint to get appointments by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        // Call the service method to fetch appointments by doctor ID
        return appointmentJdbcService.getAppointmentsByDoctor(doctorId);
    }

    // Endpoint to get appointments by patient ID
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable Long patientId) {
        // Call the service method to fetch appointments by patient ID
        return appointmentJdbcService.getAppointmentsByPatient(patientId);
    }

    // Endpoint to get appointments by appointment date
    @GetMapping("/date/{appointmentDate}")
    public List<Appointment> getAppointmentsByDate(@PathVariable String appointmentDate) {
        // Convert the String to Date format (adjust according to your date format)
        java.util.Date date = java.sql.Date.valueOf(appointmentDate);
        return appointmentJdbcService.getAppointmentsByDate(date);
    }

    // Endpoint to create a new appointment
    @PostMapping("/create")
    public String createAppointment(@RequestBody Appointment appointment) {
        try {
            // Call the service method to create the appointment
            appointmentJdbcService.createAppointment(appointment);
            return "Appointment created successfully!";
        } catch (Exception e) {
            return "Error creating appointment: " + e.getMessage();
        }
    }

    // Endpoint to delete an appointment
    @DeleteMapping("/delete/{appointmentId}")
    public String deleteAppointment(@PathVariable Long appointmentId) {
        try {
            // Call the service method to delete the appointment by its ID
            appointmentJdbcService.deleteAppointment(appointmentId);
            return "Appointment deleted successfully!";
        } catch (Exception e) {
            return "Error deleting appointment: " + e.getMessage();
        }
    }
}
