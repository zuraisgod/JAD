package com.cleaningservice.controller;

import com.cleaningservice.model.User;
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
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class GetAllMembersServlet
 */
@WebServlet("/admin/members")
public class GetAllMembersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public GetAllMembersServlet() {
        super();
    }

    /**
     * Handles GET requests to retrieve all members.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Create a REST client to communicate with the endpoint
        try (Client client = ClientBuilder.newClient()) {  // Ensure Client is closed properly
            String restUrl = "http://localhost:8081/api/users/members";  // Server 2 endpoint
            WebTarget target = client.target(restUrl);
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response resp = invocationBuilder.get();
            System.out.println("Response Status: " + resp.getStatus());

            if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Success: Retrieved members from Server 2");

                // Convert JSON response to ArrayList<User>
                ArrayList<User> members = resp.readEntity(new GenericType<ArrayList<User>>() {});
                System.out.println("Total Members: " + members.size());

                // Store members in request scope
                request.setAttribute("members", members);
                System.out.println("Forwarding to memberManagement.jsp...");
            } else {
                System.out.println("Error: Failed to fetch members");
                request.setAttribute("err", "NotFound");
            }
        }

        // Forward the request to memberManagement.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/cleaningService/admin/memberManagement.jsp");
        rd.forward(request, response);
    }

    /**
     * Handles POST requests by redirecting to the GET method.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
