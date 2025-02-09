package com.cleaningservice.member.servlet;

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

@WebServlet("/admin/addService")
public class AddServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceName = request.getParameter("serviceName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        String imagePath = request.getParameter("imagePath");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        
        String jsonInputString = "{" +
                "\"categoryId\": " + categoryId + "," +
                "\"serviceName\": \"" + serviceName + "\"," +
                "\"description\": \"" + description + "\"," +
                "\"price\": " + price + "," +
                "\"imagePath\": \"" + imagePath + "\"}";
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/api/services");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response apiResponse = invocationBuilder.post(Entity.entity(jsonInputString, MediaType.APPLICATION_JSON));
        
        if (apiResponse.getStatus() == Response.Status.OK.getStatusCode()) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=ServiceAddedSuccessfully");
        } else {
            response.sendRedirect(request.getContextPath() + "/cleaningService/admin/addService.jsp?error=AddFailed");
        }
    }
}
