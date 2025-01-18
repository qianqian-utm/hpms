package com.hpms.service;

import com.hpms.model.Appointment;
import com.hpms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class AppointmentJdbcService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // RowMapper to convert ResultSet to Appointment entity
    private RowMapper<Appointment> appointmentRowMapper = new RowMapper<Appointment>() {
        @Override
        public Appointment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getLong("id"));
            appointment.setDoctor(new User(rs.getLong("doctor_id")));  // Assume doctor_id is a field in appointment
            appointment.setPatient(new User(rs.getLong("patient_id")));  // Same for patient
            appointment.setAppointmentDate(rs.getDate("appointment_date"));
            appointment.setAppointmentStatus(rs.getString("status"));
            return appointment;
        }
    };

    // Find appointments by doctor ID
    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        String sql = "SELECT * FROM appointments WHERE doctor_id = ?";
        return jdbcTemplate.query(sql, new Object[]{doctorId}, appointmentRowMapper);
    }

    // Find appointments by patient ID
    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        return jdbcTemplate.query(sql, new Object[]{patientId}, appointmentRowMapper);
    }

    // Find appointments by appointment date
    public List<Appointment> getAppointmentsByDate(java.util.Date appointmentDate) {
        String sql = "SELECT * FROM appointments WHERE appointment_date = ?";
        return jdbcTemplate.query(sql, new Object[]{appointmentDate}, appointmentRowMapper);
    }

    // Create a new appointment
    public void createAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_date, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, appointment.getDoctor().getId(), appointment.getPatient().getId(),
                appointment.getAppointmentDate(), appointment.getAppointmentStatus());
    }

    // Delete an appointment
    public void deleteAppointment(Long appointmentId) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        jdbcTemplate.update(sql, appointmentId);
    }
}
