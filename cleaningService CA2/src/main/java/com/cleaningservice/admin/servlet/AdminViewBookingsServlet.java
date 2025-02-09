package com.cleaningservice.admin.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.List;
import java.util.Map;

/**
 * Servlet for handling admin requests to view bookings.
 */
@WebServlet("/admin/viewBookings")
public class AdminViewBookingsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch bookings from Spring Boot API
        List<Map<String, Object>> bookings = null;
        try {
            URI uri = new URI("http://localhost:8081/api/bookings");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            bookings = mapper.readValue(conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass bookings data to the JSP page
        request.setAttribute("bookings", bookings);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/adminViewBookings.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
