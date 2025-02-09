package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.GenericType;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.cleaningservice.model.User;

/**
 * Servlet for sorting users based on different criteria.
 */
@WebServlet("/admin/sortUsers")
public class SortUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SortUserServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sortBy = request.getParameter("sortBy");

        try (Client client = ClientBuilder.newClient()) {
            String restUrl = "http://localhost:8081/api/users/sorted?sortBy=" + sortBy;
            WebTarget target = client.target(restUrl);
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response resp = invocationBuilder.get();

            if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
                ArrayList<User> sortedUsers = resp.readEntity(new GenericType<ArrayList<User>>() {});

                // Convert registrationDate from String to Timestamp
                for (User user : sortedUsers) {
                    if (user.getRegistrationDate() != null) {
                        user.setRegistrationDate(convertStringToTimestamp(user.getRegistrationDate()));
                    }
                }

                request.setAttribute("sortedUsers", sortedUsers);
            } else {
                request.setAttribute("sortedUsers", null);
            }
        }

        RequestDispatcher rd = request.getRequestDispatcher("/cleaningService/admin/memberManagement.jsp");
        rd.forward(request, response);
    }

    /**
     * Converts ISO-8601 formatted date string to SQL Timestamp
     */
    private Timestamp convertStringToTimestamp(String dateString) {
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            return new Timestamp(isoFormat.parse(dateString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle null case
        }
    }
}
