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

@WebServlet("/changePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        System.out.println("User ID retrieved: " + userId);

        
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Validate passwords
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match");
            request.setAttribute("errorMessage", "New password and confirmation password do not match.");
            request.getRequestDispatcher("/profile/UpdateSuccess.jsp").forward(request, response);
            return;
        }
        
        
        try {
            // Send data to Spring Boot API
            URI uri = new URI("http", null, "localhost", 8081, "/api/users/changePassword" + userId, null, null);
            URL url = uri.toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInput = String.format(
                "{\"oldPassword\":\"%s\",\"newPassword\":\"%s\"}",
                oldPassword, newPassword
            );

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            // Handle response from Spring Boot API
            if (conn.getResponseCode() == 200) {
                response.sendRedirect("cleaningService/profile/UpdateSuccess.jsp");
            } else {
                response.sendRedirect("cleaningService/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while changing the password.");
            request.getRequestDispatcher("/profile/UpdateSuccess.jsp").forward(request, response);
        }
    }
}
