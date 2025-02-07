package com.cleaningservice.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.servlet.RequestDispatcher;
import java.net.URI;

/**
 * Servlet implementation class MyBookingsServlet
 */
@WebServlet("/myBookingsServlet")
public class MyBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());

        try {
            // Call Spring Boot API
        	URI uri = new URI("http", null, "localhost", 8081, "/api/users/" + userId + "/bookings", null, null);
        	URL url = uri.toURL(); // Convert URI to URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Handle response
            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                List<Map<String, Object>> bookings = mapper.readValue(
                    conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {}
                );
                request.setAttribute("bookings", bookings);
            } else {
                request.setAttribute("errorMessage", "Failed to fetch bookings.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred.");
        }

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/profile/profile.jsp");
        dispatcher.forward(request, response);
    }
}

