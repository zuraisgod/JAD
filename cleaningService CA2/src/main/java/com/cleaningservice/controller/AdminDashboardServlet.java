package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public AdminDashboardServlet() {
        super();
    }

    /**
     * Handles GET requests for Admin Dashboard.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
<<<<<<< Updated upstream
        // Create a REST client to communicate with Server 2
        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:8081/api/services";  // Server 2 endpoint
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();
        System.out.println("Response Status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("Success: Retrieved services from Server 2");

            // Convert JSON response to ArrayList<Service>
            ArrayList<Service> services = resp.readEntity(new GenericType<ArrayList<Service>>() {});
            System.out.println("Total Services: " + services.size());

            // Store services in request scope
            request.setAttribute("services", services);
            System.out.println("Forwarding to adminDashboard.jsp...");
        } else {
            System.out.println("Error: Failed to fetch services");
            request.setAttribute("err", "NotFound");
        }


        RequestDispatcher rd = request.getRequestDispatcher("/cleaningService/admin/adminDashboard.jsp");

        rd.forward(request, response);
=======
        // Forward the request to adminDashboard.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/adminDashboard.jsp");
        dispatcher.forward(request, response);
>>>>>>> Stashed changes
    }

    /**
     * Handles POST requests for Admin Dashboard.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect POST requests to the doGet method
        doGet(request, response);
    }
}
