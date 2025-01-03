package com.hpms.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CreateAppointmentController")
public class CreateAppointmentController extends HttpServlet {
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/hpms", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Database connection error", e);
        }
    }

    private void closeConnection(Connection conn, PreparedStatement stmt) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String doctorName = request.getParameter("doctorName");
        String patientName = request.getParameter("patientName");
        String date = request.getParameter("date");
        String time = request.getParameter("time");
        String status = request.getParameter("status");

        Connection conn = null;
        PreparedStatement stmt = null;
        HttpSession session = request.getSession();

        try {
            conn = getConnection();
            String sql = "INSERT INTO appointment (doctorName, patientName, date, time, status) VALUES (?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, doctorName);
            stmt.setString(2, patientName);
            stmt.setString(3, date);
            stmt.setString(4, time);
            stmt.setString(5, status);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                session.setAttribute("successMessage", "Appointment successfully created!");
            } else {
                session.setAttribute("errorMessage", "Failed to create appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
        } finally {
            closeConnection(conn, stmt);
        }

        response.sendRedirect(request.getContextPath() + "/appointment_listing");
    }
}