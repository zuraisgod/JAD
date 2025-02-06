package com.cleaningservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/cleaningService/MemberServiceDetails")
public class MemberServiceDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Get categoryId from the request
        String categoryId = request.getParameter("categoryId");
        if (categoryId == null || categoryId.trim().isEmpty()) {
            request.setAttribute("error", "Invalid category selection.");
            request.getRequestDispatcher("/cleaningService/error.jsp").forward(request, response);
            return;
        }

        List<Map<String, Object>> services = new ArrayList<>();
        HttpURLConnection conn = null;
        try {
            URL url = new URI("http", null, "localhost", 8081, "/api/services/" + categoryId, null, null).toURL();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                services = mapper.readValue(conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {});

                // ✅ Debugging: Print fetched services
                for (Map<String, Object> service : services) {
                    System.out.println("Fetched Service: " + service);
                }
            } else {
                request.setAttribute("error", "Failed to load services. Please try again later.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching services.");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        // ✅ Debugging: Print if the list is empty
        if (services.isEmpty()) {
            System.out.println("No services found for category ID: " + categoryId);
        }

        request.setAttribute("services", services);
        request.getRequestDispatcher("/cleaningService/member/memberServiceDetails.jsp").forward(request, response);
    }

}
