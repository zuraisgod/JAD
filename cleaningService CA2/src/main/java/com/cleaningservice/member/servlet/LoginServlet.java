package com.cleaningservice.member.servlet;

import com.fasterxml.jackson.databind.ObjectMapper; // Add this import for JSON parsing
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Spring Boot API URL
        URI uri = URI.create("http://localhost:8081/api/login");
        URL url = uri.toURL();

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // Send form data
        try (OutputStream os = conn.getOutputStream()) {
            String postData = "email=" + URLEncoder.encode(email, "UTF-8") +
                              "&password=" + URLEncoder.encode(password, "UTF-8");
            os.write(postData.getBytes());
        }

        // Read response
        int responseCode = conn.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                responseCode == 200 ? conn.getInputStream() : conn.getErrorStream()));
        StringBuilder responseContent = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            responseContent.append(line);
        }
        in.close();

        if (responseCode == 200) {
            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseData = objectMapper.readValue(
                responseContent.toString(), new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {}
            );

            // Retrieve userId and role from the response
            String userId = String.valueOf(responseData.get("userId")); // Convert userId to String
            String role = (String) responseData.get("role"); // Retrieve role as String

            // Store userId and role in the session
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId); // Store userId as String
            session.setAttribute("role", role);     // Store role

            // Redirect to the appropriate dashboard based on role
            if ("Admin".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else if ("Member".equalsIgnoreCase(role)) {
                response.sendRedirect(request.getContextPath() + "/member/dashboard");
            } else {
                request.setAttribute("errorMessage", "Unexpected role received.");
                RequestDispatcher rd = request.getRequestDispatcher("cleaningService/error.jsp");
                rd.forward(request, response);
            }
        } else {
            // Handle invalid credentials
            request.setAttribute("errorMessage", "Invalid login credentials");
            RequestDispatcher rd = request.getRequestDispatcher("cleaningService/login.jsp");
            rd.forward(request, response);
        }
    }
}
