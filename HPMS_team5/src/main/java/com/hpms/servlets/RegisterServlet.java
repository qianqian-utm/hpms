package com.hpms.servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.hpms.model.User;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String phoneNumber = request.getParameter("phone_number");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int role = 1; // Default role for new users

        // Validate passwords
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register?error=Passwords do not match");
            return;
        }

        // Create User object
        User user = new User(firstName, lastName, email, password, phoneNumber, role, gender);

        // Save the user (mocked; actual implementation requires database logic)
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        // Redirect to login page
        response.sendRedirect("login?message=Registration successful");
    }
}