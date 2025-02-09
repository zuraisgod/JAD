package com.cleaningservice.member.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/bookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Retrieve userId and role from session
        String userIdString = (String) session.getAttribute("userId");
        String role = (String) session.getAttribute("role"); // Retrieve role (if needed)

        // Debugging - Print session attributes
        System.out.println("BookingServlet - User ID in session: " + userIdString);
        System.out.println("BookingServlet - Role in session: " + role);

        int userID;
        try {
            userID = Integer.parseInt(userIdString); // Convert userId to Integer
        } catch (NumberFormatException e) {
            System.out.println("BookingServlet - ERROR: Invalid userId format.");
            response.sendRedirect(request.getContextPath() + "/error.jsp");
            return;
        }

        // Retrieve serviceId from request
        String serviceID = request.getParameter("serviceId");

        // Validate serviceID
        if (serviceID == null || serviceID.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Invalid Service ID.");
            RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
            rd.forward(request, response);
            return;
        }

        // Debugging - Print values to console
        System.out.println("BookingServlet - Final User ID: " + userID);
        System.out.println("BookingServlet - Final Service ID: " + serviceID);

        // Redirect to the Spring Boot API for booking
        response.sendRedirect("http://localhost:8081/api/bookings?userID=" + userID + "&serviceID=" + serviceID);
    }
}
