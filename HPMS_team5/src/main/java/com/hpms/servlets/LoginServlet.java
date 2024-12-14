package com.hpms.servlets;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;
import com.hpms.model.User;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve login parameters
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Mock user retrieval
        // Role: 1=Admin, 2=Patient
        ArrayList<User> validUsers = new ArrayList<>();
        validUsers.add(new User("Jane", "Doe", "admin@hpms.com", "adminuser123", "1234567890", 1, 1));
        validUsers.add(new User("John", "Doe", "user@hpms.com", "password123", "1234567890", 1, 2));

        // Validate user credentials
        boolean isValidUser = false;
        User loggedInUser = null;

        for (User user : validUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                isValidUser = true;
                loggedInUser = user;
                break;
            }
        }

        if (isValidUser) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", loggedInUser);
            if(loggedInUser.getRole() == 1 ) {
            	response.sendRedirect("userlisting");
            } else {
            	response.sendRedirect("appointmentlisting");
            }
            
        } else {
            response.sendRedirect("login?error=Invalid credentials");
        }
    }
}