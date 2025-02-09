package com.cleaningservice.member.servlet;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles requests to display services for a specific category.
 */
@WebServlet("/public/serviceDetails")
public class PublicServiceDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryId = request.getParameter("categoryId");
        List<Map<String, Object>> services = new ArrayList<>();

        try {
            // Fetch services from Spring Boot API based on category ID
            URL url = new URI("http", null, "localhost", 8081, "/api/services/" + categoryId, null, null).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                services = mapper.readValue(conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {});
            } else {
                request.setAttribute("error", "Failed to load services. Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching services.");
        }

        // Set the services as a request attribute
        request.setAttribute("services", services);

        // Forward the request to the JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/public/publicServiceDetails.jsp");
        dispatcher.forward(request, response);
    }
}
