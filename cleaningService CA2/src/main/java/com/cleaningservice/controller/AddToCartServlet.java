package com.cleaningservice.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/addToCartServlet")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("userId"); // Get userId from session
        String serviceId = request.getParameter("serviceId");

        String message;
        try {
            String apiUrl = "http://localhost:8081/api/cart/add";
            String queryParams = "userID=" + userId + "&serviceID=" + serviceId;

            URL url = new URL(apiUrl + "?" + queryParams);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            if (conn.getResponseCode() == 200) {
                message = "Item successfully added to cart!";
            } else {
                message = "Failed to add item to cart.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while adding the item to the cart.";
        }

        // Forward to the success page with the message
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/cart/addToCartSuccess.jsp");
        dispatcher.forward(request, response);
    }
}
