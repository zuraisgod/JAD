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
import java.util.Map;

/**
 * Servlet for viewing booking details.
 */
@WebServlet("/admin/viewBookingDetails")
public class AdminBookingDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("bookingId");

        Map<String, Object> bookingDetails = null;
        try {
            URI uri = new URI("http://localhost:8081/api/bookings/" + bookingId);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            bookingDetails = mapper.readValue(conn.getInputStream(), new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("bookingDetails", bookingDetails);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/adminBookingDetails.jsp");
        dispatcher.forward(request, response);
    }
}
