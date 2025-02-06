package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String contactNumber = request.getParameter("contactNumber");
        String address = request.getParameter("address");

        // Password confirmation check
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match!");
            RequestDispatcher rd = request.getRequestDispatcher("cleaningService/register.jsp");
            rd.forward(request, response);
            return;
        }

        // Send data to Spring Boot backend
        URI uri = URI.create("http://localhost:8081/api/register");
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // Send form data
        try (OutputStream os = conn.getOutputStream()) {
            String postData = "name=" + URLEncoder.encode(name, "UTF-8") +
                              "&email=" + URLEncoder.encode(email, "UTF-8") +
                              "&password=" + URLEncoder.encode(password, "UTF-8") +
                              "&contactNumber=" + URLEncoder.encode(contactNumber, "UTF-8") +
                              "&address=" + URLEncoder.encode(address, "UTF-8");
            os.write(postData.getBytes());
        }

        // Read response
        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            response.sendRedirect("cleaningService/registrationSuccess.jsp");
        } else {
            request.setAttribute("errorMessage", "Registration failed. Email may already exist.");
            RequestDispatcher rd = request.getRequestDispatcher("cleaningService/register.jsp");
            rd.forward(request, response);
        }
    }
}
