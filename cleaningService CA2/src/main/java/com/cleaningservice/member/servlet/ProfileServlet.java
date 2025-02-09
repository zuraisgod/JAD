package com.cleaningservice.member.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getSession().getAttribute("userId").toString());

        try {
            URI uri = new URI("http", null, "localhost", 8081, "/api/users/" + userId, null, null);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                Map<String, String> userProfile = mapper.readValue(
                    new BufferedReader(new InputStreamReader(conn.getInputStream())), 
                    new TypeReference<Map<String, String>>() {}
                );
                request.setAttribute("userProfile", userProfile);
            } else {
                request.setAttribute("errorMessage", "Failed to fetch user profile.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred while fetching profile data.");
        }

         try {
        // Call Spring Boot API
    	URI uri = new URI("http", null, "localhost", 8081, "/api/users/" + userId + "/bookings", null, null);
    	URL url = uri.toURL(); // Convert URI to URL
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        // Handle response
        if (conn.getResponseCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> bookings = mapper.readValue(
                conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {}
            );
            request.setAttribute("bookings", bookings);
        } else {
            request.setAttribute("errorMessage", "Failed to fetch bookings.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("errorMessage", "An error occurred.");
    }
         
         //for fetching completed bookings
         try {
             URI uri = new URI("http", null, "localhost", 8081, "/api/bookings/" + userId + "/completedBookings", null, null);
         	URL url = uri.toURL(); 
             HttpURLConnection conn = (HttpURLConnection) url.openConnection();
             conn.setRequestMethod("GET");
             conn.setRequestProperty("Accept", "application/json");

             if (conn.getResponseCode() == 200) {
                 ObjectMapper mapper = new ObjectMapper();
                 List<Map<String, Object>> completedBookings = mapper.readValue(
                     conn.getInputStream(), new TypeReference<List<Map<String, Object>>>() {}
                 );
                 request.setAttribute("completedBookings", completedBookings);
             } else {
                 request.setAttribute("errorMessage", "Failed to fetch completed bookings.");
             }
         } catch (Exception e) {
             e.printStackTrace();
             request.setAttribute("errorMessage", "An error occurred while fetching bookings.");
         }

    // Forward to JSP
    RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/profile/profile.jsp");
    dispatcher.forward(request, response);
}
}
