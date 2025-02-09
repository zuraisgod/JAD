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

/**
 * Servlet implementation class UpdateProfileServlet
 */
@WebServlet("/updateProfileServlet")
public class UpdateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());
        String name = request.getParameter("name");
        String contactNumber = request.getParameter("contactNumber");
        String address = request.getParameter("address");

        try {
            // Construct the URI with the user ID
            URI uri = new URI("http", null, "localhost", 8081, "/api/users/update/" + userId, null, null);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Create JSON payload
            String jsonInput = String.format(
                "{\"name\":\"%s\",\"contactNumber\":\"%s\",\"address\":\"%s\"}",
                name, contactNumber, address
            );

            // Send JSON payload
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes());
                os.flush();
            }

            if (conn.getResponseCode() == 200) {
                // Redirect to the profile page after success
                response.sendRedirect("cleaningService/profile/UpdateSuccess.jsp");
            } else {
                response.sendRedirect("error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
