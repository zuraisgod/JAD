package com.cleaningservice.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

@WebServlet("/finishBookingServlet")
public class FinishBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters
        String userID = request.getParameter("userID");
        String serviceID = request.getParameter("serviceID");
        String bookingDate = request.getParameter("bookingDate");

        // Debugging: Print values to console
        System.out.println("FinishBookingServlet - User ID: " + userID);
        System.out.println("FinishBookingServlet - Service ID: " + serviceID);
        System.out.println("FinishBookingServlet - Booking Date: " + bookingDate);

        // Check for null values
        if (userID == null || serviceID == null || bookingDate == null) {
            System.out.println("Null parameter found.");
            request.setAttribute("errorMessage", "Missing booking details. Please ensure all fields are filled.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // API call
        try {
            URI uri = URI.create("http://localhost:8081/api/bookings");
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            String postData = "userID=" + URLEncoder.encode(userID, "UTF-8") +
                              "&serviceID=" + URLEncoder.encode(serviceID, "UTF-8") +
                              "&bookingDate=" + URLEncoder.encode(bookingDate, "UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/booking/successBooking.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Failed to complete booking. Please try again.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while processing your booking.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
