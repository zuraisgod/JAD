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
import java.util.ArrayList;

import com.cleaningservice.model.Service;

/**
 * Servlet implementation class FilteredServicesServlet
 */
@WebServlet("/admin/filtered-services")
public class FilteredServicesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FilteredServicesServlet() {
        super();
    }

    /**
     * Handles GET requests for fetching filtered services.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Retrieve filter parameters from request
        String minPrice = request.getParameter("minPrice") != null ? request.getParameter("minPrice") : "0.00";
        String maxPrice = request.getParameter("maxPrice") != null ? request.getParameter("maxPrice") : "50";
        String sortBy = request.getParameter("sortBy") != null ? request.getParameter("sortBy") : "popularity";
        String timeFrame = request.getParameter("timeFrame") != null ? request.getParameter("timeFrame") : "month";

        // Construct API request URL with query parameters
        String restUrl = "http://localhost:8081/api/services/filtered?minPrice=" + minPrice +
                         "&maxPrice=" + maxPrice + "&sortBy=" + sortBy + "&timeFrame=" + timeFrame;

        // Create a REST client to communicate with Server 2
        try (Client client = ClientBuilder.newClient()) {  // Ensure Client is closed properly
            WebTarget target = client.target(restUrl);
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response resp = invocationBuilder.get();
            System.out.println("Response Status: " + resp.getStatus());

            if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
                System.out.println("Success: Retrieved filtered services from Server 2");

                // Convert JSON response to ArrayList<Service>
                ArrayList<Service> filteredServices = resp.readEntity(new GenericType<ArrayList<Service>>() {});
                System.out.println("Total Filtered Services: " + filteredServices.size());

                // Store filtered services in request scope
                request.setAttribute("filteredServices", filteredServices);
                System.out.println("Forwarding to adminDashboard.jsp...");
            } else {
                System.out.println("Error: Failed to fetch filtered services");
                request.setAttribute("err", "NotFound");
            }
        }

        // Forward the request to adminDashboard.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/cleaningService/admin/adminDashboard.jsp");
        rd.forward(request, response);
    }

    /**
     * Handles POST requests for fetching filtered services.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect POST requests to the doGet method
        doGet(request, response);
    }
}
