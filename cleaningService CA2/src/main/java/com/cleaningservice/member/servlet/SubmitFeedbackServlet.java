package com.cleaningservice.member.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@WebServlet("/SubmitFeedbackServlet")
public class SubmitFeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L; // Add this field

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Collect form data
        String bookingId = request.getParameter("bookingId");
        String rating = request.getParameter("rating");
        String comments = request.getParameter("comments");

        // Prepare JSON payload
        String jsonPayload = String.format(
                "{\"bookingId\": %s, \"rating\": %s, \"comments\": \"%s\"}",
                bookingId, rating, comments
        );

        // Call Spring Boot API
        try {
        	URI uri = new URI("http", null, "localhost", 8081, "/api/feedback", null, null);
        	URL url = uri.toURL(); // Convert URI to URL

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes(StandardCharsets.UTF_8));
            }

            // Handle response
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                response.sendRedirect("cleaningService/profile/feedbackSuccess.jsp");
            } else {
                response.sendRedirect("feedbackError.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("feedbackError.jsp");
        }
    }
}
