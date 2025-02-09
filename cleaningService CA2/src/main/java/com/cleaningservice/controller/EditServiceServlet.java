package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/admin/editService")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold
    maxFileSize = 1024 * 1024 * 10, // 10MB max file size
    maxRequestSize = 1024 * 1024 * 50 // 50MB max request size
)
public class EditServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "/cleaningService/images";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));

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
            return;
        } else {
            response.sendRedirect(request.getContextPath() + "/cleaningService/admin/adminDashboard.jsp?error=ServiceNotFound");
            return;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        String serviceName = request.getParameter("serviceName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        // Retrieve the existing image path
        String existingImagePath = request.getParameter("existingImage");

        // Process the uploaded file
        Part filePart = request.getPart("image");
        String fileName = filePart != null ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() : "";
        String imagePath = existingImagePath; // Default to existing image

        if (filePart != null && fileName != null && !fileName.isEmpty()) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);
            imagePath = "/cleaningService/images/" + fileName; // Set new image path
        }

        // Prepare JSON payload
        String jsonInputString = "{" +
                "\"serviceName\": \"" + serviceName + "\"," +
                "\"description\": \"" + description + "\"," +
                "\"price\": " + price + "," +
                "\"imagePath\": \"" + imagePath + "\"," +  // Use existing image if no new one
                "\"categoryId\": " + categoryId + "}";

        Client client = ClientBuilder.newClient();
        String restUrl = "http://localhost:8081/api/services/" + serviceId;
        WebTarget target = client.target(restUrl);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        Response resp = invocationBuilder.put(Entity.entity(jsonInputString, MediaType.APPLICATION_JSON));

        if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
            response.sendRedirect(request.getContextPath() +"/admin/dashboard?success=ServiceUpdated");
        } else {
            response.sendRedirect(request.getContextPath() +"/cleaningService/admin/editService.jsp?serviceId=" + serviceId + "&error=UpdateFailed");
        }
    }


}
