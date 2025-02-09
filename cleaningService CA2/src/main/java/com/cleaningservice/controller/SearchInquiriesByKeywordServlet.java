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

import com.cleaningservice.model.Inquiry;

@WebServlet("/admin/searchInquiries")
public class SearchInquiriesByKeywordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchInquiriesByKeywordServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("error", "Keyword cannot be empty.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/inquiryManagement.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8081/api/inquiries/search?keyword=" + keyword);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response apiResponse = invocationBuilder.get();

        if (apiResponse.getStatus() == 200) {
            ArrayList<Inquiry> inquiries = apiResponse.readEntity(new GenericType<ArrayList<Inquiry>>() {});
            request.setAttribute("inquiries", inquiries);
        } else {
            request.setAttribute("error", "Failed to retrieve inquiries.");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/inquiryManagement.jsp");
        dispatcher.forward(request, response);
    }
}
