package com.cleaningservice.member.servlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@WebServlet("/cartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String userId = (String) session.getAttribute("userId");
        List<Map<String, Object>> cartItems = null;
        double grandTotal = 0.0;

        try {
            URI uri = new URI("http", null, "localhost", 8081, "/api/cart/items", "userID=" + userId, null);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Handle the connection response
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder responseBuilder = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseBuilder.append(inputLine);
                }
                in.close();
                System.out.println("Response: " + responseBuilder.toString());

                // Process JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                cartItems = objectMapper.readValue(responseBuilder.toString(), new TypeReference<List<Map<String, Object>>>() {});

                // Calculate grand total
                for (Map<String, Object> item : cartItems) {
                    if (item.containsKey("price") && item.containsKey("quantity")) {
                        double price = (double) item.get("price");
                        int quantity = (int) item.get("quantity");
                        grandTotal += price * quantity;
                    }
                }
            } else {
                System.out.println("GET request failed with response code: " + responseCode);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace(); // Handle incorrect URI syntax
        } catch (IOException e) {
            e.printStackTrace(); // Handle connection issues
        }

        // Set attributes for JSP
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("grandTotal", grandTotal);

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cleaningService/cart/cart.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
