package com.hpms.servlets;

import com.hpms.model.User;
import com.hpms.service.UserService;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private UserService userService;
    private static final long serialVersionUID = 1L;
       
    public UserServlet() {
        super();
        userService = new UserService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("editAccount".equals(action)) {
            // Handle account editing
            handleEditAccount(request, response, session);
        } else {
            // Handle new user creation
            handleAddUser(request, response, session);
        }
    }

    private void handleAddUser(HttpServletRequest request, 
                                HttpServletResponse response, 
                                HttpSession session) throws IOException {
        // Collect form data for new user
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        int gender = Integer.parseInt(request.getParameter("gender"));
        int userRole = Integer.parseInt(request.getParameter("user_role"));

        // Create a new User object
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPhoneNumber(phone);
        newUser.setGender(gender);
        newUser.setRole(userRole);

        // Add user to the list
        ArrayList<User> userList = userService.addUser(newUser);
        
        // Update session with new user list
        session.setAttribute("userList", userList);
        
        // Redirect to user listing page
        response.sendRedirect("userlisting");
    }

    private void handleEditAccount(HttpServletRequest request, 
                                   HttpServletResponse response,
                                   HttpSession session) throws IOException {
        // Get the logged-in user from session
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // Update user details from form
            String firstName = request.getParameter("first_name");
            String lastName = request.getParameter("last_name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            
            // Update user details
            loggedInUser.setFirstName(firstName != null ? firstName : loggedInUser.getFirstName());
            loggedInUser.setLastName(lastName != null ? lastName : loggedInUser.getLastName());
            loggedInUser.setEmail(email != null ? email : loggedInUser.getEmail());
            loggedInUser.setPhoneNumber(phone != null ? phone : loggedInUser.getPhoneNumber());

            // Update the user in the list
            userService.updateUser(loggedInUser);
            
            // Update the session with the modified user
            session.setAttribute("loggedInUser", loggedInUser);
            
            // Set success message in session
            session.setAttribute("successMessage", "Account updated successfully!");
        }

        response.sendRedirect("editaccount");
    }
}