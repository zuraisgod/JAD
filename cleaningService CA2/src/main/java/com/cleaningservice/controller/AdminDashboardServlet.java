package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class AdminDashboardServlet
 */
@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public AdminDashboardServlet() {
        super();
    }

    /**
     * Handles GET requests for Admin Dashboard.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to adminDashboard.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/admin/adminDashboard.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests for Admin Dashboard.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirect POST requests to the doGet method
        doGet(request, response);
    }
}
