package com.hpms.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // Database connection details
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/hpms"; 
    private static final String JDBC_USERNAME = "root"; 
    private static final String JDBC_PASSWORD = "";

    // Method to get database connection
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver
            return DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }

    // Create a new appointment in the database
    public boolean createAppointment(String doctorName, String patientName, String date, String time, String status) {
        String sql = "INSERT INTO appointment (doctorName, patientName, date, time, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctorName);
            stmt.setString(2, patientName);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, status);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if insertion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    // Get all appointments from the database
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointment ORDER BY date, time"; // Adjust the query based on your requirements

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Appointment appointment = new Appointment(
                    rs.getInt("id"),
                    rs.getString("doctorName"),
                    rs.getString("patientName"),
                    rs.getDate("date"),
                    rs.getTime("time"),
                    rs.getString("status")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments; // Return the list of appointments
    }

    // Get a specific appointment by ID
    public Appointment getAppointmentById(int id) {
        Appointment appointment = null;
        String sql = "SELECT * FROM appointment WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    appointment = new Appointment(
                        rs.getInt("id"),
                        rs.getString("doctorName"),
                        rs.getString("patientName"),
                        rs.getDate("date"),
                        rs.getTime("time"),
                        rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment; // Return the appointment (null if not found)
    }

    // Update an existing appointment in the database
    public boolean updateAppointment(int id, String doctorName, String patientName, String date, String time, String status) {
        String sql = "UPDATE appointment SET doctorName = ?, patientName = ?, date = ?, time = ?, status = ? WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctorName);
            stmt.setString(2, patientName);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, status);
            stmt.setInt(6, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if update was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    // Delete an appointment by ID
    public boolean deleteAppointment(int id) {
        String sql = "DELETE FROM appointment WHERE id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if deletion was successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    // Appointment model class
    public static class Appointment {
        private int id;
        private String doctorName;
        private String patientName;
        private Date date;
        private Time time;
        private String status;

        public Appointment(int id, String doctorName, String patientName, Date date, Time time, String status) {
            this.id = id;
            this.doctorName = doctorName;
            this.patientName = patientName;
            this.date = date;
            this.time = time;
            this.status = status;
        }

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDoctorName() {
            return doctorName;
        }

        public void setDoctorName(String doctorName) {
            this.doctorName = doctorName;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
