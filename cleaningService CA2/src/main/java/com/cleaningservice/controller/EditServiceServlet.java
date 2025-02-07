package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.IOException;

/**
 * Servlet implementation class EditServiceServlet
 */
@WebServlet("/admin/editService")
public class EditServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Handles GET requests to fetch service details and forward to editService.jsp.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));

        // Fetch service details from Spring Boot API
        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:8081/api/services/service/" + serviceId;
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response resp = invocationBuilder.get();

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            com.cleaningservice.model.Service service = resp.readEntity(com.cleaningservice.model.Service.class);
            request.getSession().setAttribute("service", service);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/editService.jsp");
            dispatcher.forward(request, response);
            return; // ✅ Prevents further response processing
        } else {
            response.sendRedirect(request.getContextPath() + "/cleaningService/admin/adminDashboard.jsp?error=ServiceNotFound");
            return; // ✅ Stops execution after redirect
        }

    }

    /**
     * Handles POST requests to update a service.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String serviceName = request.getParameter("serviceName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String imagePath = request.getParameter("imagePath");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // Construct JSON request body
        String jsonInputString = "{" +
                "\"serviceName\": \"" + serviceName + "\"," +
                "\"description\": \"" + description + "\"," +
                "\"price\": " + price + "," +
                "\"imagePath\": \"" + imagePath + "\"," +
                "\"categoryId\": " + categoryId + "}";

        // Create a REST client to communicate with Spring Boot API
        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:8081/api/services/" + serviceId;
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        Response resp = invocationBuilder.put(Entity.entity(jsonInputString, MediaType.APPLICATION_JSON));
        System.out.println("Response Status: " + resp.getStatus());

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println("Success: Service updated");
            response.sendRedirect(request.getContextPath() +"/admin/dashboard?success=ServiceUpdated");
        } else {
            System.out.println("Error: Failed to update service");
            response.sendRedirect(request.getContextPath() +"/cleaningService/admin/editService.jsp?serviceId=" + serviceId + "&error=UpdateFailed");
        }
    }
}

