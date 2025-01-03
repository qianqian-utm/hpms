package com.hpms.controller;

import com.google.protobuf.TextFormat.ParseException;
import com.hpms.model.Schedule; // Import the new AppointmentModel class

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/appointments")
public class AppointmentController extends HttpServlet {
    private static final String dbURL = "jdbc:mysql://localhost:3306/hpms";
    private static final String dbUsername = "root";
    private static final String dbPassword = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String doctorName = request.getParameter("doctorName");
        String Date = request.getParameter("Date");
        String time = request.getParameter("time");
        String patientName = request.getParameter("patientName");
        String status = request.getParameter("status");

        // Create a list to hold the filtered schedule records
        List<Schedule> filteredSchedules = new ArrayList<>();

        // SQL query to filter appointments
        String query = "SELECT * FROM appointments WHERE doctor_name LIKE ? AND `Date` = ? AND time LIKE ? AND patient_name LIKE ? AND status LIKE ?";

        try (Connection connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            // Prepare the SQL statement
            PreparedStatement stmt = connection.prepareStatement(query);

            // Set the parameters for the query
            stmt.setString(1, "%" + (doctorName != null ? doctorName : "") + "%");
            stmt.setString(2, (Date != null && !Date.trim().isEmpty()) ? Date : "%");
            stmt.setString(3, "%" + (time != null ? time : "") + "%");
            stmt.setString(4, "%" + (patientName != null ? patientName : "") + "%");
            stmt.setString(5, "%" + (status != null ? status : "") + "%");

            // Execute the query and get the results
            ResultSet rs = stmt.executeQuery();

            // Process the result set and create Schedule objects
            while (rs.next()) {
                Schedule schedule = new Schedule(
                        rs.getInt("id"),
                        rs.getString("doctor_name"),
                        rs.getString("Date"),
                        rs.getString("time"),
                        rs.getString("patient_name"),
                        rs.getString("status")
                );
                filteredSchedules.add(schedule);
            }

            // If no schedules were found, set a message
            if (filteredSchedules.isEmpty()) {
                request.setAttribute("message", "No appointments found matching your search criteria.");
            }

            // Set the filtered schedules as a request attribute
            request.setAttribute("schedules", filteredSchedules);

            // Forward the request to the JSP page to display the results
            RequestDispatcher dispatcher = request.getRequestDispatcher("/appointment_listing.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            // Handle any SQL exceptions
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching the appointment data.");
            request.getRequestDispatcher("/error_page.jsp").forward(request, response);
        }
    }
}
