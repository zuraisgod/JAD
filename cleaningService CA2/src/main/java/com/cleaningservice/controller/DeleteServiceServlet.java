package com.cleaningservice.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import java.io.IOException;

@WebServlet("/admin/deleteService")
public class DeleteServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        
        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:8081/api/services/" + serviceId;
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request();
        Response apiResponse = invocationBuilder.delete();
        
        if (apiResponse.getStatus() == Response.Status.OK.getStatusCode() || apiResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=ServiceDeletedSuccessfully");
        } else if (apiResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=ServiceNotFound");
        } else if (apiResponse.getStatus() == Response.Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=ServerError");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=DeleteFailed");
        }
    }
}
