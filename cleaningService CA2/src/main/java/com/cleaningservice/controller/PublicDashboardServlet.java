package com.cleaningservice.controller;

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
import java.net.URL;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Handles Public Dashboard requests by fetching category data from Spring Boot API.
 */
@WebServlet("/public/dashboard")
public class PublicDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> categories = null; // To hold JSON data from API

        try {
            // Call the Spring Boot API to get categories
            URI uri = new URI("http://localhost:8081/api/categories");
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            categories = mapper.readValue(conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {});

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Pass the fetched data to the JSP page
        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/public/publicIndex.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
