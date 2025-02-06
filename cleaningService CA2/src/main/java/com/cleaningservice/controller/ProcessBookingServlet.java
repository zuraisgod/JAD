package com.cleaningservice.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


/**
 * Servlet implementation class ProcessBookingServlet
 */
@WebServlet("/ProcessBookingServlet")
public class ProcessBookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Retrieve session attributes
        String userId = (String) session.getAttribute("userId");
        String serviceId = request.getParameter("serviceId");
        

if (serviceId == null || serviceId.trim().isEmpty()) {
    request.setAttribute("errorMessage", "Invalid service ID.");
    RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
    rd.forward(request, response);
    return;
}

        // Set attributes for the JSP
        request.setAttribute("userId", userId);
        request.setAttribute("serviceId", serviceId);

        // Forward to bookPage.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/booking/bookPage.jsp");
        dispatcher.forward(request, response);
    }

}
