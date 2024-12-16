package com.hpms.servlets;

import com.hpms.model.*;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;

public class TransactionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Mock data for transaction records
        List<TransactionRecord> transactionList = new ArrayList<>();
        
        // Sample Users
        User doctor1 = new User("James", "Smith", "smith@example.com", "password123", "1234567890", 1, 1);
        User patient1 = new User("John", "Doe", "john@example.com", "pass123", "9876543210", 2, 1);
        User doctor2 = new User("Sarah", "Brown", "brown@example.com", "password456", "1112223333", 1, 2);
        User patient2 = new User("Jane", "Doe", "jane@example.com", "pass456", "9998887777", 2, 2);

        // Create sample appointments
        Appointment appt1 = new Appointment(
            doctor1, patient1, new Date(), Time.valueOf("09:00:00"), Time.valueOf("09:30:00"), 1, 1);
        
        Appointment appt2 = new Appointment(
            doctor2, patient2, new Date(), Time.valueOf("10:00:00"), Time.valueOf("10:45:00"), 1, 2);

        // Create transaction records
        TransactionRecord record1 = new TransactionRecord(appt1, 150.0);
        TransactionRecord record2 = new TransactionRecord(appt2, 200.0);

        transactionList.add(record1);
        transactionList.add(record2);

        // Store transaction data in the session
        HttpSession session = request.getSession();
        session.setAttribute("transactionList", transactionList);

        // Forward to JSP for display
        RequestDispatcher dispatcher = request.getRequestDispatcher("transaction_record.jsp");
        dispatcher.forward(request, response);
    }
}
