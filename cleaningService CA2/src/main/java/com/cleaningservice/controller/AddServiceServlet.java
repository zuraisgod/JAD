package com.cleaningservice.controller;

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

@WebServlet("/admin/addService")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB threshold
    maxFileSize = 1024 * 1024 * 10, // 10MB max file size
    maxRequestSize = 1024 * 1024 * 50 // 50MB max request size
)
public class AddServiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String UPLOAD_DIR = "/cleaningService/images"; // Directory to store images

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Get form parameters
            String serviceName = request.getParameter("serviceName");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int categoryId = Integer.parseInt(request.getParameter("categoryId"));
            
            // Handle file upload
            Part filePart = request.getPart("image");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            
            // Create directory if it doesn't exist
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // Save the file
            String filePath = uploadPath + File.separator + fileName;
            filePart.write(filePath);

            // Generate image path to store in database
            String imagePath = "/cleaningService/images/" + fileName;

            // Create JSON request body
            String jsonInputString = "{" +
                    "\"categoryId\": " + categoryId + "," +
                    "\"serviceName\": \"" + serviceName + "\"," +
                    "\"description\": \"" + description + "\"," +
                    "\"price\": " + price + "," +
                    "\"imagePath\": \"" + imagePath + "\"}";

            // Send data to REST API
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8081/api/services");
            Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
            Response apiResponse = invocationBuilder.post(Entity.entity(jsonInputString, MediaType.APPLICATION_JSON));

            // Redirect based on API response
            if (apiResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=ServiceAddedSuccessfully");
            } else {
                response.sendRedirect(request.getContextPath() + "/cleaningService/admin/addService.jsp?error=AddFailed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cleaningService/admin/addService.jsp?error=ServerError");
        }
    }
}
