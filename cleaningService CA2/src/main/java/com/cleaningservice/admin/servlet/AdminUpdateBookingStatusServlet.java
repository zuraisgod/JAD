package com.cleaningservice.admin.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Servlet for updating booking status.
 */
@WebServlet("/admin/updateBookingStatus")
public class AdminUpdateBookingStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");
        String newStatus = request.getParameter("newStatus");
        

        System.out.println("Booking ID: " + bookingId); // Debugging
        System.out.println("New Status: " + newStatus); // Debugging

        try {
            // Call Spring Boot API to update the booking status
            URI uri = new URI("http://localhost:8081/api/bookings/" + bookingId + "/status");
            System.out.println("Calling API at: " + uri); // Debugging

            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // JSON Payload
            String jsonPayload = String.format("{\"status\": \"%s\"}", newStatus);
            System.out.println("Payload: " + jsonPayload); // Debugging

            conn.getOutputStream().write(jsonPayload.getBytes());

            // Check API response
            int responseCode = conn.getResponseCode();
            System.out.println("API Response Code: " + responseCode);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Redirect to success page
                request.setAttribute("successMessage", "Booking status updated successfully!");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/success.jsp");
                dispatcher.forward(request, response);
            } else {
                // Redirect to error page
                request.setAttribute("errorMessage", "Failed to update booking status.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/error.jsp");
                dispatcher.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Server error occurred.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
