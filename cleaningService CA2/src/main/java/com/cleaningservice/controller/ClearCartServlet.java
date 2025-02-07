package com.cleaningservice.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@WebServlet("/clearCartServlet")
public class ClearCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get userId from session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String userId = (String) session.getAttribute("userId");

        String message;
        try {
            String apiUrl = "http://localhost:8081/api/cart/clear/" + userId;
            URL url = new URL(apiUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            if (conn.getResponseCode() == 200) {
                message = "Cart cleared successfully!";
            } else {
                message = "Failed to clear the cart.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "An error occurred while clearing the cart.";
        }

        // Redirect to cart.jsp with a success or error message
        request.setAttribute("message", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/cart/cart.jsp");
        dispatcher.forward(request, response);
    }
}
