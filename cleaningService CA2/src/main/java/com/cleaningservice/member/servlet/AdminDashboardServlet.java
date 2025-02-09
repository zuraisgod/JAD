package com.cleaningservice.member.servlet;

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
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminDashboardServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Declare the client
        Client client = null;

        try {
            // Initialize the client
            client = ClientBuilder.newClient();

            // Define the REST API URL
            String restUrl = "http://localhost:8081/api/services";
            WebTarget target = client.target(restUrl);

            // Send the GET request and receive a response
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
            } else {
                System.out.println("Error: Failed to fetch services");
                request.setAttribute("err", "NotFound");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("err", "ServerError");
        } finally {
            if (client != null) {
                client.close(); // Ensure the client is closed properly
            }
        }

        // Forward the request to adminDashboard.jsp
        RequestDispatcher rd = request.getRequestDispatcher("/cleaningService/admin/adminDashboard.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
