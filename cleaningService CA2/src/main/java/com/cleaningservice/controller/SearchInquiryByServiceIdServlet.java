package com.cleaningservice.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@WebServlet("/SearchInquiryByServiceIdServlet")
public class SearchInquiryByServiceIdServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceId = request.getParameter("serviceID");
        String apiUrl = "http://localhost:8081/api/inquiries/searchByService?serviceID=" + serviceId;
        String jsonResponse = fetchFromApi(apiUrl);
        
        request.setAttribute("inquiries", jsonResponse);
        request.getRequestDispatcher("/cleaningService/admin/inquiryManagement.jsp").forward(request, response);
    }
    
    private String fetchFromApi(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        
        InputStream responseStream = conn.getInputStream();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseBody = scanner.useDelimiter("\\A").next();
        scanner.close();
        
        return responseBody;
    }
}
