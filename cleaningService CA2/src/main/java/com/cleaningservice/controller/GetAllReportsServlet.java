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

import com.cleaningservice.model.Report;

@WebServlet("/admin/reports")
public class GetAllReportsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GetAllReportsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/api/reports");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response apiResponse = invocationBuilder.get();

        if (apiResponse.getStatus() == 200) {
            ArrayList<Report> reports = apiResponse.readEntity(new GenericType<ArrayList<Report>>() {});
            request.setAttribute("reports", reports);
        } else {
            request.setAttribute("error", "Failed to retrieve reports.");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/reportManagement.jsp");
        dispatcher.forward(request, response);
    }
}