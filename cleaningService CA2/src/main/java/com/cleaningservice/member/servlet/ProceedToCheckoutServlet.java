package com.cleaningservice.member.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Servlet to invoke Spring Boot API for checkout
 */
@WebServlet("/ProceedToCheckoutServlet")
public class ProceedToCheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId");
        String[] selectedItems = request.getParameterValues("selectedItems");

        if (selectedItems == null || selectedItems.length == 0) {
            request.setAttribute("errorMessage", "Please select at least one service to proceed.");
            request.getRequestDispatcher("/cleaningService/cart/cart.jsp").forward(request, response);
            return;
        }

        // Call API to complete the booking
        for (String serviceId : selectedItems) {
            String dateTime = request.getParameter("serviceDateTime_" + serviceId);
            try {
                String apiUrl = "http://localhost:8081/api/bookings/complete";
                String payload = String.format("{\"userId\": \"%s\", \"serviceId\": \"%s\", \"dateTime\": \"%s\"}",
                        userId, serviceId, dateTime);

                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.getOutputStream().write(payload.getBytes());

                if (conn.getResponseCode() != 200) {
                    request.setAttribute("errorMessage", "An error occurred during checkout.");
                    request.getRequestDispatcher("/cleaningService/cart/cart.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Failed to connect to checkout API.");
                request.getRequestDispatcher("/cleaningService/cart/cart.jsp").forward(request, response);
                return;
            }
        }

        // Redirect to success page
        response.sendRedirect("checkoutSuccess.jsp");
    }
}
