package com.cleaningservice.member.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
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
            // Construct the URL using URI
            URI uri = new URI(
                "http", // Scheme
                null, // User Info
                "localhost", // Host
                8081, // Port
                "/api/cart/add", // Path
                "userID=" + userId + "&serviceID=" + serviceId, // Query
                null // Fragment
            );
            URL url = uri.toURL(); // Convert URI to URL

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
