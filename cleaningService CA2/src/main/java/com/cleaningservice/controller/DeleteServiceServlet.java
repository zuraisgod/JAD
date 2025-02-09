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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;

import com.cleaningservice.model.Service;

@WebServlet("/admin/deleteService")
public class DeleteServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int serviceId = Integer.parseInt(request.getParameter("serviceId"));

            Client client = ClientBuilder.newClient();
            String restUrl = "http://localhost:8081/api/services/" + serviceId;
            WebTarget target = client.target(restUrl);

            // 🟢 1️⃣ First, get service details (to retrieve image path)
            Invocation.Builder getBuilder = target.request(MediaType.APPLICATION_JSON);
            Response getResponse = getBuilder.get();

            if (getResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                // 🟢 2️⃣ Read the service details
                Service service = getResponse.readEntity(Service.class);
                String imagePath = getServletContext().getRealPath("") + service.getImagePath();

                // 🟢 3️⃣ Delete service from database
                Invocation.Builder deleteBuilder = target.request();
                Response deleteResponse = deleteBuilder.delete();

                if (deleteResponse.getStatus() == Response.Status.OK.getStatusCode() ||
                    deleteResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {

                    // 🟢 4️⃣ Delete image file after deleting service
                    File fileToDelete = new File(imagePath);
                    if (fileToDelete.exists()) {
                        fileToDelete.delete(); // Delete the image
                    }

                    // 🟢 5️⃣ Redirect on success
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=ServiceDeletedSuccessfully");
                } else {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=DeleteFailed");
                }
            } else if (getResponse.getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=ServiceNotFound");
            } else {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=ServerError");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=ServerError");
        }
    }
}
