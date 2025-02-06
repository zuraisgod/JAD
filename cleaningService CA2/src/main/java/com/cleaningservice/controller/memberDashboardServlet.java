package com.cleaningservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * Handles Member Dashboard requests by fetching data from Spring Boot API.
 */
@WebServlet("/member/dashboard")
public class memberDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Ensure the user is logged in
        HttpSession session = request.getSession(false); // Don't create a new session
        if (session == null || session.getAttribute("userId") == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp"); // Redirect to login
            return;
        }

        // Fetch categories from the Spring Boot API
        List<Map<String, Object>> categories = null;
        try {
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

        // Add user info to the request
        request.setAttribute("userId", session.getAttribute("userId"));
        request.setAttribute("role", session.getAttribute("role"));

        // Pass the fetched data to the JSP page
        request.setAttribute("categories", categories);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/member/memberIndex.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
